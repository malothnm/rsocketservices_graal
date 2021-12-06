package in.nmaloth.rsocketservices.service;

import in.nmaloth.rsocketservices.config.model.ServiceEvent;
import in.nmaloth.rsocketservices.listeners.MessageListener;
import in.nmaloth.rsocketservices.listeners.MessageListenerImpl;
import in.nmaloth.rsocketservices.processor.ServiceEventProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.Disposable;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.Random;

@Slf4j
@Service
public class ServiceEventsServiceImpl implements ServiceEventsService {


    public static final String NO_ACTION_PERFORMED = "No Action Performed ..";
    public static final String EVENT_LOGGING = " Event Logging ############## {}";

    private final ClientService clientService;
    private final ServiceTracker serviceTracker;

    public ServiceEventsServiceImpl( ClientService clientService,
                                    ServiceTracker serviceTracker) {

        this.clientService = clientService;
        this.serviceTracker = serviceTracker;
    }


    @Override
    public void processServiceEvent(ServiceEvent serviceEvent) {

        switch (serviceEvent.getServiceAction()){

            case CONNECT:{
                clientService.connectToServer(serviceEvent);
                break;
            }
            case REMOVE:{
                serviceTracker.removeRequester(serviceEvent);
                break;
            }
            case REQUEST_REGISTRATION:
            case REQUEST_REGISTRATION_RETRY:
                {
                serviceTracker.createRequestRegistration(serviceEvent);
                break;
            }
            case REQUEST_FOR_STREAM:{
                serviceTracker.createStream(serviceEvent);
                break;

            }
            default:{
                log.info(NO_ACTION_PERFORMED);
                break;
            }
        }

    }

    @Override
    public ConnectableFlux<ServiceEvent> createServiceEventFlux(ServiceEventProcessor<ServiceEvent> serviceEventProcessor){

        MessageListener<ServiceEvent> messageListener = new MessageListenerImpl<>();

        Flux<ServiceEvent> serviceEventFlux = Flux.create(fluxSink -> messageListener.setFluxSink(fluxSink) );
        serviceEventProcessor.registerFluxListeners(messageListener);

        return serviceEventFlux.publish();

    }

    @Override
    public Disposable fluxSubscriptions(ConnectableFlux<ServiceEvent> co){

        int delay = 50 + new Random().nextInt(100);
        co
                .filter(serviceEvent -> filterServiceActions(serviceEvent))
                .delayElements(Duration.ofMillis(delay))
                .doOnNext(serviceEvent -> processServiceEvent(serviceEvent))
                .onErrorContinue((throwable, o) -> {
                    log.error(o.toString());
                    throwable.printStackTrace();
                })

                .subscribe(serviceEvent -> log.info(EVENT_LOGGING,serviceEvent.toString()));

        co
                .filter(serviceEvent -> !filterServiceActions(serviceEvent))
                .doOnNext(serviceEvent -> processServiceEvent(serviceEvent))
                .onErrorContinue((throwable, o) -> {
                    log.error(o.toString());
                    throwable.printStackTrace();
                })
                .subscribe(serviceEvent -> log.info(EVENT_LOGGING,serviceEvent.toString()))
        ;

        return co.connect();
    }

    private boolean filterServiceActions(ServiceEvent serviceEvent){
        switch (serviceEvent.getServiceAction()){
            case CONNECT:
            case REMOVE:
            case DISCONNECT:
            case REQUEST_REGISTRATION_RETRY:{
                return true;
            }
            default:{
                return false;
            }
        }
    }


}
