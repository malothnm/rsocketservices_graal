package in.nmaloth.rsocketservices.service.zookeeper;

import com.google.protobuf.InvalidProtocolBufferException;
import in.nmaloth.rsocketservices.config.model.NodeInfo;
import in.nmaloth.rsocketservices.config.model.ServerInfo;
import in.nmaloth.rsocketservices.config.model.ServiceAction;
import in.nmaloth.rsocketservices.config.model.ServiceEvent;
import in.nmaloth.rsocketservices.model.proto.zookeeper.ZnodeInfoOuterClass;
import in.nmaloth.rsocketservices.processor.ServiceEventProcessor;
import in.nmaloth.rsocketservices.service.ServiceTracker;
import in.nmaloth.rsocketservices.service.zookeeper.constants.Constants;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.x.async.AsyncCuratorFramework;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.data.Stat;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

@Slf4j
@Service
public class CoordinationServiceImpl implements CoordinationService {


    public static final String SERVER_INFO = "##### Server Info : {}";
    public static final String ENTERED_HERE = "Entered here ....";
    public static final String CHILDREN_CHANGED_EVENT = " Children Changed Event {} ";
    public static final String HASH_MESSAGE = "###########";
    public static final String UNKNOWN_EVENT = " Unknown Event {} ";
    public static final String $$$$$$$$$$_ENTERED_DATA_WATCHER = "$$$$$$$$$$Entered  Data Watcher ...";
    public static final String NODE_DELETED = " Node deleted ..";
    public static final String UNKNOWN_EVENT_TYPE_FOR_DATA_CHANGED = " Unknown event type for data changed , {}";
    public static final String DATA_RECIEVED_ON_DEPENDENT_NODE = " Data Recieved on Dependent Node : {}";
    public static final String NOT_ABLE_TO_UPDATE_THE_WATCHED_NODE = " Not able to update the watched Node {}";
    public static final String UPDATED_THE_WATCHED_NODE_FOR_PATH = "Updated the watched Node  for path {}";
    public static final String NODE_DELETED_FOR = " Node Deleted for ...{}";
    public static final String UNKNOWN_EVENT_FOR_NODE_EXISTS_FOR = " Unknown event for node exists for ...{} ";
    public static final String ENTERING_WATCHING_DEPEND_NODES = " Entering watching depend Nodes ";
    public static final String ENTERING_WATCHING_PREVIOUS_NODES = "Entering watching previous Nodes";
    public static final String UNABLE_TO_SET_DEPENDENT_SERVICE_DATA_FOR = " Unable to set dependent service data for {}";
    public static final String DEPENDENT_SERVICE_UPDATED_FOR_DISCOVERY_REQUEST = "dependent service {} updated  for discovery request ";
    public static final String ZNODE_INFO_RECEIVED = " ZNode Info  received : {}";
    public static final String DEPENDENT_SERVICES = "Dependent services {}";

    private final AsyncCuratorFramework async;
    private final CuratorFramework curatorFramework;
    private final LeaderElection leaderElection;
    private final DependentServiceDiscovery dependentServiceDiscovery;
    private final NodeInfo nodeInfo;
    private final ServiceEventProcessor<ServiceEvent> serviceEventProcessor;
    private final ConcurrentMap<String, ConcurrentMap<ServerInfo, String>> serviceInfoMap;
    private final ServiceTracker serviceTracker;
    private final LockService lockService;





    public CoordinationServiceImpl(AsyncCuratorFramework async,  LeaderElection leaderElection,
                                   DependentServiceDiscovery dependentServiceDiscovery, NodeInfo nodeInfo,
                                   ServiceEventProcessor<ServiceEvent> serviceEventProcessor,
                                   ConcurrentMap<String, ConcurrentMap<ServerInfo, String>> serviceInfoMap,
                                   ServiceTracker serviceTracker, LockService lockService) {

        this.async = async;
        this.curatorFramework = async.unwrap();
        this.leaderElection = leaderElection;
        this.dependentServiceDiscovery = dependentServiceDiscovery;
        this.nodeInfo = nodeInfo;
        this.serviceEventProcessor = serviceEventProcessor;
        this.serviceInfoMap = serviceInfoMap;
        this.serviceTracker = serviceTracker;
        this.lockService = lockService;
    }


    @Override
    public void watchDependentNodes() {

        getDependentServerServiceStream()
                .map(serviceName -> new StringBuilder()
                        .append(Constants.LEADER_NODE)
                        .append(Constants.SLASH)
                        .append(serviceName)
                        .toString())
                .forEach(servicePath ->
                {
                    log.info(DEPENDENT_SERVICES, servicePath);
                    addEventChildrenWatcher(servicePath);
                });

    }

    private void findDependentServiceIp(String serviceName) {

        List<ServerInfo> serverInfoList = fetchDependentService(serviceName);

        serverInfoList.forEach(serverInfo -> log.info(SERVER_INFO, serverInfo.toString()));

        synchronized (this) {


            ConcurrentMap<ServerInfo, String> serviceServerInfoMap = serviceInfoMap.get(serviceName);

            if (serviceServerInfoMap == null) {

                log.debug(ENTERED_HERE);

                ConcurrentMap<ServerInfo, String> createdServiceServerInfoMap = new ConcurrentHashMap<>();
                serverInfoList.forEach(serverInfo -> {

                    serverInfo.setConnected(false);
                    sendServiceEvent(serviceName, serverInfo, ServiceAction.CONNECT);
                    createdServiceServerInfoMap.put(serverInfo, serverInfo.getServerName());
                    updateNodeCurrentNode(serviceName);


                });
                serviceInfoMap.put(serviceName, createdServiceServerInfoMap);

            } else {

                serviceServerInfoMap.entrySet()
                        .stream()
                        .map(stringServerInfoEntry -> stringServerInfoEntry.getKey())
                        .forEach(serverInfo -> {

                            if (!serverInfoList.contains(serverInfo) ||
                                    serviceTracker.checkForInactiveRequester(serverInfo)) {
                                serviceServerInfoMap.remove(serverInfo);
                                sendServiceEvent(serviceName, serverInfo, ServiceAction.REMOVE);
                                updateNodeCurrentNode(serviceName);
                            }

                        });

                serverInfoList.stream()
                        .filter(serverInfo -> !serviceServerInfoMap.containsKey(serverInfo))
                        .forEach(serverInfo -> {
                            serviceServerInfoMap.put(serverInfo, serverInfo.getServerName());
                            serverInfo.setConnected(false);
                            sendServiceEvent(serviceName, serverInfo, ServiceAction.CONNECT);
                            updateNodeCurrentNode(serviceName);
                        });
            }

        }
    }

    private List<ServerInfo> fetchDependentService(String serviceName){

        List<ServerInfo> serverInfoList = null;
        try {
            serverInfoList = dependentServiceDiscovery.getIPDetails(serviceName);
        } catch (Exception ex) {
            ex.printStackTrace();
            serverInfoList = new ArrayList<>();
        }
        return serverInfoList;
    }


    private void sendServiceEvent(String serviceName, ServerInfo serverInfo, ServiceAction serviceAction) {
        ServiceEvent serviceEvent = ServiceEvent.builder()
                .serverInfo(serverInfo)
                .serviceName(serviceName)
                .serviceAction(serviceAction)
                .build();

        serviceEventProcessor.processMessage(serviceEvent);
    }

    @Override
    public void watchPreviousNode() {

        String previousNodePath = leaderElection.getWatchedNode();
        addEventDataWatcher(previousNodePath);
        addEventExistsWatcher(previousNodePath);


    }

    @Override
    public void addEventChildrenWatcher(String path) {

        async.watched()
                .getChildren()
                .forPath(path)
                .event()
                .thenAcceptAsync(watchedEvent -> {


                    if (watchedEvent.getType().equals(Watcher.Event.EventType.NodeChildrenChanged)) {
                        log.info(CHILDREN_CHANGED_EVENT + watchedEvent.getPath());

                        String[] pathCpmponents = watchedEvent.getPath().split(Constants.SLASH);
                        log.debug(HASH_MESSAGE + pathCpmponents[2]);
                        if (pathCpmponents[2] != null) {

                            findDependentServiceIp(pathCpmponents[2]);

                        }

                    } else {
                        log.info(UNKNOWN_EVENT, watchedEvent.toString());
                    }
                    addEventChildrenWatcher(path);
                })
                .exceptionally(throwable -> {
                    log.error(throwable.getMessage());
                    return null;
                });

    }

    public void addEventDataWatcher(String path) {

        async.watched()
                .getData()
                .forPath(path)
                .event()
                .thenAcceptAsync(watchedEvent -> {
                    log.debug($$$$$$$$$$_ENTERED_DATA_WATCHER);
                    if (watchedEvent.getType().equals(Watcher.Event.EventType.NodeDataChanged)) {

                        fetchDataFromNode(path);

                    } else if (watchedEvent.getType().equals(Watcher.Event.EventType.NodeDeleted)) {

                        log.info(NODE_DELETED + watchedEvent.getPath());
                    } else {
                        log.warn(UNKNOWN_EVENT_TYPE_FOR_DATA_CHANGED, watchedEvent.getType());
                    }

                })
                .exceptionally(throwable -> {
                    log.error(throwable.getMessage());
                    return null;
                });
        ;

    }

    private void fetchDataFromNode(String path) {


        try {
            lockService.getWatchedProcessMutex().acquire(30, TimeUnit.SECONDS);
            byte[] bytes = curatorFramework.getData()
                    .forPath(path);
            ZnodeInfoOuterClass.ZnodeInfo zNodeInfo = ZnodeInfoOuterClass.ZnodeInfo.parseFrom(bytes);
            // process data fetched.
            log.info(DATA_RECIEVED_ON_DEPENDENT_NODE, zNodeInfo.toString());

            zNodeInfo.getServiceToWatchList()
                    .stream()
                    .forEach(serviceName -> findDependentServiceIp(serviceName));

            ZnodeInfoOuterClass.ZnodeInfo zNodeOut = ZnodeInfoOuterClass.ZnodeInfo.newBuilder()
                    .setInstanceName(zNodeInfo.getInstanceName())
                    .setServiceName(zNodeInfo.getServiceName())
                    .build();
            Stat stat = curatorFramework.setData()
                    .forPath(path, zNodeOut.toByteArray());
            if (stat == null) {
                log.info(NOT_ABLE_TO_UPDATE_THE_WATCHED_NODE, path);
            } else {
                log.info(UPDATED_THE_WATCHED_NODE_FOR_PATH, path);
            }
            lockService.getWatchedProcessMutex().release();


        } catch (Exception e) {
            e.printStackTrace();
        }

        addEventDataWatcher(path);
    }

    public void addEventExistsWatcher(String path) {

        async.watched()
                .checkExists()
                .forPath(path)
                .event()
                .thenAcceptAsync(watchedEvent -> {
                    if (watchedEvent.getType().equals(Watcher.Event.EventType.NodeDeleted)) {
                        log.info(NODE_DELETED_FOR, watchedEvent.getPath());
                        String basePath = new StringBuilder().append(leaderElection.getAppBasePath())
                                .append(Constants.CHILD_PREFIX)
                                .toString();
                        Integer incomingNode = Integer.parseInt(watchedEvent.getPath().replace(basePath, Constants.EMPTY));
                        if (incomingNode > Integer.parseInt(leaderElection.getCurrentNodeNumber())) {
                            leaderElection.checkForLaterNode();
                            if (!leaderElection.isLastNode()) {
                                watchLaterNode();
                            }
                        } else {
                            try {


                                leaderElection.electLeader();

                                if (!leaderElection.isLeader()) {
                                    lockService.createWatchedLock(leaderElection.getWatchedNodeNumber(), nodeInfo.getAppName());
                                } else {
                                    lockService.clearCurrentInterProcessMutex();
                                }

                                setWatchers();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                    } else {
                        log.info(UNKNOWN_EVENT_FOR_NODE_EXISTS_FOR, watchedEvent.getPath());
                    }
                    addEventExistsWatcher(path);

                })
                .exceptionally(throwable -> {
                    throwable.printStackTrace();
                    return null;
                });
        ;
    }

    @Override
    public void houseKeeping() throws Exception {

        log.debug(ENTERED_HERE);
        leaderElection.validateAndCreateNodes();
        leaderElection.volunteerForLeaderElection();
        leaderElection.electLeader();
        lockService.createCurrentLock(leaderElection.getCurrentNodeNumber(), nodeInfo.getAppName());
        if (!leaderElection.isLeader()) {
            lockService.createWatchedLock(leaderElection.getWatchedNodeNumber(), nodeInfo.getAppName());
        }

        dependentServiceDiscovery.registerSelf();


        getDependentServerServiceStream()
                .flatMap(serviceName ->
                        fetchDependentService(serviceName).stream()
                                .map(serverInfo -> ServiceEvent.builder()
                                        .serviceName(serviceName)
                                        .serviceAction(ServiceAction.CONNECT)
                                        .serverInfo(serverInfo).build())
                )
                .forEach(serviceEvent -> {

                    serviceEventProcessor.processMessage(serviceEvent);
                    ConcurrentMap<ServerInfo, String> serverInfoMap = serviceInfoMap.get(serviceEvent.getServiceName());
                    if (serverInfoMap == null) {
                        serverInfoMap = new ConcurrentHashMap<>();
                        serverInfoMap.put(serviceEvent.getServerInfo(), serviceEvent.getServerInfo().getServerName());
                        serviceInfoMap.put(serviceEvent.getServiceName(), serverInfoMap);
                    } else {
                        serverInfoMap.put(serviceEvent.getServerInfo(), serviceEvent.getServerInfo().getServerName());
                    }

                });


        ;

    }

    @Override
    public void setWatchers() {

        watchSelf();

        if (leaderElection.isLeader()) {
            log.info(ENTERING_WATCHING_DEPEND_NODES);
            watchDependentNodes();
        } else {
            log.info(ENTERING_WATCHING_PREVIOUS_NODES);
            watchPreviousNode();
        }

        if (!leaderElection.isLastNode()) {
            watchLaterNode();
        }
    }

    private void watchSelf() {
        String currentNode = leaderElection.getCurrentNode();
        addEventExistsWatcher(currentNode);
    }


    private void watchLaterNode() {

        String laterNode = leaderElection.getLaterNode();
        addEventExistsWatcher(laterNode);
    }

    private Stream<String> getDependentServerServiceStream() {

        return nodeInfo.getDependentService()
                .stream()
                .filter(serviceInfo -> serviceInfo.getIsServer())
                .map(serviceInfo -> serviceInfo.getServiceName());
    }

    private void updateNodeCurrentNode(String serviceName) {

        if (leaderElection.isLastNode()) {
            leaderElection.checkForLaterNode();
        }

        if (!leaderElection.isLastNode()) {
            InterProcessMutex interProcessMutex = lockService.getCurrentInterProcessMutex();
            try {
                interProcessMutex.acquire(10, TimeUnit.SECONDS);
                updateNodeData(leaderElection.getCurrentNode(), serviceName);
                interProcessMutex.release();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }

    private void updateNodeData(String path, String serviceName) {


        try {
            byte[] nodeBytes = curatorFramework.getData()
                    .forPath(path);
            ZnodeInfoOuterClass.ZnodeInfo zNodeInfo = deserializeZNodeAddPendingService(nodeBytes, serviceName);

            log.info("Initial : {}", zNodeInfo.toString());

            Stat stat = curatorFramework.setData()
                    .forPath(path, zNodeInfo.toByteArray());
            if (stat == null) {
                log.error(UNABLE_TO_SET_DEPENDENT_SERVICE_DATA_FOR, serviceName);
            } else {
                log.info(DEPENDENT_SERVICE_UPDATED_FOR_DISCOVERY_REQUEST, serviceName);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    private ZnodeInfoOuterClass.ZnodeInfo deserializeZNodeAddPendingService(byte[] bytes, String serviceName) {

        ZnodeInfoOuterClass.ZnodeInfo zNodeInfo = null;
        try {
            zNodeInfo = ZnodeInfoOuterClass.ZnodeInfo.parseFrom(bytes);
            log.info(ZNODE_INFO_RECEIVED, zNodeInfo.toString());
            List<String> servicesToWatch = new ArrayList<>();
            zNodeInfo.getServiceToWatchList()
                    .stream()
                    .forEach(s -> servicesToWatch.add(s));


            servicesToWatch.add(serviceName);


            return ZnodeInfoOuterClass.ZnodeInfo.newBuilder()
                    .setServiceName(nodeInfo.getAppName())
                    .setInstanceName(nodeInfo.getInstanceName())
                    .addAllServiceToWatch(servicesToWatch)
                    .build();

        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }

        return null;


    }

}
