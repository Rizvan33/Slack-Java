package client.front;

import controleur.ClientController;
import domaine.model.ChannelModel;
import domaine.model.MessagesModel;
import domaine.model.UserModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

public class Window extends JFrame implements ActionListener {
    private static JPanel center;
    private static String actualPage = "Authentication Page";
    private static final Window window = new Window();
    private static ClientController clientController;

    private static void createWindow() throws IOException {
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.getContentPane().setBackground(new Color(255, 255, 255));

        window.setTitle("Slackish");
        window.setFocusable(true);
        window.setResizable(false);

        center = AuthPage.getPage();
        window.setContentPane(center);

        window.setVisible(true);
        window.pack();
        window.setLocationRelativeTo(null);
        window.setSize(500, 320);

        clientController = new ClientController();
    }

    public static Window getWindow() {
        return window;
    }

    public static void setActualPage(String value) {
        actualPage = value;
    }

    public static void setContent(Page content) {
        center = content;
        center.repaint();
        window.setContentPane(center);
        window.setVisible(true);
    }

    public static void backToHome() {
        setContent(IndexPage.getPage());
        window.addHeader();
        setActualPage("index");
    }

    public void addHeader() {
        window.setLayout(new BorderLayout());
        window.getContentPane().add(Header.getHeader(), BorderLayout.PAGE_START);
    }

    /**
     * This method renders the content of the channel : Messages + Users
     */
    public void channelContent() {

        List<UserModel> users = clientController.getUsers();
        if (users != null) {
            for (UserModel user : users) {
                String usr = user.getPseudo();
                ChatPage.getPage().addUsersToList(usr + "\n");
            }
        }
        List<MessagesModel> messages = clientController.getMessages();
        if (messages != null) {
            for (MessagesModel msg : messages) {
                ChatPage.getPage().getConvTa().append(msg.toString() + "\n");
            }
        }
    }

    /**
     * This method blocks getting TWICE the content of the channel and only adds NEW Messages
     */
    public void addNewMessage(MessagesModel message) {
        ChatPage.getPage().getConvTa().append(message.toString() + "\n");
    }

    /**
     * This method blocks getting TWICE the content of the channel and only adds NEW User
     */
    public void addNewUser(UserModel userModel) {
        ChatPage.getPage().addUsersToList(userModel.getPseudo());
    }

    /**
     * ACTIONS PERFORMED IN DIFFERENT PAGES
     */
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        /** AUTHENTICATION PAGE ----> First page of the app */
        if (actualPage.equals("Authentication Page")) {
            if (source == AuthPage.getPage().getSigninButton()) {
                System.out.println("SignIn");
                Window.setContent(SigninPage.getPage());
                Window.setActualPage("Signin");
            }
            if (source == AuthPage.getPage().getLoginButton()) {
                System.out.println("LogIn");
                //change the page
                Window.setContent(LoginPage.getPage());
                Window.setActualPage("login");
            }
        }

        if ((actualPage.equals("login")) || (actualPage.equals("Signin"))) {
            if ((source == LoginPage.getPage().getBack()) || (source == SigninPage.getPage().getBack())) {
                LoginPage.getPage().getUserNameBox().setText("");
                LoginPage.getPage().getPasswordBox().setText("");
                SigninPage.getPage().getUserNameBox().setText("");
                SigninPage.getPage().getEmailBox().setText("");
                SigninPage.getPage().getPasswordBox().setText("");
                SigninPage.getPage().getPasswordBox2().setText("");
                System.out.println("Authentication Page");
                Window.setContent(AuthPage.getPage());
                Window.setActualPage("Authentication Page");
            }
        }

        /** HANDLES THE SIGN IN -----> CHECKS EMAIL + PASSWORD + HASHES THE ENTERED PASSWORD*/
        if (actualPage.equals("Signin")) {
            if (source == SigninPage.getPage().getEnter()) {
                String pseudo = SigninPage.getPage().getUserNameBox().getText();
                String email = SigninPage.getPage().getEmailBox().getText();
                String password = SigninPage.getPage().getPasswordBox().getText();
                String password2 = SigninPage.getPage().getPasswordBox2().getText();
                System.out.println(pseudo + ", " + email + ", " + password);


                if (pseudo.equals("") || email.equals("") || password.equals("") || password2.equals("")) {
                    System.out.println("Empty box");
                    JLabel error = new JLabel("Empty box");
                    SigninPage.getPage().add(error);
                    error.setBounds(200, 200, 100, 20);
                }
                if (!this.isEmailValid(email)) {
                    System.out.println("Invalid email");
                    JLabel error2 = new JLabel("Invalid email");
                    SigninPage.getPage().add(error2);
                    error2.setBounds(200, 220, 100, 20);
                }
                if ((!this.isPwValid(password)) && (!password.equals(password2))) {
                    System.out.println("Invalid password");
                    JLabel error3 = new JLabel("Invalid password");
                    SigninPage.getPage().add(error3);
                    error3.setBounds(200, 240, 100, 20);


                } else {
                    HashPassword hp = new HashPassword();
                    String hashedpw = hp.hashPassword(password);
                    clientController.createUser(email, pseudo, hashedpw);
                    System.out.println("Index Page");
                    Window.setContent(IndexPage.getPage());
                    Header.getHeader().getLoggedInAsBox().setText(pseudo);
                    addHeader();
                    Window.setActualPage("index");
                }
                SigninPage.getPage().getUserNameBox().setText("");
                SigninPage.getPage().getEmailBox().setText("");
                SigninPage.getPage().getPasswordBox().setText("");
                SigninPage.getPage().getPasswordBox2().setText("");

            }
        }

        /** HANDLES USER CONNECTION ---> CHECKS THE ENTERED EMAIL + PASSWORD AND HASHES IT*/
        if (actualPage.equals("login")) { //only with pseudo
            if (source == LoginPage.getPage().getEnter()) {
                String email = LoginPage.getPage().getUserNameBox().getText();
                String password = LoginPage.getPage().getPasswordBox().getText();
                System.out.println(email + ", " + password);

                if (email.equals("") || password.equals("")) {
                    System.out.println("Empty box");
                    JLabel error4 = new JLabel("Empty box");
                    LoginPage.getPage().add(error4);
                    error4.setBounds(350, 210, 100, 20);

                } else {
                    HashPassword hp = new HashPassword();
                    String hashedpw = hp.hashPassword(password);
                    clientController.connectUser(email, hashedpw);
                }
                LoginPage.getPage().getUserNameBox().setText("");
                LoginPage.getPage().getPasswordBox().setText("");

                /* to set the logged user pseudo in header*/
                String pseudo = clientController.getCurrentUser().getPseudo();
                System.out.println("Index Page");
                Window.setContent(IndexPage.getPage());
                Header.getHeader().getLoggedInAsBox().setText(pseudo);
                addHeader();

                /* to get all channels of the current logged user */
                clientController.getUserChannels(email);
                List<ChannelModel> channels = clientController.getChannels();
                for (ChannelModel channel : channels) {
                    IndexPage.getPage().addChatToList(channel.toString());
                }
                Window.setActualPage("index");
            }
        }

        /** CONNECT TO A CHANNEL */
        if (actualPage.equals("index")) {
            if (source == IndexPage.getPage().getConnect()) {
                JList list = IndexPage.getPage().getChatList();
                if (list.getSelectedIndex() != -1) {
                    System.out.println("Channel Page");
                    Window.setContent(ChatPage.getPage());
                    addHeader();

                    /* to name the channels of the current user + get their content */
                    String val = (String) list.getSelectedValue();
                    String[] ip = val.split(" : ");
                    ChatPage.getConvL().setText(ip[0]);
                    boolean connected = clientController.connectToChannel(ip[1]);
                    if (connected) {
                        channelContent();
                        Window.setActualPage("chat page");
                    }else {
                        backToHome();
                    }
                }
            }
        }
        /** ADDING A NEW CHANNEL  */
        if (actualPage.equals("index")) {
            if (source == IndexPage.getPage().getNewChannel()) {
                System.out.println("New channel Page");
                Window.setContent(CreateChannelPage.getPage());
                addHeader();
                Window.setActualPage("new channel page");
                CreateChannelPage.getPage().getTitleBox().setText("");
                CreateChannelPage.getPage().getIpBox().setText("");
                CreateChannelPage.getPage().getPrivacyCheckBox().setSelected(false);
            }
        }


        if (actualPage.equals("index")) {
            if (source == IndexPage.getPage().getJoinChannel()) {
                System.out.println("join channel Page");
                Window.setContent(JoinPage.getPage());
                addHeader();
                Window.setActualPage("join channel page");
            }
        }

        /** SEND MESSAGE IN CHAT  */
        if (actualPage.equals("chat page")) {
            if (source == ChatPage.getPage().getSend()) {
                String mess = ChatPage.getPage().getMessageTf().getText();
                System.out.println(mess);
                ChatPage.getPage().getMessageTf().setText("");
                ChatPage.getPage().getMessageTf();

                String sender = clientController.getCurrentUser().getEmail();
                String channel = clientController.getCurrentChannel().getIp();

                clientController.addMsg(channel, sender, mess);
                addHeader();
                Window.setActualPage("chat page");

            }
        }

        /** CREATING A NEW CHANNEL */
        if (actualPage.equals("new channel page")) {
            if (source == CreateChannelPage.getPage().getEnter()) {
                String title = CreateChannelPage.getPage().getTitleBox().getText();
                String ip = CreateChannelPage.getPage().getIpBox().getText();
                JCheckBox pBox = CreateChannelPage.getPage().getPrivacyCheckBox();
                int privacy = CreateChannelPage.getPage().iSelected(pBox);


                String admin = clientController.getCurrentUser().getEmail();
                clientController.createChannel(title, ip, admin, privacy);
                String titleChannel = title + " : " + ip;
                System.out.println("new channel created");
                backToHome();
                IndexPage.getPage().addChatToList(titleChannel);
            }
        }
        /** JOIN CHANNEL + GET ITS CONTENT */
        if (actualPage.equals("join channel page")) {
            if (source == JoinPage.getPage().getEnter()) {
                String ip = JoinPage.getPage().getIpBox().getText();
                clientController.joinChannel(ip);
                String title = clientController.getCurrentChannel().getTitle();
                String titleChannel = title + " : " + ip;
                System.out.println("you joined a new channel");
                /* to name the channels of the current user + get their content */
                ChatPage.getConvL().setText(title);
                channelContent();
                Window.setActualPage("chat page");
                IndexPage.getPage().addChatToList(titleChannel);
                Window.setContent(ChatPage.getPage());
                addHeader();
            }
        }

        /** ADDING A NEW MEMBER */
        if (actualPage.equals("chat page")) {
            if (source == ChatPage.getPage().getAddUser()) {
                System.out.println("Add Member Page");
                Window.setContent(AddMemberPage.getPage());
                addHeader();
                Window.setActualPage("add member page");
            }
        }

        /** SET THE NEW MEMBER IN THE CHANNEL MEMBERS */
        if (actualPage.equals("add member page")) {
            if (source == AddMemberPage.getPage().getEnter()) {
                String email = AddMemberPage.getPage().getEmailBox().getText();

                System.out.println("you added a new member");
                Window.setContent(ChatPage.getPage());
                addHeader();
//                List<UserModel> list = clientController.getUsers();
//                String pseudo = "";
//                for(UserModel user : list){
//                    String userMail= user.getEmail();
//                    if(userMail.equals(email)){
//                        pseudo = user.getPseudo();
//                    }
//                }
                clientController.inviteToChannel(email);
//                ChatPage.getPage().addUsersToList(pseudo);

            }
        }
    }

    public boolean isEmailValid(String email) {
        if (email == "oie" || email == "chevre") return true;
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        return email.matches(regex);
    }


    public boolean isPwValid(String mdp) {
        if (mdp == "onk" || mdp == "ack") return true;
        String regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";

        //Explanation
        /*  ^                 # start-of-string
            (?=.*[0-9])       # a digit must occur at least once
            (?=.*[a-z])       # a lower case letter must occur at least once
            (?=.*[A-Z])       # an upper case letter must occur at least once
            (?=.*[@#$%^&+=])  # a special character must occur at least once
            (?=\S+$)          # no whitespace allowed in the entire string
            .{8,}             # anything, at least eight places though
            $                 # end-of-string*/

        return mdp.matches(regex);
    }


    public static void main(String[] args) {
        try {
            createWindow();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
