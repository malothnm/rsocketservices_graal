package in.nmaloth.rsocketservices.controller;

import in.nmaloth.rsocketservices.config.model.ServerInfo;
import in.nmaloth.rsocketservices.service.zookeeper.DependentServiceDiscoveryImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ZookeeperController {

    private final  DependentServiceDiscoveryImpl dependentServiceDiscovery;

    public ZookeeperController(DependentServiceDiscoveryImpl dependentServiceDiscovery) {
        this.dependentServiceDiscovery = dependentServiceDiscovery;
    }

    @GetMapping("/zookeeper/children/{serviceName}")
    public List<String> getChildren(@PathVariable String serviceName) throws Exception {
        return dependentServiceDiscovery.getChildren(serviceName);
    }

    @GetMapping("/zookeeper/children/ip/{serviceName}")
    public List<ServerInfo> getServerInfo(@PathVariable String serviceName) throws Exception {
        return dependentServiceDiscovery.getIPDetails(serviceName);
    }
}
