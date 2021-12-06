package in.nmaloth.rsocketservices.bootstrap;

import in.nmaloth.rsocketservices.config.BeanNames;
import in.nmaloth.rsocketservices.config.model.NodeInfo;
import in.nmaloth.rsocketservices.config.model.ServiceEvent;
import in.nmaloth.rsocketservices.handler.ClientHandler;
import in.nmaloth.rsocketservices.processor.ServiceEventProcessor;
import in.nmaloth.rsocketservices.service.*;
import in.nmaloth.rsocketservices.service.zookeeper.CoordinationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import reactor.core.publisher.ConnectableFlux;

@Component
@Slf4j
public class Startup implements CommandLineRunner {


    private final ServerService serverService;
    private final DispatcherService dispatcherService;
    private final ServiceTracker serviceTracker;
    private final ClientService clientService;
    private final NodeInfo nodeInfo;
    private final ServiceEventsService serviceEventsService;
    private final ServiceEventProcessor<ServiceEvent> serviceEventProcessor;
    private final CoordinationService coordinationService;


    private ConnectableFlux<ServiceEvent> connectableFlux;

    public Startup(ServerService serverService,
                   DispatcherService dispatcherService,
                   ServiceTracker serviceTracker,
                   ClientService clientService,
                   NodeInfo nodeInfo,
                   ServiceEventsService serviceEventsService,
                   @Qualifier(BeanNames.SERVICE_EVENT_PROCESSOR) ServiceEventProcessor<ServiceEvent> serviceEventProcessor, CoordinationService coordinationService) {

        this.serverService = serverService;
        this.dispatcherService = dispatcherService;
        this.serviceTracker = serviceTracker;
        this.clientService = clientService;
        this.nodeInfo = nodeInfo;
        this.serviceEventsService = serviceEventsService;
        this.serviceEventProcessor = serviceEventProcessor;
        this.coordinationService = coordinationService;
    }


    @Override
    public void run(String... args) throws Exception {

        connectableFlux = serviceEventsService.createServiceEventFlux(serviceEventProcessor);
        log.info(" Updating dispatcher Service");
        serviceTracker.updateDispatcherService(dispatcherService);
        clientService.updateClientHandler(new ClientHandler(nodeInfo,serviceTracker,dispatcherService));
        serverService.createServer();
        serviceEventsService.fluxSubscriptions(connectableFlux);
        coordinationService.houseKeeping();
        coordinationService.setWatchers();

    }
}
