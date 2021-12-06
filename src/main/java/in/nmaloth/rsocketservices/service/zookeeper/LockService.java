package in.nmaloth.rsocketservices.service.zookeeper;

import org.apache.curator.framework.recipes.locks.InterProcessMutex;

public interface LockService {

    void createCurrentLock(String instance, String serviceName);
    void createWatchedLock(String instance, String serviceName);
    InterProcessMutex getCurrentInterProcessMutex();
    void clearCurrentInterProcessMutex();
    InterProcessMutex getWatchedProcessMutex();
    InterProcessMutex createLocke(String barrier);


}
