package in.nmaloth.rsocketservices.processor;


import in.nmaloth.rsocketservices.listeners.MessageListener;

public class ServiceEventProcessorImpl<T> implements ServiceEventProcessor<T> {

    private MessageListener<T> messageListener;

    @Override
    public void registerFluxListeners(MessageListener<T> messageListener) {
        this.messageListener = messageListener;
    }

    @Override
    public void processMessage(T message) {
        messageListener.processMessage(message);
    }
}
