package server;

import java.net.Socket;
import java.util.*;

public class Connections implements Iterable<String> {

    private static final Connections INSTANCE = new Connections();
    private static Map<String, Socket> connectionList = new HashMap<>();

    private Connections() {
    }

    public static Connections getInstance(){
        return INSTANCE;
    }

    public void addConnection(String user, Socket socket) {
        connectionList.put(user, socket);
    }

    public void removeConnection(String user, Socket socket) {
        connectionList.remove(user, socket);
    }

    public Socket getConnection(String user){
        return connectionList.get(user);
    }

    @Override
    public Iterator<String> iterator() {
        return connectionList.keySet().iterator();
    }
}
