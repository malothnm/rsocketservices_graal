package in.nmaloth.rsocketservices.service.zookeeper;

import in.nmaloth.rsocketservices.service.zookeeper.constants.Constants;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.x.async.AsyncCuratorFramework;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Slf4j
@Service
public class LockServiceImpl implements LockService {

    private final CuratorFramework curatorFramework;

    private ConcurrentMap<String,InterProcessMutex> interProcessMutexMap = new ConcurrentHashMap<>();

    public LockServiceImpl(AsyncCuratorFramework asyncCuratorFramework) {

        this.curatorFramework = asyncCuratorFramework.unwrap();
    }


    private void createLock(String instance, String serviceName, boolean isCurrent){

        String path = new StringBuilder()
                .append(Constants.BARRIER)
                .append(Constants.SLASH)
                .append(serviceName)
                .append(Constants.SLASH)
                .append(instance)
                .toString();

        InterProcessMutex interProcessMutex =  new InterProcessMutex(curatorFramework,path);
        if(isCurrent){
            interProcessMutexMap.put(Constants.CURR,interProcessMutex);
        } else {
            interProcessMutexMap.put(Constants.WATCH,interProcessMutex);
        }
    }

    @Override
    public void createCurrentLock(String instance, String serviceName) {

        createLock(instance,serviceName,true);
    }

    @Override
    public void createWatchedLock(String instance, String serviceName) {
        createLock(instance,serviceName,false);
    }

    @Override
    public InterProcessMutex getCurrentInterProcessMutex() {
        return interProcessMutexMap.get(Constants.CURR);
    }

    @Override
    public void clearCurrentInterProcessMutex() {

        interProcessMutexMap.remove(Constants.WATCH);

    }

    @Override
    public InterProcessMutex getWatchedProcessMutex() {
        return interProcessMutexMap.get(Constants.WATCH);
    }

    @Override
    public InterProcessMutex createLocke(String barrier) {
        return new InterProcessMutex(curatorFramework,barrier);
    }


}
