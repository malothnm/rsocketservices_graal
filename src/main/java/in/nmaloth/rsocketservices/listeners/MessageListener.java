package in.nmaloth.rsocketservices.listeners;

import reactor.core.publisher.FluxSink;

public interface MessageListener<T> {

    void processMessage(T message);
    void processClose();
    void setFluxSink(FluxSink<T> fluxSink);
}
