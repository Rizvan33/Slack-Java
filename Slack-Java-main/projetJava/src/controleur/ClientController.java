package controleur;

import client.Client;
import client.front.ChatPage;
import client.front.Window;
import domaine.model.ChannelModel;
import domaine.model.MessagesModel;
import domaine.model.UserModel;
import server.Action;

import java.util.ArrayList;
import java.util.List;

public class ClientController {

    private final Client client;
    private UserModel currentUser;
    private List<UserModel> users = new ArrayList<>();
    private ChannelModel currentChannel;
    private List<ChannelModel> channels = new ArrayList<>();
    private List<MessagesModel> messages = new ArrayList<>();
    private static ClientController clientController;

    public ClientController() {
        clientController = this;
        client = new Client();
    }

    public static ClientController getClientController() {
        return clientController;
    }

    public UserModel getCurrentUser() {
        return currentUser;
    }

    public ChannelModel getCurrentChannel() {
        return currentChannel;
    }

    public List<ChannelModel> getChannels() {
        return channels;
    }

    public List<UserModel> getUsers() {
        return users;
    }

    public List<MessagesModel> getMessages() {
        return messages;
    }

    public void receiveNewMessage(MessagesModel messagesModel) {
        String messageIp = messagesModel.getIp();
        String channelIp = currentChannel.getIp();
        if (messageIp.equals(channelIp)) {
            messages.add(messagesModel);
            Window.getWindow().addNewMessage(messagesModel);
            Window.setContent(ChatPage.getPage());
        }
    }

    public void receiveNewUser(UserModel userModel, String ip) {
        for (UserModel user : users) {
            if (user.equals(userModel)) {
                users.add(userModel);
                Window.getWindow().addNewUser(userModel);
                Window.setContent(ChatPage.getPage());
                return;
            }
        }
    }

    public void receiveDeleteMessage(MessagesModel messagesModel) {
        if (messagesModel.getIp().equals(currentChannel.getIp())) {
            messages.remove(messagesModel);
            Window.getWindow().addNewMessage(messagesModel);
            Window.setContent(ChatPage.getPage());
        }
    }

    /////// USER  ///////
    public void createUser(String email, String pseudo, String pwd) {
        client.requestToServer(Client.createUser(email, pseudo, pwd));
        currentUser = new UserModel(email, pseudo);
    }

    public void deleteUser() {
        client.requestToServer(Client.deleteUser(currentUser.getEmail()));
        currentUser = null;
        currentChannel = null;
        users = new ArrayList<>();
        channels = new ArrayList<>();
        messages = new ArrayList<>();
    }

    public void connectUser(String email, String pwd) {
        Action feedback = client.requestToServer(Client.connectUser(email, pwd));
        if (feedback.isSuccess()) {
            currentUser = (UserModel) feedback.getContent();
            feedback = client.requestToServer(Client.getUserChannels(email));
            if (feedback.isSuccess())
                channels = (List<ChannelModel>) feedback.getContent();
            else
                System.out.println("No messages found for " + currentChannel);
        } else
            System.err.println("CONNECT FAILED");
    }

    public void disconnectUser() {
        client.requestToServer(Client.disconnectUser());
        disconnectFromChannel();
        channels = new ArrayList<>();
        users = new ArrayList<>();
    }

    public void searchUserEmail(String email) {
        Action feedback = client.requestToServer(Client.searchUserEmail(email));
    }

    public void searchUserPseudo(String pseudo) {
        Action feedback = client.requestToServer(Client.searchUserPseudo(pseudo));
    }


    /////// CHANNEL ///////
    public void createChannel(String title, String ip, String idAdmin, int isPrivate) {
        Action feedback = client.requestToServer(Client.createChannel(ip, title, idAdmin, isPrivate));
        if (!feedback.isSuccess())
            System.err.println("Failed to create channel");
    }

    public void deleteChannel(String channel) {
        if (currentChannel.getIdAdmin().equals(currentUser.getEmail()))
            client.requestToServer(Client.deleteChannel(channel, currentUser.getEmail()));
        channels.remove(currentChannel);
        currentChannel = null;
        messages = new ArrayList<>();
    }

    /////// JOINS ///////
    public void joinChannel(String ip) {
        Action feedback = client.requestToServer(Client.joinChannel(ip, currentUser.getEmail()));
        if (feedback.isSuccess()) {
            currentChannel = (ChannelModel) feedback.getContent();
            channels.add(currentChannel);
            getChannelMessages();
            getChannelUsers();
        } else
            System.err.println("COULDN'T JOIN CHANNEL " + ip);
    }

    public void leaveChannel(String channel, String email) {
        Action feedback = client.requestToServer(Client.leaveChannel(channel, email));
        disconnectFromChannel();
        for (ChannelModel channelModel : channels) {
            if (channelModel.equals(channel)) {
                channels.remove(channelModel);
                return;
            }
        }
    }

    public void inviteToChannel(String guest) {
        for (UserModel userModel : users)
            if (guest.equals(userModel.getEmail()))
                return;
        if (currentChannel.isPrivate()) {
            if (currentChannel.getIdAdmin().equals(currentUser.getEmail()))
                client.requestToServer(Client.inviteToChannel(guest, currentChannel.getIp()));
        } else
            client.requestToServer(Client.inviteToChannel(guest, currentChannel.getIp()));
    }

    public void kickFromChannel(String guest) {
        if (currentChannel.getIdAdmin().equals(currentUser.getEmail()))
            client.requestToServer(Client.kickFromChannel(currentChannel.getIp(), guest));
    }

    public boolean connectToChannel(String ip) {
        String ipChannel;
        for (ChannelModel channelModel : channels) {
            ipChannel = channelModel.getIp();
            if (ip.equals(ipChannel)) {
                currentChannel = channelModel;
                getChannelMessages();
                getChannelUsers();
                return true;
            }
        }
        System.err.println("No CHANNEL FOUND");
        return false;
    }

    public void disconnectFromChannel() {
        currentChannel = null;
        users = new ArrayList<>();
        messages = new ArrayList<>();
    }

    public void getChannelUsers() {
        Action feedback = client.requestToServer(Client.getChannelUsers(currentChannel.getIp()));
        feedback.getActionType();
        users = (List<UserModel>) feedback.getContent();
    }

    public void getUserChannels(String email) {
        Action feedback = client.requestToServer(Client.getUserChannels(email));
        if (feedback.getActionType() == Action.Type.SUCCESS)
            channels = (List<ChannelModel>) feedback.getContent();
        else
            System.err.println("COULDN'T GET CHANNELS");
    }

    public void getChannelMessages() {
        Action feedback = client.requestToServer(Client.getChannelMessages(currentChannel.getIp()));
        if (feedback.getActionType() == Action.Type.SUCCESS)
            messages = (List<MessagesModel>) feedback.getContent();
        else
            System.err.println("COULDN'T GET MESSAGES FROM CHANNEL " + currentChannel.getIp());
    }

    ////// MESSAGE ////////
    public void addMsg(String ip, String email, String msg) {
        client.requestToServer(Client.addMsg(ip, email, msg));
    }

    public void deleteMessage(String email, long date, String content) {
        if (currentChannel.getIdAdmin().equals(email) || currentUser.getEmail().equals(email))
            client.requestToServer(Client.deleteMessage(currentChannel.getIdAdmin(), email, date, content));
    }
}
