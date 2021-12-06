package in.nmaloth.rsocketservices.processor.model;

import in.nmaloth.rsocketservices.listeners.MessageListener;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.messaging.rsocket.RSocketRequester;
import reactor.core.publisher.Flux;

import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class OutgoingFluxInfo<T> {

    private Flux<T> outgoingFlux;
    private RSocketRequester rSocketRequester;
    private MessageListener<T> outGoingListener;
    private String serviceInstance;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OutgoingFluxInfo)) return false;
        OutgoingFluxInfo<?> that = (OutgoingFluxInfo<?>) o;
        return rSocketRequester.equals(that.rSocketRequester);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rSocketRequester);
    }
}
