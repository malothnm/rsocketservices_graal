package in.nmaloth.rsocketservices.service.zookeeper;

import com.google.protobuf.InvalidProtocolBufferException;
import in.nmaloth.rsocketservices.config.model.NodeInfo;
import in.nmaloth.rsocketservices.config.model.ServerInfo;
import in.nmaloth.rsocketservices.model.proto.ServiceDiscoveryOuterClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.x.async.AsyncCuratorFramework;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class DependentServiceDiscoveryImpl implements DependentServiceDiscovery {


    private final AsyncCuratorFramework asyncCuratorFramework;
    private final CuratorFramework curatorFramework;
    private final NodeInfo nodeInfo;

    public static final String serviceDiscoveryPath = "/service-discovery";
    public static final String serviceProtocol = "/rsocket";
    @Value("${zookeeper.connect-string}")
    private String zookeeperConnectString;

    public static final String SERVICE_DISCOVERY_ERROR_CONTACT_ADMINISTRATOR = "Service Discovery Error.. Contact Administrator ";
    public static final String SLASH = "/";

    public DependentServiceDiscoveryImpl(AsyncCuratorFramework asyncCuratorFramework,
                                         NodeInfo nodeInfo) {
        this.asyncCuratorFramework = asyncCuratorFramework;
        this.curatorFramework = asyncCuratorFramework.unwrap();
        this.nodeInfo = nodeInfo;
    }

    @Override
    public List<ServerInfo> getIPDetails(String serviceName) throws Exception {

        String servicePath = new StringBuilder()
                .append(serviceDiscoveryPath)
                .append(serviceProtocol)
                .append(SLASH)
                .append(serviceName)
                .toString()

                ;
        return  getChildren(serviceName)
                .stream()
                .map(s -> servicePath + SLASH + s)
                .map(s -> {
                    try {
                        return curatorFramework.getData().forPath(s);
                    } catch (Exception e) {
                        e.printStackTrace();
                        return null;
                    }
                })
                .filter(bytes -> bytes != null)
                .map(bytes -> {
                    try {
                        return ServiceDiscoveryOuterClass.ServiceDiscovery.parseFrom(bytes);
                    } catch (InvalidProtocolBufferException e) {
                        e.printStackTrace();
                        return null;
                    }
                })
        .filter(serviceDiscovery -> serviceDiscovery != null)
        .map(serviceDiscovery -> ServerInfo.builder().serverName(serviceDiscovery.getHost())
                .serverPort(serviceDiscovery.getPort())
                .build()

        ).collect(Collectors.toList())
        ;
    }

    public List<String> getChildren(String serviceName) throws Exception {

        String servicePath = new StringBuilder()
                .append(serviceDiscoveryPath)
                .append(serviceProtocol)
                .append(SLASH)
                .append(serviceName)
                .toString()

                ;

        Stat stat = curatorFramework.checkExists()
                .forPath(servicePath);
        if(stat == null){

            createPaths(Arrays.asList(servicePath));
        }

        return curatorFramework.getChildren()
                .forPath(servicePath);


    }

    @Override
    public void registerSelf() throws Exception {

        checkPathExists();

        String[] zookeeperIp = zookeeperConnectString.split(":");
        String host = zookeeperIp[0];
        Integer port = Integer.parseInt(zookeeperIp[1]);
        Socket socket = new Socket(host,port);

        log.info( "Is Socket Connected {}",socket.isConnected());
        log.info("Inet Address {} and port {}",socket.getInetAddress().toString(), socket.getLocalPort());
        log.info("Local Address {} and port {}",socket.getLocalAddress().toString(),socket.getLocalPort());
        log.info(socket.getRemoteSocketAddress().toString());
        String address = socket.getInetAddress().getHostAddress().toString().replace("/","");



        socket.close();


//        String address = InetAddress.getLocalHost().getHostAddress();
            log.info("#######Server Address Registered: {}",address);
        ServiceDiscoveryOuterClass.ServiceDiscovery.Builder builder =
                ServiceDiscoveryOuterClass.ServiceDiscovery.newBuilder()
                .setHost(address)
                ;


        String instanceName = nodeInfo.getInstanceName().replace(nodeInfo.getAppName()+ ":",  "" );
        String servicePath = new StringBuilder()
                .append(serviceDiscoveryPath)
                .append(serviceProtocol)
                .append(SLASH)
                .append(nodeInfo.getAppName())
                .append(SLASH)
                .append(instanceName)
                .toString()
                ;


        if(nodeInfo.getServerPort() != null){
            builder.setPort(nodeInfo.getServerPort());
        }
        builder.build().toByteArray();
        curatorFramework.create()
                .withMode(CreateMode.EPHEMERAL)
                .forPath(servicePath,builder.build().toByteArray());

    }

    private void checkPathExists() throws Exception {

        String protocolNode = serviceDiscoveryPath + serviceProtocol;
        String appNode = protocolNode + SLASH + nodeInfo.getAppName();

        Stat stat = curatorFramework.checkExists()
                .forPath(serviceDiscoveryPath);
        if(stat == null){
            createPaths(Arrays.asList(serviceDiscoveryPath,protocolNode,appNode));

            return;
        }

        stat = curatorFramework.checkExists()
                .forPath(protocolNode);
        if(stat == null){
            createPaths(Arrays.asList(protocolNode,appNode));
            return;
        }
        stat = curatorFramework.checkExists()
                .forPath(appNode);

        if(stat == null){
            createPaths(Arrays.asList(appNode));
        }
    }

    private void createPaths(List<String> serviceNameList) {

        serviceNameList.forEach(s -> {
            try {
                curatorFramework
                        .create()
                        .forPath(s);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

    }
//    private final ServiceDiscovery<ZookeeperInstance> serviceDiscovery;





//    @Override
//    public List<ServerInfo> getIPDetails(String serviceName) {
//
//        List<Mono<ServerInfo>> serverMonoInfoList = new ArrayList<>();
//
//        List<ServerInfo> serverInfoList = new ArrayList<>();
//
//        try {
//            String searchName = new StringBuilder().append("rsocket/")
//                    .append(serviceName).toString();
//            Collection<ServiceInstance<ZookeeperInstance>> serviceInstances =
//                    serviceDiscovery.queryForInstances(searchName);
//
//            if (serviceInstances == null || serviceInstances.size() == 0) {
//                return serverInfoList;
//            }
//
//            serviceInstances
//                    .forEach(serviceInstance -> {
//
//                                log.info("############## Server Ip : {}",serviceInstance.getAddress());
//                                log.info("############### Server Port: {}", serviceInstance.getPort());
//                                ServerInfo serverInfo = ServerInfo.builder()
//                                        .serverName(serviceInstance.getAddress())
//                                        .serverPort(serviceInstance.getPort())
//                                        .build();
//
//                                serverInfoList.add(serverInfo);
//                            });
//            return serverInfoList;
//
//        } catch (Exception e) {
//
//            log.error(SERVICE_DISCOVERY_ERROR_CONTACT_ADMINISTRATOR);
//            e.printStackTrace();
//            return new ArrayList<>();
//        }
//    }


}
