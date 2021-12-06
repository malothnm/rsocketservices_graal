//package in.nmaloth.rsocketservices.bootstrap.zookeeper;
//
//import in.nmaloth.rsocketservices.config.model.NodeInfo;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.curator.x.discovery.ServiceDiscovery;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.cloud.zookeeper.serviceregistry.ServiceInstanceRegistration;
//import org.springframework.cloud.zookeeper.serviceregistry.ZookeeperRegistration;
//import org.springframework.cloud.zookeeper.serviceregistry.ZookeeperServiceRegistry;
//import org.springframework.stereotype.Component;
//
//import java.net.InetAddress;
//
//@Component
//@Slf4j
//public class ServiceRegistry implements CommandLineRunner {
//
//    private final ZookeeperServiceRegistry serviceRegistry;
//    private final NodeInfo nodeInfo;
//
//    public ServiceRegistry(ZookeeperServiceRegistry serviceRegistry, NodeInfo nodeInfo) {
//        this.serviceRegistry = serviceRegistry;
//        this.nodeInfo = nodeInfo;
//    }
//
//
//    @Override
//    public void run(String... args) throws Exception {
//
//        String serviceName = new StringBuilder().append("rsocket/")
//                .append(nodeInfo.getAppName()).toString();
//        if(nodeInfo.getServerPort() != null && nodeInfo.getServerPort() != 0){
//
//            String address = InetAddress.getLocalHost().getHostAddress();
//            log.info("#######Server Address Registered: {}",address);
//            ZookeeperRegistration registration = ServiceInstanceRegistration.builder()
//                    .defaultUriSpec()
//                    .address(address)
//                    .port(nodeInfo.getServerPort())
//                    .name(serviceName)
//                    .build();
//            this.serviceRegistry.register(registration);
//        }
//
//    }
//}
