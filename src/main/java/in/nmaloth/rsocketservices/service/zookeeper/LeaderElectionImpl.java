package in.nmaloth.rsocketservices.service.zookeeper;


import in.nmaloth.rsocketservices.config.model.NodeInfo;
import in.nmaloth.rsocketservices.model.proto.zookeeper.ZnodeInfoOuterClass;
import in.nmaloth.rsocketservices.service.zookeeper.constants.Constants;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.x.async.AsyncCuratorFramework;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class LeaderElectionImpl implements LeaderElection {

    public static final String UNABLE_TO_CREATE_NODE_IN_ZOOKEEPER_FOR = " Unable to create node in zookeeper for ";
    public static final String I_AM_THE_LEADER = " I am the leader {}";
    public static final String INSTANCE_NODE_IS_WATCHING_NODE_LATER_NODE_IS = "instance Node is {} watching node {} later Node is {} ";
    public static final String INVALID_PATH_LIST_ = "Invalid Path List ";
    private boolean isLeader = false;
    private boolean isLastNode = true;


    private final CuratorFramework client;
    private final NodeInfo nodeInfo;

    private String basePath;
    private String instanceNode;
    private String watchedNode;
    private String laterNode;

    @Value("${spring.application.name}")
    private String appName;


    public LeaderElectionImpl(AsyncCuratorFramework async, NodeInfo nodeInfo) {
        this.client = async.unwrap();
        this.nodeInfo = nodeInfo;
    }


    @Override
    public void volunteerForLeaderElection() throws Exception {




        ZnodeInfoOuterClass.ZnodeInfo zNodeInfo = ZnodeInfoOuterClass.ZnodeInfo.newBuilder()
                .setInstanceName(nodeInfo.getInstanceName())
                .setServiceName(appName)
                .build()

                ;

        String currentNode = client.create()
                .withMode(CreateMode.EPHEMERAL_SEQUENTIAL)
                .forPath(basePath,zNodeInfo.toByteArray());

        if (currentNode == null) {
            throw new RuntimeException(UNABLE_TO_CREATE_NODE_IN_ZOOKEEPER_FOR + basePath);
        }

        instanceNode = currentNode.replace(basePath, Constants.EMPTY);

    }

    @Override
    public void electLeader() throws Exception {

        Stat stat = null;

        String path = basePath.replace(Constants.CHILD_PREFIX, Constants.EMPTY);

        while (stat == null) {

            List<String> pathNumbersList = getSortedChildrenList(path);
            if (pathNumbersList.get(0).equals(instanceNode)) {
                isLeader = true;
                log.info(I_AM_THE_LEADER, instanceNode);

                if(pathNumbersList.size() > 1 ){
                    laterNode = pathNumbersList.get(1);
                    isLastNode = false;
                } else  {
                    isLastNode = true;
                    laterNode = null;
                }
                return;
            } else {
                isLeader = false;
                int index = Collections.binarySearch(pathNumbersList, instanceNode);
                watchedNode = pathNumbersList.get(index - 1);
                String watchedPath = new StringBuilder().append(basePath).append(watchedNode).toString();
                stat = client.checkExists().forPath(watchedPath);

                if(pathNumbersList.size() > index + 1 ){
                    isLastNode = false;
                    laterNode = pathNumbersList.get(index + 1);
                } else {
                    isLastNode = true;
                    laterNode = null;
                }
            }
        }
        log.info(INSTANCE_NODE_IS_WATCHING_NODE_LATER_NODE_IS, instanceNode, watchedNode, laterNode);
    }

    @Override
    public void checkForLaterNode() {

        Stat stat = null;
        String path = basePath.replace(Constants.CHILD_PREFIX, Constants.EMPTY);

        while (stat == null){

            try {
                List<String> pathNumbersList = getSortedChildrenList(path);
                int index = Collections.binarySearch(pathNumbersList,instanceNode);
                if(index + 1 == (pathNumbersList.size() )) {

                    laterNode = null;
                    isLastNode = true;
                    return;
                } else {
                    laterNode = pathNumbersList.get(index + 1);
                    isLastNode = false;
                    String laterPath = new StringBuilder().append(basePath).append(laterNode)
                            .toString();
                    stat = client.checkExists()
                            .forPath(laterPath);

                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    private List<String> getSortedChildrenList(String path) throws Exception {

        List<String> pathList = client.getChildren()
                .forPath(path);

        if (pathList == null || pathList.size() == 0) {
            log.info(INVALID_PATH_LIST_);
            throw new RuntimeException(INVALID_PATH_LIST_);
        }

        String childIndicator = Constants.CHILD_PREFIX.replace(Constants.SLASH, Constants.EMPTY);
        List<String> pathNumbersList = pathList.stream()
                .filter(s -> !s.equals(childIndicator))
                .map(s -> s.replace(childIndicator, Constants.EMPTY))
                .collect(Collectors.toList());

        Collections.sort(pathNumbersList);

        return pathNumbersList;

    }

    @Override
    public boolean isLeader() {
        return isLeader;
    }

    @Override
    public boolean isLastNode() {

        return isLastNode;
    }

    @Override
    public String getWatchedNode() {
        return new StringBuilder().append(basePath).append(watchedNode).toString();
    }

    @Override
    public String getWatchedNodeNumber() {
        return watchedNode;
    }

    @Override
    public String getAppBasePath() {
        return basePath.replace(Constants.CHILD_PREFIX, Constants.EMPTY);
    }

    @Override
    public String getCurrentNode() {
        return new StringBuilder().append(basePath).append(instanceNode).toString();
    }

    @Override
    public String getCurrentNodeNumber() {
        return instanceNode;
    }

    @Override
    public String getLaterNode() {
        return new StringBuilder().append(basePath).append(laterNode).toString();
    }

    @Override
    public String getLaterNodeNumber() {
        return laterNode;
    }


    public void validateAndCreateNodes() throws Exception {

        basePath = new StringBuilder()
                .append(Constants.LEADER_NODE)
                .append(Constants.SLASH)
                .append(appName)
                .append(Constants.CHILD_PREFIX)
                .toString()
        ;

        validateAndCreateNodes(appName);

        nodeInfo.getDependentService()
                .stream()
                .map(serviceInfo -> serviceInfo.getServiceName())
                .forEach(serviceName -> validateAndCreateNodes(serviceName));

    }


    private void validateAndCreateNodes(String appName) {

        StringBuilder sb = new StringBuilder();
        String appPath = sb.append(Constants.LEADER_NODE)
                .append(Constants.SLASH)
                .append(appName)
                .toString();


        Stat stat = null;
        try {
            stat = client.checkExists()
                    .forPath(Constants.LEADER_NODE);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (stat == null) {
            try {
                client.create()
                        .forPath(Constants.LEADER_NODE);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        Stat statApp = null;
        try {
            statApp = client.checkExists()
                    .forPath(appPath);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (statApp == null) {
            try {
                client.create()
                        .forPath(appPath);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        String childPath = sb.append(Constants.CHILD_PREFIX).toString();

        Stat childStat = null;
        try {
            childStat = client.checkExists()
                    .forPath(childPath);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (childStat == null) {
            try {
                client.create()
                        .forPath(childPath);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}
