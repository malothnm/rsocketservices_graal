package in.nmaloth.rsocketservices.service;


import in.nmaloth.rsocketservices.config.BeanNames;
import in.nmaloth.rsocketservices.config.model.NodeInfo;
import in.nmaloth.rsocketservices.config.model.ServiceAction;
import in.nmaloth.rsocketservices.config.model.ServiceEvent;
import in.nmaloth.rsocketservices.config.model.ServiceInfo;
import in.nmaloth.rsocketservices.model.proto.ServerRegistrationOuterClass;
import io.rsocket.core.RSocketServer;
import io.rsocket.transport.netty.server.CloseableChannel;
import io.rsocket.transport.netty.server.TcpServerTransport;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.messaging.rsocket.RSocketStrategies;
import org.springframework.messaging.rsocket.annotation.support.RSocketMessageHandler;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Slf4j
@Service
public class ServerServiceImpl implements ServerService {


    public static final String INVALID_SERVICE_NAME = "Invalid Service Name ";
    public static final String BOUND_TO_PORT = " BOUND to Port {} ";
    public static final String CREATED_SERVER = "#########  Created Server";


    private final NodeInfo nodeInfo;
    private final ServiceTracker serviceTracker;
    private final RSocketStrategies socketStrategies;
    private final RSocketMessageHandler handler;




    public ServerServiceImpl(NodeInfo nodeInfo,
                             ServiceTracker serviceTracker,
                             @Qualifier(BeanNames.ROCKET_STRATEGIES)RSocketStrategies socketStrategies,
                             @Qualifier(BeanNames.SERVER_HANDLER) RSocketMessageHandler handler) {
        this.nodeInfo = nodeInfo;
        this.serviceTracker = serviceTracker;
        this.socketStrategies = socketStrategies;
        this.handler = handler;
    }

    @Override
    public int getServerPort() {
        if (nodeInfo.getServerPort() != null) {
            return nodeInfo.getServerPort();
        } else {
            return 0;
        }
    }

    @Override
    public void registerService(RSocketRequester rSocketRequester, ServerRegistrationOuterClass.ServerRegistration registration) {

        serviceTracker.processNewConnections(rSocketRequester, registration);

        Optional<ServiceInfo> serviceInfoOptional = serviceTracker.getServiceInfo(registration.getServiceName());
        if (serviceInfoOptional.isPresent()) {

            ServiceInfo serviceInfo = serviceInfoOptional.get();
            sendRegistrationEvent(rSocketRequester,serviceInfo.getIsIn(),serviceInfo.getIsOut());

        } else {
            throw new RuntimeException(INVALID_SERVICE_NAME + registration.getServiceName());
        }

    }

    @Override
    public void createServer() {

        if(nodeInfo.getServerPort() != null){

            CloseableChannel server =
                    RSocketServer.create(handler.responder())
                            .bind(TcpServerTransport.create(nodeInfo.getServerPort()))
                            .doOnNext(closeableChannel -> log.info(BOUND_TO_PORT,nodeInfo.getServerPort()))
                            .block();

            log.info(CREATED_SERVER);
        }

    }

    @Override
    public Mono<byte[]> clientRegistration(Mono<ServerRegistrationOuterClass.ServerRegistration> registrationMono, String appName) {

        byte[] registrationBytes = ServerRegistrationOuterClass.ServerRegistration.newBuilder()
                .setStatusReady(true)
                .setServiceInstance(nodeInfo.getInstanceName())
                .setServiceName(appName)
                .build()
                .toByteArray()
        ;

        return registrationMono.map(registration -> registrationBytes
                );

    }

    private void sendRegistrationEvent(RSocketRequester rSocketRequester,boolean isIn, boolean isOut) {
        ServiceEvent serviceEvent = ServiceEvent.builder()
                .serviceAction(ServiceAction.REQUEST_REGISTRATION)
                .serviceName(nodeInfo.getAppName())
                .rSocketRequester(rSocketRequester)
                .serviceInstance(nodeInfo.getInstanceName())
                .isIn(isIn)
                .isOut(isOut)
                .build();

        serviceTracker.sendEventForProcessing(serviceEvent);
    }
}
