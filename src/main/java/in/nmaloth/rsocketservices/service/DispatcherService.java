package in.nmaloth.rsocketservices.service;

import in.nmaloth.rsocketservices.model.proto.ServerRegistrationOuterClass;
import org.springframework.messaging.rsocket.RSocketRequester;
import reactor.core.publisher.Flux;

public interface DispatcherService {


    Flux<String> requestForStreamForIncoming(String serviceName, String serviceInstance, String route,
                                                                                      RSocketRequester rSocketRequester);

    Flux<String> requestForStreamForOutgoingProcessor(RSocketRequester rSocketRequester);


}
