package domaine.services;

import domaine.model.ChannelModel;
import domaine.model.JoinsModel;
import domaine.model.UserModel;
import persistance.ChannelDAO;
import persistance.JoinsDAO;
import persistance.UserDAO;
import server.Action;

import java.util.*;

public class JoinsService {
    private final JoinsDAO joinsDAO = new JoinsDAO();
    private final ChannelDAO channelDAO = new ChannelDAO();
    private final UserDAO userDAO = new UserDAO();

    public Action joinChannel(String ip, String email) {
        if (joinsDAO.joinChannel(ip, email)) {
            ChannelModel channelModel = channelDAO.getChannel(ip);
            return new Action(
                    channelModel,
                    Action.Type.SUCCESS,
                    null

            );
        }
        return new Action(
                null,
                Action.Type.FAIL,
                null
        );
    }

    public Action joinsExists(String ip) {
        return new Action(null, joinsDAO.joinsExist(ip) ? Action.Type.SUCCESS : Action.Type.FAIL,
                null
        );
    }

    public Action leaveChannel(String ip, String email) {
        return new Action(
                null,
                joinsDAO.leaveChannel(ip, email) ? Action.Type.SUCCESS : Action.Type.FAIL,
                null
        );
    }

    public Action getUserChannels(String email) {
        List<JoinsModel> joinModels = joinsDAO.getUserChannels(email);
        List<ChannelModel> channelModels = new ArrayList<>();
        String ip;
        ChannelModel channel;
        for (JoinsModel joinsModel : joinModels) {
            ip = joinsModel.getIp();
            channel = channelDAO.getChannel(ip);
            if (channel != null) {
                channelModels.add(channel);
            }
        }
        return new Action(
                channelModels,
                channelModels.isEmpty() ? Action.Type.FAIL : Action.Type.SUCCESS,
                null
        );
    }

    public Action getChannelUsers(String ip) {
        List<JoinsModel> joinsModels = joinsDAO.getChannelUsers(ip);
        List<UserModel> userModels = new ArrayList<>();
        String email;
        UserModel user;
        for (JoinsModel joinsModel : joinsModels) {
            email = joinsModel.getEmail();
            user = userDAO.searchUserByEmail(email);
            if (user != null) {
                userModels.add(user);
            }
        }
        return new Action(
                userModels,
                userModels.isEmpty() ? Action.Type.FAIL : Action.Type.SUCCESS,
                null
        );
    }

    public Action addUserToChannel(String ip, String email) {
        UserModel userModel = null;
        Action.Type type = Action.Type.FAIL;
        if (joinsDAO.joinChannel(ip, email)) {
            userModel = userDAO.searchUserByEmail(email);
            type = Action.Type.SUCCESS;
        }
        return new Action(
                userModel,
                type,
                null
        );
    }

    public Action removeUserFromChannel(String ip, String email) {
        Action.Type type = Action.Type.FAIL;
        if (joinsDAO.leaveChannel(ip, email))
            type = Action.Type.SUCCESS;
        return new Action(
                null,
                type,
                null
        );
    }
}
