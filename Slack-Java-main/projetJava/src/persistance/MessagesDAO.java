package persistance;

import domaine.model.MessagesModel;
import domaine.model.UserModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MessagesDAO {
    private Connection con;

    public MessagesDAO() {
        String pwd = "admin123";
        String url = "jdbc:mysql://db4free.net:3306/slackish";
        try {
            con = DriverManager.getConnection(url, "slackish", pwd);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public MessagesModel addMsg(String ip, String email, Timestamp date, String msg) {
        MessagesModel messagesModel = null;
        UserDAO userDAO = new UserDAO();
        try (Statement stmt = con.createStatement()) {
            String request = "insert into MESSAGE values ('" + ip + "', '" + email + "', '" + date + "', '" + msg + "' );";
            stmt.executeUpdate(request);
            UserModel userModel = userDAO.searchUserByEmail(email);
            messagesModel = new MessagesModel(userModel, ip, msg, date);
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return messagesModel;
    }

    public boolean removeMsg(String ip, String email, Timestamp date) {
        String request = "delete from MESSAGE where ip='" + ip + "', email='" + email + "', date_msg='" + date + "' ;";
        try (Statement stmt = con.createStatement()) {
            stmt.executeUpdate(request);
        } catch (SQLException throwable) {
            throwable.printStackTrace();
            return false;
        }
        return true;
    }

    public List<MessagesModel> getChannelMsg(String ip) {
        String request = "select * from MESSAGE where ip='" + ip + "' order by date_msg;";
        List<MessagesModel> msg = new ArrayList<>();
        UserDAO useDAO = new UserDAO();
        try (Statement stmt = con.createStatement()) {
            ResultSet rs = stmt.executeQuery(request);
            String email;
            String content;
            Timestamp date;
            UserModel userModel;
            while (rs.next()) {
                email = rs.getString("email");
                userModel = useDAO.searchUserByEmail(email);
                content = rs.getString("contenu");
                date = rs.getTimestamp("date_msg");
                msg.add(new MessagesModel(userModel, ip, content, date));
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return msg;
    }

}
