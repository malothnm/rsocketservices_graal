package in.nmaloth.rsocketservices.processor;



import in.nmaloth.rsocketservices.listeners.MessageListener;
import in.nmaloth.rsocketservices.processor.model.OutgoingFluxInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.rsocket.RSocketRequester;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
public class EventOutGoingProcessorImpl<T>  implements EventOutGoingProcessor<T> {

    private List<OutgoingFluxInfo> outgoingFluxInfoList = new ArrayList<>();
    private AtomicLong roundRobin = new AtomicLong(0L);
    private boolean testMode = false;
    private List<T> testList = new ArrayList<>();


    @Override
    public void registerFluxListeners(Flux<T> flux, RSocketRequester rSocketRequester, MessageListener<T> messageListener, String serviceInstance) {

        outgoingFluxInfoList.add(new OutgoingFluxInfo(flux,rSocketRequester,messageListener,serviceInstance));


    }

    @Override
    public void removeRegisteredFluxListener(RSocketRequester rSocketRequester) {

        outgoingFluxInfoList.remove(new OutgoingFluxInfo(null,rSocketRequester,null,null));

    }

    @Override
    public void processMessage(T message) {

        if(testMode){
            testList.add(message);
            return;
        }

        if(message instanceof byte[]){
            log.info("Message at outgoing flux is :  " +  String.valueOf(message));
        }
        OutgoingFluxInfo<T> outgoingFluxInfo;
        do {
            outgoingFluxInfo = selectListener();

            if(outgoingFluxInfo != null){
                try {
                    outgoingFluxInfo.getOutGoingListener().processMessage(message);
                } catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        }
        while ( outgoingFluxInfo != null && outgoingFluxInfo.getRSocketRequester()!= null &&
                outgoingFluxInfo.getRSocketRequester().rsocket().isDisposed());

        if(outgoingFluxInfo == null){
            log.info(" No Active Flux ...");

            // Strategy to Write to  Geode backup
        }


    }

    @Override
    public boolean processMessage(T message, String instance) {

        Optional<RSocketRequester> optionalRSocketRequester = findRSocketRequester(instance);
        if(optionalRSocketRequester.isPresent()){
            return processMessage(message,optionalRSocketRequester.get());
        } else {
            return false;
        }
    }

    private Optional<RSocketRequester> findRSocketRequester(String instance){

        for (OutgoingFluxInfo<T> outgoingFluxInfo:outgoingFluxInfoList) {
            if(outgoingFluxInfo.getServiceInstance().equals(instance)){
                return Optional.of(outgoingFluxInfo.getRSocketRequester());
            }
        }
        return Optional.empty();
    }


    @Override
    public boolean processMessage(T message,RSocketRequester rSocketRequester) {


        if(testMode){
            testList.add(message);
            return true;
        }

        if(rSocketRequester == null){
            return false;
        }

        if(rSocketRequester.rsocket().isDisposed()){
            return false;
        }

        Optional<OutgoingFluxInfo<T>> outgoingFluxInfoOptional = fetchOutgoingFLuxInfo(rSocketRequester);

        if(outgoingFluxInfoOptional.isEmpty()){
            return false;
        }

        if(outgoingFluxInfoOptional.get().getRSocketRequester().rsocket().isDisposed()){
            return false;
        }

        try {
            MessageListener<T> messageListener = outgoingFluxInfoOptional.get().getOutGoingListener();
            messageListener.processMessage(message);
            return true;

        } catch (Exception ex){
            ex.printStackTrace();
            return false;
        }

    }

    @Override
    public List<T> getTestMessage() {
        return testList;
    }

    @Override
    public void setTestMode(boolean testMode) {
        this.testMode = testMode;
    }

    @Override
    public List<OutgoingFluxInfo> getOutgoingFluxInfo() {
        return outgoingFluxInfoList;
    }

    private Optional<OutgoingFluxInfo<T>> fetchOutgoingFLuxInfo(RSocketRequester rSocketRequester) {

        Optional<OutgoingFluxInfo<T>> outgoingFluxInfoOptional = Optional.empty();
        for (OutgoingFluxInfo<T>  outgoingfluxInfo:outgoingFluxInfoList ) {

            if(outgoingfluxInfo.getRSocketRequester().equals(rSocketRequester)){
                outgoingFluxInfoOptional = Optional.of(outgoingfluxInfo);
                break;
            }
        }
        return outgoingFluxInfoOptional;
    }


    private OutgoingFluxInfo<T> selectListener(){
        int size = outgoingFluxInfoList.size();
        if(size > 0 ){
            long roundRobin = this.roundRobin.incrementAndGet();
            int selectedListener = (int) roundRobin%size;
            return outgoingFluxInfoList.get(selectedListener);
        }
        return null;
    }

}
