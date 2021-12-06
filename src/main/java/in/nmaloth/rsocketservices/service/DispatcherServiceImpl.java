package in.nmaloth.rsocketservices.service;

import in.nmaloth.rsocketservices.config.model.NodeInfo;
import in.nmaloth.rsocketservices.model.proto.ServerRegistrationOuterClass;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.stream.Stream;

@Service
public class DispatcherServiceImpl implements DispatcherService{

    private final NodeInfo nodeInfo;
    private final MessageServices messageServices;

    public DispatcherServiceImpl(NodeInfo nodeInfo, MessageServices messageServices) {
        this.nodeInfo = nodeInfo;
        this.messageServices = messageServices;
    }


    @Override
    public Flux<String> requestForStreamForIncoming(String serviceName, String serviceInstance, String route, RSocketRequester rSocketRequester) {

        ServerRegistrationOuterClass.ServerRegistration registration = ServerRegistrationOuterClass.ServerRegistration.newBuilder()
                .setServiceName(nodeInfo.getAppName())
                .setServiceInstance(serviceInstance)
                .setStatusReady(true)
                .build();

        return messageServices.requestForStreamMessageProcessorIn(rSocketRequester,route,registration,serviceName);
    }

    @Override
    public Flux<String> requestForStreamForOutgoingProcessor(RSocketRequester rSocketRequester) {

        Stream<Integer> stream = Stream.iterate(0,(i)-> i + 1);

        return Flux.fromStream(stream)
                .delayElements(Duration.ofSeconds(1))
                .map(integer -> "This is test " + nodeInfo.getAppName() +  integer);    }
}
