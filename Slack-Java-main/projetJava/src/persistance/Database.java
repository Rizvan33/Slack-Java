package persistance;
import java.sql.*;

public class Database {
    private Connection con;
   // public static final Database db = new Database();

    public Database() {
        String pwd = "admin123";
        String url = "jdbc:mysql://db4free.net:3306/slackish";
        try {
            con = DriverManager.getConnection(url, "slackish", pwd);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void showTable(String table) {
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = null;
            rs = stmt.executeQuery("SELECT * from " + table.toUpperCase() + " ;");
            ResultSetMetaData rsmd = rs.getMetaData();
            int nbCols = rsmd.getColumnCount();
            System.out.println("\n\nLa table " + table.toUpperCase() + " contient " + nbCols + " colonnes");
            while (rs.next()) {
                for (int i = 1; i <= nbCols; i++)
                    System.out.print(rs.getString(i) + (i < nbCols ? " | " : ""));
                System.out.println();
            }
            System.out.println("\n\n");
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean emptyTable(String table) throws SQLException {
        String request = "delete from " + table.toUpperCase() + ";";
        try {
            Statement stmt = con.createStatement();
            System.out.println("before i");
            int i = stmt.executeUpdate(request);
            System.out.println("i =  "+i);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }


    public void closeDB() {
        try {
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void showDB() {
        showTable("USER");
        showTable("FRIENDS");
        showTable("CHANNEL");
        showTable("JOINS");
        showTable("MESSAGE");
    }

    public void fckDaDB() {
        try {
            emptyTable("MESSAGE");
            emptyTable("JOINS");
            emptyTable("CHANNEL");
            emptyTable("FRIENDS");
            emptyTable("USER");
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }
}
