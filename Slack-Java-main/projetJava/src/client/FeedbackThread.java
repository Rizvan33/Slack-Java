package client;

import controleur.ClientController;
import domaine.model.MessagesModel;
import domaine.model.UserModel;
import server.Action;
import server.IOUtils;

import java.net.Socket;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class FeedbackThread extends Thread {
    private final Socket socket;
    private ClientController clientController;
    private Action feedback;
    private final Lock lock;
    private final Condition condition;

    public FeedbackThread(Socket sock, Lock lock, Condition condition) {
        clientController = ClientController.getClientController();
        socket = sock;
        this.lock = lock;
        this.condition = condition;
    }

    @Override
    public void run() {
        try {
            while (!socket.isClosed()) {
                feedback = IOUtils.receive(socket.getInputStream());
                switch (feedback.getActionType()) {
                    case SUCCESS:
                    case FAIL:
                        lock.lock();
                        condition.signal();
                        lock.unlock();
                        break;
                    case SEND_MESSAGE:
                        MessagesModel messagesModel = (MessagesModel) feedback.getContent();
                        if( messagesModel != null){
                            clientController.receiveNewMessage(messagesModel);
                        }
                        break;
                    case JOIN_CHANNEL:
                    case INVITE_CHANNEL:
                        UserModel user = (UserModel) feedback.getContent();
                        if(user != null && ! user.equals(clientController.getCurrentUser())){
                            clientController.receiveNewUser(user, feedback.getChannelIp());
                        }
                        break;

                    default:
                        System.err.println("YO DA FCK U DOING HERE");
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Action getFeedback() {
        return feedback;
    }
}
