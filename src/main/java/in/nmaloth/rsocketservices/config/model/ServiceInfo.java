package in.nmaloth.rsocketservices.config.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ServiceInfo {

    @JsonProperty("serviceName")
    private String serviceName;
    @JsonProperty("route")
    private String route;
    @JsonProperty("isServer")
    private Boolean isServer;
    @JsonProperty("isIn")
    private Boolean isIn;
    @JsonProperty("isOut")
    private Boolean isOut;

}
