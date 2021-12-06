package in.nmaloth.rsocketservices.processor;


import in.nmaloth.rsocketservices.listeners.MessageListener;
import in.nmaloth.rsocketservices.processor.model.OutgoingFluxInfo;
import org.springframework.messaging.rsocket.RSocketRequester;
import reactor.core.publisher.Flux;

import java.util.List;

public interface EventOutGoingProcessor<T> {


    void registerFluxListeners(Flux<T> flux, RSocketRequester rSocketRequester, MessageListener<T> messageListener, String serviceInstance);

    void removeRegisteredFluxListener(RSocketRequester rSocketRequester);

    void processMessage(T message);
    boolean processMessage(T message,String instance);


    boolean processMessage(T message,RSocketRequester rSocketRequester);

    List<T> getTestMessage();
    void setTestMode(boolean testMode);

    List<OutgoingFluxInfo> getOutgoingFluxInfo();


}
