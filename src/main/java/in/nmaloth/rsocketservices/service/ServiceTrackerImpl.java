package in.nmaloth.rsocketservices.service;


import com.google.protobuf.InvalidProtocolBufferException;
import in.nmaloth.rsocketservices.config.BeanNames;
import in.nmaloth.rsocketservices.config.model.*;
import in.nmaloth.rsocketservices.model.proto.ServerRegistrationOuterClass;
import in.nmaloth.rsocketservices.processor.ServiceEventProcessor;
import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.ClientCnxnSocketNIO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Slf4j
@Service
public class ServiceTrackerImpl implements ServiceTracker {

    public static final String CLIENT_REGISTRATION = "client.registration";
    public static final String REMOVED_RSOCKET_REQUESTER_MAP = "Removed from  Rsocket requester Map and Service Instance Map  for instance {} and Service {}";
    public static final String COLON = ":";
    public static final String UPDATED_IP_REQUESTER_MAP_FOR_IP = "Updated ip requester Map for ip {}";
    public static final String INVALID_SERVICE_EVENT = "Invalid Service Event {}";
    public static final String REMOVED_REQUESTER_FROM_IP = " Removed Requester from Ip {}";
    public static final String ADDED_TO_OUTGOING_MAP = "Added to outgoing Map {} for service {}";
    public static final String ADDED_TO_INCOMING_MAP = "Added to incoming Map {} for service {}";
    public static final String ADDED_TO_REQUESTER_MAP = "Added to requester Map {} for service {}";
    public static final String INVALID_SERVICE_NAME = "Invalid Service Name {} ... Cannot Connect .. ";
    public static final String CLOSING_CONNECTION = " Closing connection";
    public static final String TRIGGERING_EVENT_REMOVAL_EVENT = "Triggering Event Removal Event for {} instance {}";


    private final ConcurrentMap<String, Set<String>> incomingServices;
    private final ConcurrentMap<String, Set<String>> outgoingServices;
    private final ConcurrentMap<String, RSocketRequester> rSocketRequesterMap;
    private final ServiceEventProcessor<ServiceEvent> serviceEventProcessor;
    private DispatcherService dispatcherService;

    private final ConcurrentMap<String,RSocketRequester> ipRSocketRequesterMap = new ConcurrentHashMap<>();

    private final NodeInfo nodeInfo;

    public ServiceTrackerImpl(@Qualifier(BeanNames.INCOMING_SERVICE_INSTANCE) ConcurrentMap<String, Set<String>> incomingServices,
                              @Qualifier(BeanNames.OUTGOING_SERVICE_INSTANCE) ConcurrentMap<String, Set<String>> outgoingServices,
                              @Qualifier(BeanNames.INSTANCE_REQUESTER) ConcurrentMap<String, RSocketRequester> rSocketRequesterMap,
                              ServiceEventProcessor<ServiceEvent> serviceEventProcessor,
                              NodeInfo nodeInfo) {

        this.incomingServices = incomingServices;
        this.outgoingServices = outgoingServices;
        this.rSocketRequesterMap = rSocketRequesterMap;
        this.serviceEventProcessor = serviceEventProcessor;
        this.nodeInfo = nodeInfo;
    }


    @Override
    public Optional<ServiceInfo> getServiceInfo(String serviceName) {

        return nodeInfo.getDependentService()
                .stream()
                .filter(serviceInfo -> serviceInfo.getServiceName().equalsIgnoreCase(serviceName))
                .findFirst();

    }

    @Override
    public Optional<String> verifyServiceAddress(ServiceEvent serviceEvent, boolean isIn, boolean isOut) {

        String serviceAddress = createServiceAddress(serviceEvent.getServerInfo());
        RSocketRequester rSocketRequester = ipRSocketRequesterMap.get(serviceAddress);
        if(rSocketRequester != null && !rSocketRequester.rsocket().isDisposed() ){
            ipRSocketRequesterMap.remove(serviceAddress);
            return Optional.empty();
        }

        return Optional.of(serviceAddress);
    }

    @Override
    public boolean checkForInactiveRequester(ServerInfo serverInfo) {

        String serverAddress = createServiceAddress(serverInfo);
        RSocketRequester rSocketRequester = ipRSocketRequesterMap.get(serverAddress);

        if(rSocketRequester == null){
            return true;
        }

        if(rSocketRequester.rsocket().isDisposed()){
            ipRSocketRequesterMap.remove(serverAddress);
        }
         return rSocketRequester.rsocket().isDisposed();
    }

    @Override
    public void processNewConnections(RSocketRequester rSocketRequester,
                                      ServerRegistrationOuterClass.ServerRegistration serverRegistration) {


        Optional<ServiceInfo> serviceInfoOptional = nodeInfo.getDependentService()
                .stream()
                .filter(serviceInfo-> serviceInfo.getServiceName().equalsIgnoreCase(serverRegistration.getServiceName()))
                .findFirst();

        if(serviceInfoOptional.isEmpty()){
            log.info(INVALID_SERVICE_NAME,serverRegistration.getServiceName());
            rSocketRequester.rsocket().dispose();
            log.info(CLOSING_CONNECTION);
            return;
        }
        ServiceInfo serviceInfo = serviceInfoOptional.get();

        synchronized (this){

            rSocketRequesterMap.put(serverRegistration.getServiceInstance(),rSocketRequester);

            log.info(ADDED_TO_REQUESTER_MAP,serverRegistration.getServiceInstance(),
                    serverRegistration.getServiceName() );


            if(serviceInfo.getIsIn()){
                Set<String> connectionSet = incomingServices.get(serviceInfo.getServiceName());
                connectionSet.add(serverRegistration.getServiceInstance());
                log.info(ADDED_TO_INCOMING_MAP,serverRegistration.getServiceInstance(),serviceInfo.getServiceName() );
            }

            if(serviceInfo.getIsOut()){
                Set<String> connectionSet = outgoingServices.get(serviceInfo.getServiceName());
                connectionSet.add(serverRegistration.getServiceInstance());
                log.info(ADDED_TO_OUTGOING_MAP,serverRegistration.getServiceInstance(),serviceInfo.getServiceName() );

            }

        }

    }

    @Override
    public void removeRequester(ServiceEvent serviceEvent) {

        Optional<ServiceInfo> serviceInfoOptional = getServiceInfo(serviceEvent.getServiceName());
        if(serviceInfoOptional.isPresent()){

            ServiceInfo serviceInfo = serviceInfoOptional.get();

            String serviceName = serviceInfo.getServiceName();

            synchronized (this){

                if(serviceInfo.getIsIn()){
                    removeDisposedConnections(serviceInfo,incomingServices);
                }

                if(serviceInfo.getIsOut()){
                    removeDisposedConnections(serviceInfo,outgoingServices);
                }

                if(serviceEvent.getServerInfo() != null){
                    String serviceAddress = createServiceAddress(serviceEvent.getServerInfo());
                    ipRSocketRequesterMap.remove(serviceAddress);
                    log.info(REMOVED_REQUESTER_FROM_IP,serviceAddress);
                }

            }

        } else {
            log.error(INVALID_SERVICE_EVENT,serviceEvent);
        }

    }

    @Override
    public void updateNewConnection(RSocketRequester rSocketRequester, String serviceAddress) {

        ipRSocketRequesterMap.put(serviceAddress,rSocketRequester);
        log.info(UPDATED_IP_REQUESTER_MAP_FOR_IP,serviceAddress);
    }


    @Override
    public void createStream(ServiceEvent serviceEvent) {

        log.info("############Requesting Stream....");

        try {

            String serviceName = serviceEvent.getServiceName();
            String serviceInstance = serviceEvent.getServiceInstance();
            RSocketRequester rSocketRequester = serviceEvent.getRSocketRequester();

            ServiceInfo serviceInfo = nodeInfo.getDependentService()
                    .stream()
                    .filter(serviceInfo1 -> serviceInfo1.getServiceName().equalsIgnoreCase(serviceName))
                    .findFirst()
                    .orElseThrow(()->new RuntimeException("Invalid Service Name Requested For Stream .."))
                    ;

            boolean isIn = serviceInfo.getIsIn();
            boolean isOut = serviceInfo.getIsOut();

            dispatcherService.requestForStreamForIncoming(serviceName,nodeInfo.getInstanceName(),
                    serviceInfo.getRoute(),rSocketRequester)
//                    .doOnTerminate(() -> createServiceRemovalEvent(serviceName,serviceInstance,isIn,isOut))
                    .doOnCancel(() -> createServiceRemovalEvent(serviceName,serviceInstance,isIn,isOut))
                    .subscribe();


        } catch (Exception ex) {
            ex.printStackTrace();
        }




    }

    private void createServiceRemovalEvent(String serviceName,String serviceInstance,boolean isIn,boolean isOut) {

        log.info(TRIGGERING_EVENT_REMOVAL_EVENT,serviceName,serviceInstance);
        ServiceEvent serviceEvent = ServiceEvent.builder()
                .serviceAction(ServiceAction.REMOVE)
                .serviceName(serviceName)
                .serviceInstance(serviceInstance)
                .isIn(isIn)
                .isOut(isOut)
                .build();

        sendEventForProcessing(serviceEvent);

    }

    @Override
    public void createRequestRegistration(ServiceEvent serviceEvent) {

        RSocketRequester rSocketRequester = serviceEvent.getRSocketRequester();

        byte[] serverRegistrationBytes =
                ServerRegistrationOuterClass.ServerRegistration.newBuilder()
                .setServiceName(serviceEvent.getServiceName())
                .setServiceInstance(serviceEvent.getServiceInstance())
                .build().toByteArray();

        rSocketRequester.route(CLIENT_REGISTRATION)
                .data(serverRegistrationBytes)
                .retrieveMono(byte[].class)
                .doOnNext(registration -> processRegistrationResponse(registration,serviceEvent) )
               .doOnError(throwable -> processRegistrationError(throwable,serviceEvent))
                .subscribe();


    }

    private void processRegistrationError(Throwable throwable,  ServiceEvent serviceEvent) {
        throwable.printStackTrace();

        if(serviceEvent.getRSocketRequester() != null && !serviceEvent.getRSocketRequester().rsocket().isDisposed()){
            serviceEvent.setServiceAction(ServiceAction.REQUEST_REGISTRATION_RETRY);
            sendEventForProcessing(serviceEvent);

        }


    }

    private void processRegistrationResponse(byte[] registrationBytes,
                                             ServiceEvent serviceEvent) {

        ServerRegistrationOuterClass.ServerRegistration registration =
                null;
        try {
            registration = ServerRegistrationOuterClass.ServerRegistration.parseFrom(registrationBytes);
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
            throw new RuntimeException(" Unable to Parse Server Registration ");
        }

        if (registration.getStatusReady()) {

            if(serviceEvent.isIn()){
                ServiceEvent serviceEventStream = ServiceEvent.builder()
                        .serviceName(registration.getServiceName())
                        .serviceInstance(registration.getServiceInstance())
                        .isOut(serviceEvent.isOut())
                        .isIn(serviceEvent.isIn())
                        .rSocketRequester(serviceEvent.getRSocketRequester())
                        .serviceAction(ServiceAction.REQUEST_FOR_STREAM)
                        .build();
                sendEventForProcessing(serviceEventStream);
            }

        } else {
            serviceEvent.setServiceAction(ServiceAction.REQUEST_REGISTRATION_RETRY);
            sendEventForProcessing(serviceEvent);
        }

    }

    private ServiceEvent.ServiceEventBuilder getServiceEvent(ServerRegistrationOuterClass.ServerRegistration serverRegistration,
                                                             RSocketRequester rSocketRequester) {
        return ServiceEvent.builder()
                    .serviceName(serverRegistration.getServiceName())
                    .rSocketRequester(rSocketRequester)
                    .serviceInstance(serverRegistration.getServiceInstance());
    }


    private void removeDisposedConnections(ServiceInfo serviceInfo, ConcurrentMap<String,Set<String>> services) {
        Set<String> serviceInstanceSet = services.get(serviceInfo.getServiceName());

        if(serviceInstanceSet != null) {

            serviceInstanceSet.forEach(instanceName -> {
                RSocketRequester rSocketRequester = rSocketRequesterMap.get(instanceName);


                if (rSocketRequester != null && rSocketRequester.rsocket().isDisposed()) {
                    rSocketRequesterMap.remove(instanceName);
                    serviceInstanceSet.remove(instanceName);
                    log.info(REMOVED_RSOCKET_REQUESTER_MAP,
                            instanceName,serviceInfo.getServiceName());
                }
                if(rSocketRequester == null) {
                    serviceInstanceSet.remove(instanceName);
                }

            });
        }

    }

    private void removeFromMap(ServiceInfo serviceInfo, String serviceAddress) {
        if(serviceInfo.getIsIn()){
            incomingServices.get(serviceInfo.getServiceName()).remove(serviceAddress);
        }
        if(serviceInfo.getIsOut()){
            outgoingServices.get(serviceInfo.getServiceName()).remove(serviceAddress);
        }
    }


    private String createServiceAddress(ServerInfo serverInfo) {

        return new StringBuilder()
                .append(serverInfo.getServerName())
                .append(COLON)
                .append(serverInfo.getServerPort())
                .toString();
    }

    @Override
    public void sendEventForProcessing(ServiceEvent serviceEvent) {
        serviceEventProcessor.processMessage(serviceEvent);
    }

    @Override
    public void updateDispatcherService(DispatcherService dispatcherService) {
        this.dispatcherService = dispatcherService;
    }
}
