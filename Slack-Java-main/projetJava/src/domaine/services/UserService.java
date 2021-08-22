package domaine.services;

import domaine.model.UserModel;
import persistance.UserDAO;
import server.Action;

import java.util.List;

public class UserService {

    private final UserDAO userDAO = new UserDAO();

    public Action createUser(String email, String pseudo, String mdp) {
        UserModel userModel = null;
        Action.Type type = Action.Type.FAIL;
        if (userDAO.searchUserByEmail(email) == null)
            if (userDAO.addUser(email, pseudo, mdp)) {
                userModel = new UserModel(email, pseudo);
                type = Action.Type.SUCCESS;
            }
        return new Action(userModel, type, null);
    }

    public Action searchUserByEmail(String email) {
        UserModel userModel = userDAO.searchUserByEmail(email);
        return new Action(
                userModel,
                userModel == null ? Action.Type.FAIL : Action.Type.SUCCESS,
                null
        );
    }

    public Action searchUserByPseudo(String pseudo) {
        UserModel userModel = userDAO.searchUserByPseudo(pseudo);
        return new Action(
                userModel,
                userModel == null ? Action.Type.FAIL : Action.Type.SUCCESS,
                null
        );
    }

    public Action connectWithPseudo(String pseudo, String pw) {
        UserModel userModel = userDAO.connexionPseudo(pseudo, pw);
        return new Action(
                userModel,
                userModel == null ? Action.Type.FAIL : Action.Type.SUCCESS,
                null
        );
    }

    public Action connectWithEmail(String email, String pw) {
        UserModel userModel = userDAO.connexionEmail(email, pw);
        return new Action(
                userModel,
                userModel == null ? Action.Type.FAIL : Action.Type.SUCCESS,
                null
        );
    }

    public Action deleteUser(String email, String pwd) {
        return new Action(
                null,
                userDAO.deleteUser(email) ? Action.Type.SUCCESS : Action.Type.FAIL,
                null
        );
    }

    public Action getListUsers() {
        List<UserModel> userModels = userDAO.getListUsers();
        return new Action(
                userModels,
                userModels.isEmpty() ? Action.Type.FAIL : Action.Type.SUCCESS,
                null
        );
    }
}