package in.nmaloth.rsocketservices.service.zookeeper;

public interface CoordinationService {


    void watchDependentNodes();
    void watchPreviousNode();
    void addEventChildrenWatcher(String path);
    void addEventDataWatcher(String path);
    void addEventExistsWatcher(String path);
    void houseKeeping() throws Exception;

    void setWatchers();
}
