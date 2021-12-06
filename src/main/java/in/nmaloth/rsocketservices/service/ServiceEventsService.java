package in.nmaloth.rsocketservices.service;


import in.nmaloth.rsocketservices.config.model.ServiceEvent;
import in.nmaloth.rsocketservices.processor.ServiceEventProcessor;
import reactor.core.Disposable;
import reactor.core.publisher.ConnectableFlux;

public interface ServiceEventsService {

    void processServiceEvent(ServiceEvent serviceEvent);
    ConnectableFlux<ServiceEvent> createServiceEventFlux(ServiceEventProcessor<ServiceEvent> serviceEventProcessor);
    Disposable fluxSubscriptions(ConnectableFlux<ServiceEvent> co);
}
