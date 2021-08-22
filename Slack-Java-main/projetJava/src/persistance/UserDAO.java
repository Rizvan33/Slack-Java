package persistance;

import domaine.model.UserModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    private Connection con;

    public UserDAO() {
        String pwd = "admin123";
        String url = "jdbc:mysql://db4free.net:3306/slackish";
        try {
            con = DriverManager.getConnection(url, "slackish", pwd);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean addUser(String email, String pseudo, String pwd) {
        String request = "insert into USER values ('" + email + "', '" + pseudo + "', '" + pwd + "');";
        try (Statement stmt = con.createStatement()) {
            stmt.executeUpdate(request);
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return true;
    }

    public boolean deleteUser(String email) {
        String request = "delete from USER where email='" + email + "';";
        try (Statement stmt = con.createStatement()) {
            stmt.executeUpdate(request);
            return true;
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return false;
    }

    public UserModel searchUserByEmail(String email) {
        String request = "select PSEUDO from USER where email='" + email + "';";
        UserModel userModel = null;
        try (Statement stmt = con.createStatement()) {
            ResultSet rs = stmt.executeQuery(request);
            if (rs.next())
                userModel = new UserModel(email, rs.getString("pseudo"));
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return userModel;
    }

    public UserModel searchUserByPseudo(String pseudo) {
        String request = "select EMAIL, PWD from USER where pseudo='" + pseudo + "';";
        UserModel usr = null;
        try (Statement stmt = con.createStatement()) {
            ResultSet rs = stmt.executeQuery(request);
            if (rs.next()) {
                String email = rs.getString("EMAIL");
                usr = new UserModel(email, pseudo);
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return usr;
    }

    public List<UserModel> getListUsers() {
        String request = "select email, pseudo from `USER`";
        ArrayList<UserModel> users = new ArrayList<>();
        try (Statement stmt = con.createStatement()) {
            ResultSet rs = stmt.executeQuery(request);
            String email, pseudo;
            while (rs.next()) {
                email = rs.getString("email");
                pseudo = rs.getString("pseudo");
                users.add(new UserModel(email, pseudo));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return users;
    }

    public boolean userExist(String email) {
        String request = "select email from USER where email='" + email + "';";
        try (Statement stmt = con.createStatement()) {
            ResultSet rs = stmt.executeQuery(request);
            return rs.next();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return false;
    }

    public UserModel connexionEmail(String email, String pwd) {
        String request = "select PSEUDO from USER where email='" + email + "' AND pwd='" + pwd + "'";
        try (Statement stmt = con.createStatement()) {
            ResultSet rs = stmt.executeQuery(request);
            if (rs.next()) {
                String pseudo = rs.getString("PSEUDO");
                return new UserModel(email, pseudo);
            }

        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return null;
    }

    public UserModel connexionPseudo(String pseudo, String pwd) {
        String request = "select EMAIL from USER where pseudo='" + pseudo + "' AND pwd='" + pwd + "'";
        try (Statement stmt = con.createStatement()) {
            ResultSet rs = stmt.executeQuery(request);
            String email = rs.getString("EMAIL");
            return new UserModel(email, pseudo);
        } catch (SQLException throwable) {
            throwable.printStackTrace();
            return null;
        }
    }


}
