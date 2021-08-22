package server;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

import server.Action.ParameterKey;

/**
 * @author Olivier Pitton <olivier@indexima.com> on 18/12/2020
 */

public class IOUtils {

    private IOUtils() {

    }

    public static void send(OutputStream out, Action action) throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(out);
        oos.writeObject(action);
        oos.flush();
    }

    public static Action receive(InputStream in) throws IOException {
        ObjectInputStream ois = new ObjectInputStream(in);
        try {
            return (Action) ois.readObject();
        } catch (ClassNotFoundException e) {
            throw new IOException(e);
        }
    }
}
