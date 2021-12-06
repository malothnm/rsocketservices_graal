package in.nmaloth.rsocketservices.controller;


import com.google.protobuf.InvalidProtocolBufferException;
import in.nmaloth.rsocketservices.config.model.NodeInfo;
import in.nmaloth.rsocketservices.constants.RouteConstantNames;
import in.nmaloth.rsocketservices.model.proto.ServerRegistrationOuterClass;
import in.nmaloth.rsocketservices.service.DispatcherService;
import in.nmaloth.rsocketservices.service.ServerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.messaging.rsocket.annotation.ConnectMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Controller
public class ServerMessageController {


    public static final String ENTERED_CONNECT_MAPPING = "entered connect Mapping ";
    public static final String FROM_SERVER = "From Server {}";
    public static final String STREAMING_REQUEST_FOR_SERVICE = "######## Streaming request for {} Service from Instance {}";



    private final NodeInfo nodeInfo;
    private final ServerService serverService;
    private final DispatcherService dispatcherService;

    public ServerMessageController(NodeInfo nodeInfo,
                                   ServerService serverService,
                                   DispatcherService dispatcherService) {
        this.nodeInfo = nodeInfo;
        this.serverService = serverService;
        this.dispatcherService = dispatcherService;
    }


    @ConnectMapping(RouteConstantNames.SERVER_REGISTRATION)
    public void registerOnServer(RSocketRequester rSocketRequester,
                                 byte[] registrationBytes) throws InvalidProtocolBufferException {

        ServerRegistrationOuterClass.ServerRegistration registration = ServerRegistrationOuterClass.ServerRegistration.parseFrom(registrationBytes);
        log.info(ENTERED_CONNECT_MAPPING + registration.getServiceName());


        ServerRegistrationOuterClass.ServerRegistration serverRegistration =
                ServerRegistrationOuterClass.ServerRegistration.newBuilder()
                        .setServiceName(nodeInfo.getAppName())
                        .setServiceInstance(nodeInfo.getInstanceName())
                        .build();

        log.info(FROM_SERVER,serverRegistration.getServiceName());
        serverService.registerService(rSocketRequester,registration);

    }

    @MessageMapping(RouteConstantNames.CLIENT_REGISTRATION)
    public Mono<byte[]> registerClient(Mono<byte[]> registrationBytesMono){

            Mono<ServerRegistrationOuterClass.ServerRegistration>registrationMono = registrationBytesMono
                .map(bytes -> {
                    try {
                        return ServerRegistrationOuterClass.ServerRegistration.parseFrom(bytes);
                    } catch (InvalidProtocolBufferException e) {
                        e.printStackTrace();
                        throw new RuntimeException("Not able to parse Client Registration");
                    }
                });

            return serverService.clientRegistration(registrationMono,nodeInfo.getAppName())
                    ;

    }

    @MessageMapping(RouteConstantNames.DISTRIBUTOR)
    public Flux<String> getTestFLuxDistributor(RSocketRequester rSocketRequester,
                                               byte[] registrationBytes){

        ServerRegistrationOuterClass.ServerRegistration registration;
        try {
            registration = ServerRegistrationOuterClass.ServerRegistration.parseFrom(registrationBytes);
            log.info(STREAMING_REQUEST_FOR_SERVICE,registration.getServiceName());
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }

        return dispatcherService.requestForStreamForOutgoingProcessor(rSocketRequester);


    }

    @MessageMapping(RouteConstantNames.CONNECTOR)
    public Flux<String> getTestFLux(RSocketRequester rSocketRequester,
                                    byte[] registrationBytes) throws InvalidProtocolBufferException {

        ServerRegistrationOuterClass.ServerRegistration registration =
                ServerRegistrationOuterClass.ServerRegistration.parseFrom(registrationBytes);

        log.info(STREAMING_REQUEST_FOR_SERVICE,registration.getServiceName());

        return dispatcherService.requestForStreamForOutgoingProcessor(rSocketRequester);


    }

}
