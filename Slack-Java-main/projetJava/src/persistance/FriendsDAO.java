package persistance;

import domaine.model.FriendsModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FriendsDAO {
    private Connection con;

    public FriendsDAO() {
        String pwd = "admin123";
        String url = "jdbc:mysql://db4free.net:3306/slackish";
        try {
            con = DriverManager.getConnection(url, "slackish", pwd);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean addFriend(String email1, String email2) {
        try (Statement stmt = con.createStatement()) {
            String request = "insert into FRIENDS values ('" + email1 + "', '" + email2 + "');";
            stmt.executeUpdate(request);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean removeFriend(String email1, String email2) {
        try (Statement stmt = con.createStatement()) {
            String request = "delete from FRIENDS where email1='" + email1 + "' AND email2='" + email2 + "';";
            stmt.executeUpdate(request);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
        return true;
    }

    public List<FriendsModel> getFriends(String email) {
        try (Statement stmt = con.createStatement()) {
            String request = "select * from FRIENDS where email1='" + email + "';";
            ResultSet rs = stmt.executeQuery(request);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
        List<FriendsModel> friends = new ArrayList<>();
        // ...
        return friends;
    }
}
