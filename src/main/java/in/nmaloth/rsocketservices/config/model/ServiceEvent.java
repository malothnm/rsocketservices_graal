package in.nmaloth.rsocketservices.config.model;


import lombok.*;
import org.springframework.messaging.rsocket.RSocketRequester;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ServiceEvent {

    private ServiceAction serviceAction;
    private String serviceName;
    private String serviceInstance;
    private ServerInfo serverInfo;
    private boolean isIn;
    private boolean isOut;
    private RSocketRequester rSocketRequester;


}
