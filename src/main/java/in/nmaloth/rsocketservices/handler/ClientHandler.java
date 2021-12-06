package in.nmaloth.rsocketservices.handler;


import com.google.protobuf.InvalidProtocolBufferException;
import in.nmaloth.rsocketservices.config.model.NodeInfo;
import in.nmaloth.rsocketservices.config.model.ServiceAction;
import in.nmaloth.rsocketservices.config.model.ServiceEvent;
import in.nmaloth.rsocketservices.config.model.ServiceInfo;
import in.nmaloth.rsocketservices.constants.RouteConstantNames;
import in.nmaloth.rsocketservices.model.proto.ServerRegistrationOuterClass;
import in.nmaloth.rsocketservices.service.DispatcherService;
import in.nmaloth.rsocketservices.service.ServiceTracker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.rsocket.RSocketRequester;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;


@Slf4j
public class ClientHandler {

    public static final String ENTERING_CLIENT_REGISTRATION = "#######Entering Client Registration for service ... ######";
    public static final String STREAMING_REQUEST_FOR_SERVICE = "######## Streaming request for {} Service from Instance {}";
    public static final String STREAMING_REQUEST_FOR_PROCESSOR = "streaming request for processor";

    private final NodeInfo nodeInfo;
    private String serviceName;
    private final ServiceTracker serviceTracker;
    private final DispatcherService dispatcherService;



    public ClientHandler(NodeInfo nodeInfo, ServiceTracker serviceTracker, DispatcherService dispatcherService) {
        this.nodeInfo = nodeInfo;
        this.serviceTracker = serviceTracker;
        this.dispatcherService = dispatcherService;
    }

    @MessageMapping(RouteConstantNames.CLIENT_REGISTRATION)
    public Mono<byte[]> registerServer(byte[] serverRegistrationBytes,RSocketRequester rSocketRequester) throws InvalidProtocolBufferException {

        log.info(ENTERING_CLIENT_REGISTRATION);

        ServerRegistrationOuterClass.ServerRegistration serverRegistration = ServerRegistrationOuterClass.ServerRegistration
                .parseFrom(serverRegistrationBytes);

        serviceTracker.processNewConnections(rSocketRequester,serverRegistration);

        prepareForRegistration(serverRegistration,rSocketRequester);

        ServerRegistrationOuterClass.ServerRegistration serverRegistration1 = ServerRegistrationOuterClass.ServerRegistration.newBuilder()
                .setServiceName(serviceName)
                .setServiceInstance(nodeInfo.getInstanceName())
                .setStatusReady(true)
                .build();

        return Mono.just(serverRegistration1.toByteArray());

    }


    @MessageMapping(RouteConstantNames.CONNECTOR)
    public Flux<String> getTestFLux(RSocketRequester rSocketRequester,
                                    byte[] registrationBytes) throws InvalidProtocolBufferException {

        ServerRegistrationOuterClass.ServerRegistration registration =
                ServerRegistrationOuterClass.ServerRegistration.parseFrom(registrationBytes);
        log.info(STREAMING_REQUEST_FOR_SERVICE,registration.getServiceName(),
                registration.getServiceInstance());

        return dispatcherService.requestForStreamForOutgoingProcessor(rSocketRequester);


    }

    @MessageMapping(RouteConstantNames.DISTRIBUTOR)
    public Flux<String> getTestFLuxDistributor(RSocketRequester rSocketRequester,
                                               byte[] registrationBytes) throws InvalidProtocolBufferException {

        ServerRegistrationOuterClass.ServerRegistration registration =
                ServerRegistrationOuterClass.ServerRegistration.parseFrom(registrationBytes);

        log.info(STREAMING_REQUEST_FOR_SERVICE,registration.getServiceName(),
                registration.getServiceInstance());

        return dispatcherService.requestForStreamForOutgoingProcessor(rSocketRequester);


    }



    private void prepareForRegistration(ServerRegistrationOuterClass.ServerRegistration serverRegistration,
                                        RSocketRequester rSocketRequester) {

        Optional<ServiceInfo> serviceInfoOptional = serviceTracker.getServiceInfo(serverRegistration.getServiceName());
        if(serviceInfoOptional.isPresent()){
            ServiceInfo serviceInfo = serviceInfoOptional.get();
            sendRegistrationEvent(rSocketRequester,serviceInfo.getIsIn(),serviceInfo.getIsOut());
        }

    }

    private void sendRegistrationEvent(RSocketRequester rSocketRequester,boolean isIn, boolean isOut) {
        ServiceEvent serviceEvent = ServiceEvent.builder()
                .serviceAction(ServiceAction.REQUEST_REGISTRATION)
                .serviceName(serviceName)
                .rSocketRequester(rSocketRequester)
                .serviceInstance(nodeInfo.getInstanceName())
                .isIn(isIn)
                .isOut(isOut)
                .build();

        serviceTracker.sendEventForProcessing(serviceEvent);
    }

    public void setAppName(String appName){
        this.serviceName = appName;
    }

    public DispatcherService dispatcherService(){
        return dispatcherService;
    }

    public ServiceTracker serviceTracker(){
        return serviceTracker;
    }

    public NodeInfo nodeInfo(){
        return nodeInfo;
    }

}
