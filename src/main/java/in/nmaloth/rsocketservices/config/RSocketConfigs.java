package in.nmaloth.rsocketservices.config;


import in.nmaloth.rsocketservices.config.model.NodeInfo;
import in.nmaloth.rsocketservices.config.model.ServiceEvent;
import in.nmaloth.rsocketservices.processor.ServiceEventProcessor;
import in.nmaloth.rsocketservices.processor.ServiceEventProcessorImpl;
import in.nmaloth.rsocketservices.service.ServiceEventsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.codec.protobuf.ProtobufDecoder;
import org.springframework.http.codec.protobuf.ProtobufEncoder;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.messaging.rsocket.RSocketStrategies;
import org.springframework.messaging.rsocket.annotation.support.RSocketMessageHandler;
import org.springframework.web.util.pattern.PathPatternRouteMatcher;
import reactor.core.publisher.ConnectableFlux;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Slf4j
@Configuration
public class RSocketConfigs {

    private final NodeInfo nodeInfo;

    public RSocketConfigs(NodeInfo nodeInfo) {

        this.nodeInfo = nodeInfo;

    }


    @Bean(BeanNames.INSTANCE_REQUESTER)
    public ConcurrentMap<String, RSocketRequester> getMapRSocketRequesterMap(){
        return new ConcurrentHashMap<>();
    }


    @Bean(BeanNames.INCOMING_SERVICE_INSTANCE)
    public ConcurrentMap<String, Set<String>> getIncomingRequester(){

        ConcurrentMap<String,Set<String>> concurrentMap = new ConcurrentHashMap<>();

        ConcurrentHashMap<String,String> concurrentSetMap = new ConcurrentHashMap<>();


        nodeInfo.getDependentService()
                .stream()
                .filter(serviceInfo -> serviceInfo.getIsIn())
                .map(serviceInfo -> serviceInfo.getServiceName())
                .forEach(serviceName -> concurrentMap.put(serviceName,concurrentSetMap.newKeySet()));

        return concurrentMap;
    }

    @Bean(BeanNames.OUTGOING_SERVICE_INSTANCE)
    public ConcurrentMap<String, Set<String>> getOutgoingRequester(){

        ConcurrentMap<String,Set<String>> concurrentMap = new ConcurrentHashMap<>();

        ConcurrentHashMap<String,String> concurrentSetMap = new ConcurrentHashMap<>();

        nodeInfo.getDependentService()
                .stream()
                .filter(serviceInfo -> serviceInfo.getIsOut())
                .map(serviceInfo -> serviceInfo.getServiceName())
                .forEach(serviceName -> concurrentMap.put(serviceName,concurrentSetMap.newKeySet()));

        return concurrentMap;
    }

    @Bean(BeanNames.ROCKET_STRATEGIES)
    public RSocketStrategies rsocketStrategies() {
        return RSocketStrategies.builder()
//                .encoders(encoders -> encoders.add(new ProtobufEncoder()))
//                .decoders(decoders -> decoders.add(new ProtobufDecoder()))
                .routeMatcher(new PathPatternRouteMatcher())
                .build();
    }

    @Bean(BeanNames.SERVER_HANDLER)
    public RSocketMessageHandler rsocketMessageHandler() {
        RSocketMessageHandler handler = new RSocketMessageHandler();

        handler.setRSocketStrategies(RSocketStrategies.builder()
                .encoders(encoders -> encoders.add(new ProtobufEncoder()))
                .decoders(decoders -> decoders.add(new ProtobufDecoder()))
                .routeMatcher(new PathPatternRouteMatcher())
                .build());

        return handler;
    }

    @Bean(BeanNames.SERVICE_EVENT_PROCESSOR)
    public ServiceEventProcessor<ServiceEvent> getServiceDiscoveryEvent(){
        return new ServiceEventProcessorImpl<>();
    }


}
