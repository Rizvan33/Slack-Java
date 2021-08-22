package persistance;

import domaine.model.ChannelModel;
import domaine.model.JoinsModel;
import domaine.model.UserModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JoinsDAO {
    private Connection con;

    public JoinsDAO() {
        String pwd = "admin123";
        String url = "jdbc:mysql://db4free.net:3306/slackish";
        try {
            con = DriverManager.getConnection(url, "slackish", pwd);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean joinChannel(String ip, String email) {
        String request = "insert into JOINS values ('" + ip + "', '" + email + "');";
        try (Statement stmt = con.createStatement()) {
            stmt.executeUpdate(request);
            return true;
        } catch (SQLException throwable) {
            throwable.printStackTrace();
            return false;
        }
    }

    public boolean leaveChannel(String ip, String email) {
        String request = "delete from JOINS where ip='" + ip + "' AND email='" + email + "';";
        try (Statement stmt = con.createStatement()) {
            stmt.executeUpdate(request);
        } catch (SQLException throwable) {
            throwable.printStackTrace();
            return false;
        }
        return true;
    }

    public List<JoinsModel> getUserChannels(String email) {
        String request = "select IP from JOINS where email='" + email + "';";
        List<JoinsModel> joins = new ArrayList<>();
        try (Statement stmt = con.createStatement()) {
            ResultSet rs = stmt.executeQuery(request);
            String ip;
            while (rs.next()) {
                ip = rs.getString("ip");
                joins.add(new JoinsModel(ip, email));
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return joins;
    }

    public List<JoinsModel> getChannelUsers(String ip) {
        String request = "select EMAIL from JOINS where ip='" + ip + "';";
        List<JoinsModel> users = new ArrayList<>();
        try (Statement stmt = con.createStatement()) {
            ResultSet rs = stmt.executeQuery(request);
            String email;
            while (rs.next()) {
                email = rs.getString("email");
                users.add(new JoinsModel(ip, email));
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return users;
    }

    public boolean joinsExist(String ip){
        String request = "select IP from JOINS where ip='" + ip + "';";
        try (Statement stmt = con.createStatement()) {
            ResultSet rs = stmt.executeQuery(request);
            return rs.next();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return false;
    }

}
