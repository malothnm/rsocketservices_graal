package in.nmaloth.rsocketservices.service;

import in.nmaloth.rsocketservices.model.proto.ServerRegistrationOuterClass;
import org.springframework.messaging.rsocket.RSocketRequester;
import reactor.core.publisher.Flux;

public interface MessageServices {


    Flux<String> requestForStreamMessageProcessorIn(RSocketRequester rSocketRequester, String route,
                                            ServerRegistrationOuterClass.ServerRegistration registration,
                                            String serviceName);

    }
