package in.nmaloth.rsocketservices.service;


import in.nmaloth.rsocketservices.config.model.ServerInfo;
import in.nmaloth.rsocketservices.config.model.ServiceEvent;
import in.nmaloth.rsocketservices.config.model.ServiceInfo;
import in.nmaloth.rsocketservices.model.proto.ServerRegistrationOuterClass;
import org.springframework.messaging.rsocket.RSocketRequester;

import java.util.Optional;

public interface ServiceTracker {

    Optional<ServiceInfo> getServiceInfo(String serviceName);
    Optional<String> verifyServiceAddress(ServiceEvent serviceEvent, boolean isIn, boolean isOut);

    boolean checkForInactiveRequester(ServerInfo serverInfo);

    void processNewConnections(RSocketRequester rSocketRequester, ServerRegistrationOuterClass.ServerRegistration serverRegistration);
    void removeRequester(ServiceEvent serviceEvent);

    void updateNewConnection(RSocketRequester rSocketRequester, String serviceAddress);


    void createStream(ServiceEvent serviceEvent);

    void createRequestRegistration(ServiceEvent serviceEvent);

    void sendEventForProcessing(ServiceEvent serviceEvent);
    void updateDispatcherService(DispatcherService dispatcherService);
}
