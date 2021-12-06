package in.nmaloth.rsocketservices.config.zookeeper;

import in.nmaloth.rsocketservices.config.model.ServerInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.x.async.AsyncCuratorFramework;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.ReentrantLock;

@Configuration
@Slf4j
public class ZookeeperConfigs {

    @Value("${zookeeper.connect-string}")
    private String zookeeperConnectString;

//    @Bean
//    public ServiceDiscovery<ZookeeperInstance> serviceDiscovery(CuratorFramework curatorFramework,
//                                                                ZookeeperDiscoveryProperties zookeeperDiscoveryProperties){
//
//            return ServiceDiscoveryBuilder.builder(ZookeeperInstance.class)
//                    .client(curatorFramework)
//                    .basePath(zookeeperDiscoveryProperties.getRoot())
//                    .build()
//                    ;
//    }

    @Bean
    public AsyncCuratorFramework async(){

        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 5);
        CuratorFramework client = CuratorFrameworkFactory.newClient(zookeeperConnectString, retryPolicy);
        client.start();

        return AsyncCuratorFramework.wrap(client);

    }

    @Bean
    public ConcurrentMap<String, ConcurrentMap<ServerInfo, String>> serviceInfo(){
        return new ConcurrentHashMap<>();
    }

    @Bean
    public ReentrantLock getRequesterLock(){
        return new ReentrantLock();
    }


}
