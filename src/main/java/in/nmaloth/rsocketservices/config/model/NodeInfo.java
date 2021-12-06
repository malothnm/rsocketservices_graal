package in.nmaloth.rsocketservices.config.model;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NodeInfo {

    private String appName;
    private String instanceName;
    private Integer serverPort;
    private List<ServiceInfo> dependentService;

}
