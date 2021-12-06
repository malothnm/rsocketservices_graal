package in.nmaloth.rsocketservices.processor;


import in.nmaloth.rsocketservices.listeners.MessageListener;

public interface ServiceEventProcessor<T> {

    void registerFluxListeners(MessageListener<T> messageListener);
    void processMessage(T message);
}
