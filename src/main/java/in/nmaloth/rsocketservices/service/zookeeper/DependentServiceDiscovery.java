package in.nmaloth.rsocketservices.service.zookeeper;


import in.nmaloth.rsocketservices.config.model.ServerInfo;

import java.net.UnknownHostException;
import java.util.List;

public interface DependentServiceDiscovery {
    List<ServerInfo> getIPDetails(String serviceName) throws Exception;
    void registerSelf() throws Exception;

}
