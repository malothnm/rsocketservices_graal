package in.nmaloth.rsocketservices.service;


import in.nmaloth.rsocketservices.config.model.ServiceEvent;
import in.nmaloth.rsocketservices.handler.ClientHandler;

public interface ClientService {

    void connectToServer(ServiceEvent serviceEvent);
    void updateClientHandler(ClientHandler clientHandler);

}
