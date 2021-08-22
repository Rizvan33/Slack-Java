package domaine.services;

import domaine.model.MessagesModel;
import domaine.model.UserModel;
import persistance.MessagesDAO;
import persistance.UserDAO;
import server.Action;

import java.sql.Timestamp;
import java.util.List;

public class MessagesService {
    MessagesDAO messagesDAO = new MessagesDAO();

    public Action addMessage(String ip, String email, String msg, Timestamp timestamp){
        MessagesModel messagesModel = messagesDAO.addMsg(ip,email, timestamp,msg);
        return new Action(
                messagesModel,
                messagesModel == null ? Action.Type.FAIL : Action.Type.SUCCESS,
                null
        );
    }

    public Action deleteMessage(String ip, String email, Timestamp date) {
        Action.Type type = Action.Type.FAIL;
        if (messagesDAO.removeMsg(ip,email, date))
            type = Action.Type.SUCCESS;
        return new Action(null, type, null);
    }

    public Action getChannelMessages(String ip){
        List<MessagesModel> messagesModels = messagesDAO.getChannelMsg(ip);
        return new Action(
                messagesModels,
                messagesModels.isEmpty() ? Action.Type.FAIL : Action.Type.SUCCESS,
                null
        );
    }

}

