package server;

import domaine.model.MessagesModel;
import domaine.model.UserModel;
import domaine.services.*;

import java.io.Closeable;
import java.io.IOException;
import java.net.Socket;
import java.sql.Timestamp;
import java.util.ArrayList;

public class ServerThread extends Thread implements Closeable {

    private final Socket sock;
    private final Connections connections;
    private final UserService userService;
    private final FriendsService friendsService;
    private final ChannelService channelService;
    private final JoinsService joinsService;
    private final MessagesService messagesService;
    private String user = "anon";

    public ServerThread(Socket sock) {
        this.sock = sock;
        connections = Connections.getInstance();
        userService = new UserService();
        friendsService = new FriendsService();
        channelService = new ChannelService();
        joinsService = new JoinsService();
        messagesService = new MessagesService();
    }

    @Override
    public void run() {
        try {
            Action action;
            while (!sock.isClosed()) {
                action = IOUtils.receive(sock.getInputStream());
                switch (action.getActionType()) {
                    // ~~~~~~~~~~ USER CASES ~~~~~~~~~~
                    case CREATE_USER:
                        createUser(action);
                        break;
                    case DELETE_USER:
                        deleteUser(action);
                        break;
                    case CONNECT_USER:
                        connectUser(action);
                        break;
                    case DISCONNECT_USER:
                        disconnectUser();
                        break;
                    case SEARCH_USER:
                        searchUser(action);
                        break;
                    case LIST_USERS:
                        getListUsers();
                        break;
                    case INVITE_CHANNEL:
                        addUserToChannel(action);
                        break;
                    case KICK_CHANNEL:
                        deleteUserFromChannel(action);
                        break;
                    // ~~~~~~~~~~ CHANNEL CASES ~~~~~~~~~~
                    case CREATE_CHANNEL:
                        createChannel(action);
                        break;
                    case DELETE_CHANNEL:
                        deleteChannel(action);
                        break;
                    // ~~~~~~~~~~ JOINS CASES ~~~~~~~~~~
                    case CHANNEL_EXISTS:
                        channelExists(action);
                        break;
                    case JOIN_CHANNEL:
                        joinChannel(action);
                        break;
                    case LEAVE_CHANNEL:
                        leaveChannel(action);
                        break;
                    case CHANNEL_USERS:
                        getChannelUsers(action);
                        break;
                    case USER_CHANNELS:
                        getUserChannels(action);
                        break;
                    // ~~~~~~~~~~ MESSAGE CASES ~~~~~~~~~~
                    case SEND_MESSAGE:
                        sendMessage(action);
                        break;
                    case DELETE_MESSAGE:
                        deleteMessage(action);
                        break;
                    case CHANNEL_MESSAGES:
                        getChannelMessages(action);
                        break;
                    default:
                        System.err.println("Action type not handled");
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            disconnect();
        } finally {
            close();
        }
    }

    private void sendFeedback(Action feedback) {
//        new Thread(() -> {
        try {
            IOUtils.send(sock.getOutputStream(), feedback);
        } catch (IOException e) {
            e.printStackTrace();
        }
//        }).start();
    }

    public void notifyChannel(Action action, Action channelUserAction) {
        ArrayList<UserModel> channelUsers = (ArrayList<UserModel>) channelUserAction.getContent();
        Socket userSocket;
        for (UserModel userModel : channelUsers) {
            userSocket = connections.getConnection(userModel.getEmail());
            if (userSocket != null && userSocket.isConnected()) {
                try {
                    IOUtils.send(
                            userSocket.getOutputStream(),
                            action
                    );
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~ USER FUNCTIONS ~~~~~~~~~~~~~~~~~~~~~~~~~
    private void createUser(Action action) {
        Action feedback = userService.createUser(action.getEmail(), action.getPseudo(), action.getPassword());
        sendFeedback(feedback);
        if (feedback.isSuccess()) {
            user = action.getEmail();
            connections.addConnection(user, sock);
        }
    }

    private void deleteUser(Action action) {
        Action feedback = userService.deleteUser(action.getEmail(), action.getPassword());
        sendFeedback(
                feedback
        );
        disconnectUser();
    }

    public void connectUser(Action action) {
        Action feedback;
        if (action.getEmail() != null) {
            System.out.println(System.currentTimeMillis() + " email : " + action.getEmail());
            feedback = userService.connectWithEmail(action.getEmail(), action.getPassword());
        } else
            feedback = userService.connectWithPseudo(action.getPseudo(), action.getPassword());
        if (feedback.isSuccess()) {
            user = action.getEmail();
            connections.addConnection(user, sock);
        }
        sendFeedback(feedback);
    }

    private void disconnectUser() {
        disconnect();
        sendFeedback(new Action(null, Action.Type.SUCCESS, null));
    }

    private void searchUser(Action action) {
        Action feedback;
        if (action.getEmail() != null)
            feedback = userService.searchUserByEmail(action.getEmail());
        else
            feedback = userService.searchUserByPseudo(action.getPseudo());
        sendFeedback(feedback);
    }

    private void getListUsers() {
        Action feedback = userService.getListUsers();
        sendFeedback(feedback);
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~ CHANNEL FUNCTIONS ~~~~~~~~~~~~~~~~~~~~~~~~~
    private void createChannel(Action action) {
        Action feedback = channelService.createChannel(
                action.getChannelIp(),
                action.getChannelTitle(),
                action.getEmail(),
                Integer.parseInt(action.getChannelPrivacy())
        );
        sendFeedback(feedback);
    }

    private void deleteChannel(Action action) {
        Action feedback = channelService.deleteChannel(action.getChannelIp(), action.getEmail(), action.getPassword());
        sendFeedback(feedback);
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~ JOINS FUNCTION ~~~~~~~~~~~~~~~~~~~~~~~~~
    private void channelExists(Action action) {
        Action feedback = joinsService.joinsExists(action.getChannelIp());
    }

    private void joinChannel(Action action) {
        Action feedback = joinsService.joinChannel(action.getChannelIp(), action.getEmail());
        sendFeedback(feedback);
        if (feedback.isSuccess()) {
            feedback = userService.searchUserByEmail(action.getEmail());
            feedback.setType(Action.Type.JOIN_CHANNEL);
            Action channelUsersAction = joinsService.getChannelUsers(action.getChannelIp());
            notifyChannel(feedback, channelUsersAction);
        }
    }

    private void leaveChannel(Action action) {
        Action feedback = joinsService.leaveChannel(action.getChannelIp(), action.getEmail());
        sendFeedback(feedback);
    }

    private void getUserChannels(Action action) {
        Action feedback = joinsService.getUserChannels(action.getEmail());
        sendFeedback(feedback);
    }

    private void getChannelUsers(Action action) {
        Action feedback = joinsService.getChannelUsers(action.getChannelIp());
        sendFeedback(feedback);
    }

    private void addUserToChannel(Action action) {
        Action feedback = joinsService.addUserToChannel(action.getChannelIp(), action.getEmail());
        sendFeedback(feedback);
        if (feedback.isSuccess()) {
            feedback.setType(Action.Type.INVITE_CHANNEL);
            Action channelUsersAction = joinsService.getChannelUsers(action.getChannelIp());
            notifyChannel(feedback, channelUsersAction);
        }
    }

    private void deleteUserFromChannel(Action action) {
        Action feedback = joinsService.removeUserFromChannel(action.getChannelIp(), action.getEmail());
        sendFeedback(feedback);
    }


    // ~~~~~~~~~~~~~~~~~~~~~~~~~ MESSAGE FUNCTION ~~~~~~~~~~~~~~~~~~~~~~~~~
    private void sendMessage(Action action) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Action feedback = messagesService.addMessage(action.getChannelIp(), action.getEmail(), action.getMessageContent(), timestamp);
        sendFeedback(feedback);
        if (feedback.isSuccess()) {
            MessagesModel message = (MessagesModel) feedback.getContent();
            Action channelUsersAction = joinsService.getChannelUsers(message.getIp());
            notifyChannel(new Action(message, action.getActionType(), null), channelUsersAction);
        }
    }

    private void deleteMessage(Action action) {
        MessagesModel msg = (MessagesModel) action.getContent();
        Action feedback = messagesService.deleteMessage(msg.getIp(), msg.getSender().getEmail(), msg.getDate());
        sendFeedback(feedback);
    }

    private void getChannelMessages(Action action) {
        Action feedback = messagesService.getChannelMessages(action.getChannelIp());
        sendFeedback(feedback);
    }

    private void disconnect() {
        connections.removeConnection(user, sock);
        String message = user + " disconnected!";
        user = "anon";
        System.out.println(message);
    }

    @Override
    public void close() {
        try {
            sock.close();
        } catch (IOException ignored) {
        }
    }
}
