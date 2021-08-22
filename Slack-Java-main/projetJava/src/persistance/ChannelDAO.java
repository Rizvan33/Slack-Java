package persistance;

import domaine.model.ChannelModel;

import java.sql.*;

public class ChannelDAO {
    private Connection con;

    public ChannelDAO() {
        String pwd = "admin123";
        String url = "jdbc:mysql://db4free.net:3306/slackish";
        try {
            con = DriverManager.getConnection(url, "slackish", pwd);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ChannelModel createChannel(String ip, String title, String idAdmin, int isPrivate) {
        String request = "insert into CHANNEL values ('" + ip + "', '" + title + "', '" + idAdmin + "', '" + isPrivate + "');";
        ChannelModel channelModel = null;
        try (Statement stmt = con.createStatement()) {
            stmt.executeUpdate(request);
            channelModel = new ChannelModel(ip, title, idAdmin, isPrivate);
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return channelModel;
    }

    public boolean removeChannel(String ip) {
        String request = "delete fom CHANNEL where ip ='" + ip + "';";
        boolean success = false;
        try (Statement stmt = con.createStatement()) {
            stmt.executeUpdate(request);
            success = true;
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return success;
    }

    public ChannelModel getChannel(String ip) {
        String request = "select TITLE, ID_ADMIN, ISPRIVATE from CHANNEL where ip='" + ip + "';";
        ChannelModel channelModel = null;
        try (Statement stmt = con.createStatement()) {
            ResultSet rs = stmt.executeQuery(request);
            if (rs.next()) {
                String tittle = rs.getString("title");
                String admin = rs.getString("id_Admin");
                int isPrivate = rs.getInt("isPrivate");
                channelModel = new ChannelModel(ip, tittle, admin, isPrivate);
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return channelModel;
    }

    public boolean isAdmin(String ip, String email) {
        String request = "select ID_ADMIN from CHANNEL where ip='" + ip + "' AND id_admin='" + email + "';";
        try (Statement stmt = con.createStatement()) {
            return stmt.execute(request);
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return false;
    }

    public boolean channelExist(String ip) {
        try (Statement stmt = con.createStatement()) {
            String request = "select IP from CHANNEL where ip='" + ip + "';";
            ResultSet rs = stmt.executeQuery(request);
            return rs.next();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return false;
    }
}
