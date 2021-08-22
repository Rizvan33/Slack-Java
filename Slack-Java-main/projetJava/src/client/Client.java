package client;

import server.Action;
import server.Action.ParameterKey;
import server.IOUtils;

import java.io.Closeable;
import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static server.Action.ParameterKey.*;

public class Client implements Closeable {
    private Socket socket;
    private FeedbackThread feedbackThread;
    private Lock lock;
    private Condition condition;

    public Client() {
        try {
            int port = 2000;
            String host = "localhost";
            socket = new Socket(host, port);
            lock = new ReentrantLock();
            condition = lock.newCondition();
            feedbackThread = new FeedbackThread(socket, lock, condition);
            feedbackThread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public Action requestToServer(Action action) {
        Action feedback = new Action(null, Action.Type.FAIL, null);
        try {
            IOUtils.send(
                    socket.getOutputStream(),
                    action
            );
            lock.lock();
            condition.await();
            feedback = feedbackThread.getFeedback();
            lock.unlock();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return feedback;
    }

    public static Action createUser(String email, String pseudo, String pwd) {
        Map<ParameterKey, String> parameters = new HashMap<ParameterKey, String>() {{
            put(PSEUDO, pseudo);
            put(EMAIL, email);
            put(PASSWORD, pwd);
        }};
        return new Action(null, Action.Type.CREATE_USER, parameters);
    }

    public static Action deleteUser(String email) {
        Map<ParameterKey, String> parameters = new HashMap<ParameterKey, String>() {{
            put(EMAIL, email);
        }};
        return new Action(null, Action.Type.DELETE_USER, parameters);
    }

    public static Action connectUser(String email, String pwd) {
        Map<ParameterKey, String> parameters = new HashMap<ParameterKey, String>() {{
            put(EMAIL, email);
            put(PASSWORD, pwd);
        }};
        return new Action(null, Action.Type.CONNECT_USER, parameters);
    }

    public static Action disconnectUser() {
        return new Action(null, Action.Type.DISCONNECT_USER, null);
    }

    public static Action searchUserEmail(String email) {
        Map<ParameterKey, String> parameters = new HashMap<ParameterKey, String>() {{
            put(EMAIL, email);
        }};
        return new Action(null, Action.Type.SEARCH_USER, parameters);
    }

    public static Action searchUserPseudo(String pseudo) {
        Map<ParameterKey, String> parameters = new HashMap<ParameterKey, String>() {{
            put(PSEUDO, pseudo);
        }};
        return new Action(null, Action.Type.SEARCH_USER, parameters);
    }

    public static Action getListUsers() {
        return new Action(null, Action.Type.LIST_USERS, null);
    }

    public static Action createChannel(String channel, String title, String idAdmin, int isPrivate) {
        String prvt = String.valueOf(isPrivate);
        Map<ParameterKey, String> parameters = new HashMap<ParameterKey, String>() {{
            put(CHANNEL_IP, channel);
            put(CHANNEL_TITLE, title);
            put(EMAIL, idAdmin);
            put(IS_PRIVATE, prvt);
        }};
        return new Action(null, Action.Type.CREATE_CHANNEL, parameters);
    }

    public static Action deleteChannel(String channel, String email) {
        Map<ParameterKey, String> parameters = new HashMap<ParameterKey, String>() {{
            put(CHANNEL_IP, channel);
            put(EMAIL, email);
        }};
        return new Action(null, Action.Type.DELETE_CHANNEL, parameters);
    }

    public static Action joinExists(String ip) {
        Map<ParameterKey, String> parameters = new HashMap<ParameterKey, String>() {{
            put(CHANNEL_IP, ip);
        }};
        return new Action(null, Action.Type.CHANNEL_EXISTS, parameters);

    }

    public static Action joinChannel(String channel, String email) {
        Map<ParameterKey, String> parameters = new HashMap<ParameterKey, String>() {{
            put(CHANNEL_IP, channel);
            put(EMAIL, email);
        }};
        return new Action(null, Action.Type.JOIN_CHANNEL, parameters);
    }

    public static Action leaveChannel(String channel, String email) {
        Map<ParameterKey, String> parameters = new HashMap<ParameterKey, String>() {{
            put(CHANNEL_IP, channel);
            put(EMAIL, email);
        }};
        return new Action(null, Action.Type.LEAVE_CHANNEL, parameters);
    }

    public static Action inviteToChannel(String channel, String guest) {
        Map<ParameterKey, String> parameters = new HashMap<ParameterKey, String>() {{
            put(EMAIL, guest);
            put(CHANNEL_IP, channel);
        }};
        return new Action(null, Action.Type.INVITE_CHANNEL, parameters);
    }

    public static Action kickFromChannel(String channel, String guest) {
        Map<ParameterKey, String> parameters = new HashMap<ParameterKey, String>() {{
            put(EMAIL, guest);
            put(CHANNEL_IP, channel);
        }};
        return new Action(null, Action.Type.KICK_CHANNEL, parameters);
    }

    public static Action getChannelUsers(String channel) {
        Map<ParameterKey, String> parameters = new HashMap<ParameterKey, String>() {{
            put(CHANNEL_IP, channel);
        }};
        return new Action(null, Action.Type.CHANNEL_USERS, parameters);
    }

    public static Action getUserChannels(String email) {
        Map<ParameterKey, String> parameters = new HashMap<ParameterKey, String>() {{
            put(EMAIL, email);
        }};
        return new Action(null, Action.Type.USER_CHANNELS, parameters);
    }

    public static Action deleteMessage(String email, String channel, long date, String content) {
        Map<ParameterKey, String> parameters = new HashMap<ParameterKey, String>() {{
            put(EMAIL, email);
            put(CHANNEL_IP, channel);
        }};
        return new Action(null, Action.Type.DELETE_MESSAGE, parameters);
    }

    public static Action getChannelMessages(String channel) {
        Map<ParameterKey, String> parameters = new HashMap<ParameterKey, String>() {{
            put(CHANNEL_IP, channel);
        }};
        return new Action(null, Action.Type.CHANNEL_MESSAGES, parameters);
    }


    public static Action addMsg(String ip, String email, String msg) {
        Map<ParameterKey, String> parameters = new HashMap<ParameterKey, String>() {{
            put(CHANNEL_IP, ip);
            put(EMAIL, email);
            put(MESSAGE_CONTENT, msg);
        }};
        return new Action(null, Action.Type.SEND_MESSAGE, parameters);
    }

    @Override
    public void close() throws IOException {
        IOUtils.send(
                socket.getOutputStream(),
                disconnectUser()
        );
        socket.close();
    }
}

//todo modifier date format