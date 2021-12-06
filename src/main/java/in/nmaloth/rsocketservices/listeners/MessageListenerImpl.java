package in.nmaloth.rsocketservices.listeners;

import reactor.core.publisher.FluxSink;

public class MessageListenerImpl<T> implements MessageListener<T> {

    private FluxSink<T> fluxSink;

    public MessageListenerImpl(){ }

    public MessageListenerImpl(FluxSink<T> fluxSink){
        this.fluxSink = fluxSink;
    }


    @Override
    public void processMessage(T message) {
        fluxSink.next(message);
    }

    @Override
    public void processClose() {

        fluxSink.complete();

    }

    @Override
    public void setFluxSink(FluxSink<T> fluxSink) {

        this.fluxSink = fluxSink;
    }
}
