package in.nmaloth.rsocketservices.service;


import in.nmaloth.rsocketservices.config.BeanNames;
import in.nmaloth.rsocketservices.config.model.NodeInfo;
import in.nmaloth.rsocketservices.config.model.ServiceEvent;
import in.nmaloth.rsocketservices.config.model.ServiceInfo;
import in.nmaloth.rsocketservices.handler.ClientHandler;
import in.nmaloth.rsocketservices.model.proto.ServerRegistrationOuterClass;
import io.rsocket.SocketAcceptor;
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
public class ClientServiceImpl implements ClientService {

    public static final String SERVER_REGISTRATION = "server.registration";
    public static final String SERVICE_ADDRESS_ALREADY_PRESENT = "Service Address already present {} ";
    public static final String CANCELED_FOR_ADDRESS_SERVICE = "Canceled ...for address {} , Service {} ";
    public static final String CLOSED_FOR_ADDRESS_SERVICE = "Closed for address {} , Service {} ";
    public static final String INVALID_SERVICE_REQUESTED_TO_CONNECT = " Invalid Service requested to connect {} ";
    public static final String CONTACT_ADMINS_TO_CHECK = "Contact Admins to check";


    private final RSocketStrategies rSocketStrategies;
    private final RSocketRequester.Builder builder;
    private final ServiceTracker serviceTracker;
    private ClientHandler clientHandler;



    private final NodeInfo nodeInfo;

    public ClientServiceImpl( @Qualifier(BeanNames.ROCKET_STRATEGIES) RSocketStrategies rSocketStrategies,
                             RSocketRequester.Builder builder,
                              ServiceTracker serviceTracker,
                              NodeInfo nodeInfo) {

        this.rSocketStrategies = rSocketStrategies;
        this.builder = builder;
        this.serviceTracker = serviceTracker;
        this.nodeInfo = nodeInfo;

    }


    @Override
    public void connectToServer(ServiceEvent serviceEvent) {

        Optional<ServiceInfo> serviceInfoOptional =  serviceTracker.getServiceInfo(serviceEvent.getServiceName());

        if(serviceInfoOptional.isPresent()){
            ServiceInfo serviceInfo = serviceInfoOptional.get();
            createServerConnection(serviceEvent,serviceInfo);


        } else {
            log.error(INVALID_SERVICE_REQUESTED_TO_CONNECT,serviceEvent );
            log.error(CONTACT_ADMINS_TO_CHECK);
        }

    }

    @Override
    public void updateClientHandler(ClientHandler clientHandler) {
        this.clientHandler = clientHandler;
    }


    private Mono<RSocketRequester> createSocketRequester(ServiceEvent serviceEvent){


        clientHandler.setAppName(nodeInfo.getAppName());
        SocketAcceptor socketAcceptor = RSocketMessageHandler.responder(rSocketStrategies, clientHandler);

        byte[] registrationData = ServerRegistrationOuterClass.ServerRegistration.newBuilder()
                .setServiceName(nodeInfo.getAppName())
                .setServiceInstance(nodeInfo.getInstanceName())
                .build().toByteArray();


        return builder
                .rsocketStrategies(rSocketStrategies)
                .rsocketConnector(connector -> connector.acceptor(socketAcceptor))
                .setupRoute(SERVER_REGISTRATION)
                .setupData(registrationData
                )
                .connectTcp(serviceEvent.getServerInfo().getServerName(), serviceEvent.getServerInfo().getServerPort());

    }

    private void createServerConnection(ServiceEvent serviceEvent, ServiceInfo serviceInfo) {


        Optional<String> serviceAddressOptional = serviceTracker.verifyServiceAddress(serviceEvent,serviceInfo.getIsIn(),serviceInfo.getIsOut());
        if(serviceAddressOptional.isEmpty()){
            log.info(SERVICE_ADDRESS_ALREADY_PRESENT,serviceEvent.getServerInfo().toString());
            return;
        }

        String serviceAddress = serviceAddressOptional.get();

        createSocketRequester(serviceEvent)
                .doOnNext(rSocketRequester -> serviceTracker.updateNewConnection(rSocketRequester,serviceAddress))
                .doOnError(throwable -> throwable.printStackTrace())
                .doOnCancel(() -> log.info(CANCELED_FOR_ADDRESS_SERVICE, serviceAddress,serviceEvent.getServiceName()))
                .doFinally(signalType -> log.info(CLOSED_FOR_ADDRESS_SERVICE,serviceAddress,serviceEvent.getServiceName()))
                .subscribe();

    }


}
