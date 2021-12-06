package in.nmaloth.rsocketservices.service.zookeeper;

public interface LeaderElection {

    void volunteerForLeaderElection() throws Exception;
    void validateAndCreateNodes() throws Exception;
    void electLeader() throws Exception;

    void checkForLaterNode();

    boolean isLeader();
    boolean isLastNode();
    String getWatchedNode();
    String getWatchedNodeNumber();

    String getAppBasePath();
    String getCurrentNode();
    String getCurrentNodeNumber();
    String getLaterNode();
    String getLaterNodeNumber();






}
