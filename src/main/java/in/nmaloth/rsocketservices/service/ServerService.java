package in.nmaloth.rsocketservices.service;

import in.nmaloth.rsocketservices.model.proto.ServerRegistrationOuterClass;
import org.springframework.messaging.rsocket.RSocketRequester;
import reactor.core.publisher.Mono;

public interface ServerService {

    int getServerPort();

    void registerService(RSocketRequester rSocketRequester, ServerRegistrationOuterClass.ServerRegistration registration);

    void createServer();

    Mono<byte[]> clientRegistration(Mono<ServerRegistrationOuterClass.ServerRegistration> registrationMono, String appName);
}
