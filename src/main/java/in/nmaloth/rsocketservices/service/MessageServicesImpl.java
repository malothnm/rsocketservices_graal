package in.nmaloth.rsocketservices.service;

import in.nmaloth.rsocketservices.model.proto.ServerRegistrationOuterClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Slf4j
@Service
public class MessageServicesImpl implements MessageServices{

    @Override
    public Flux<String> requestForStreamMessageProcessorIn(RSocketRequester rSocketRequester, String route, ServerRegistrationOuterClass.ServerRegistration registration, String serviceName) {

        byte[] registrationBytes = registration.toByteArray();
        return rSocketRequester.route(route)
                .data(registrationBytes)
                .retrieveFlux(String.class)
                .doOnNext(s -> log.info(s))
                ;
    }

    private void logErrors(Throwable throwable, Object o) {

        throwable.printStackTrace();
        log.error(o.toString());
    }
}
