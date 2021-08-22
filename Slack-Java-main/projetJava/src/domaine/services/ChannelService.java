package domaine.services;

import domaine.model.ChannelModel;
import domaine.model.UserModel;
import persistance.ChannelDAO;
import persistance.JoinsDAO;
import persistance.UserDAO;
import server.Action;


public class ChannelService {

    ChannelDAO channelDAO = new ChannelDAO();
    UserDAO userDAO = new UserDAO();

    public Action createChannel(String ip, String title, String idAdmin, int isPrivate) {
        ChannelModel channelModel = channelDAO.createChannel(ip, title, idAdmin, isPrivate);
        Action.Type type = Action.Type.FAIL;
        JoinsDAO joinsDAO = new JoinsDAO();
        if (channelModel != null && joinsDAO.joinChannel(ip, idAdmin))
            type = Action.Type.SUCCESS;
        else {
            //channelModel = null;
            //channelDAO.removeChannel(ip);
        }
        return new Action(
                channelModel,
                type,
                null
        );
    }

    public Action deleteChannel(String ip, String email, String pwd) {
        UserModel userModel = userDAO.connexionEmail(email, pwd);
        Action.Type type = Action.Type.FAIL;
        if (userModel != null && channelDAO.isAdmin(ip, userModel.getEmail()))
            if (channelDAO.removeChannel(ip))
                type = Action.Type.SUCCESS;
        return new Action(null, type, null);
    }

}
