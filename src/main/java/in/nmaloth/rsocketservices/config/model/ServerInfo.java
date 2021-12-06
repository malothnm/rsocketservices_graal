package in.nmaloth.rsocketservices.config.model;

import lombok.*;

import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ServerInfo {

    public ServerInfo(String serverName, int serverPort){
        this.serverName = serverName;
        this.serverPort = serverPort;
    }

    private String serverName;
    private int serverPort;
    private boolean connected;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ServerInfo)) return false;
        ServerInfo that = (ServerInfo) o;
        return getServerPort() == that.getServerPort() &&
                getServerName().equals(that.getServerName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getServerName(), getServerPort());
    }
}
