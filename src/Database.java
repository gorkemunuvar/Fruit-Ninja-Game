import java.sql.*;

public class Database {
    private static Connection conn;

    private static String url = "jdbc:mysql://localhost:3306/fruitninjadatabase?useUnicode=true&useLegacyDatetimeCode=false&serverTimezone=Turkey";
    private static String user = "root";
    private static String pass = "tosun46";

    public static Connection connect() throws SQLException {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url="jdbc:mysql://localhost:3306/fruitninjadatabase?useUnicode=true&useLegacyDatetimeCode=false&serverTimezone=Turkey";
            String user="root";
            String password="tosun46";
            conn = DriverManager.getConnection(url,user,password);

        }catch (Throwable t){
            t.printStackTrace();
        }

        conn = DriverManager.getConnection(url,user,pass);

        return conn;
    }

    public static Connection getConnection() throws SQLException, ClassNotFoundException{
        if(conn !=null && !conn.isClosed())
            return conn;
        connect();
        return conn;

    }

    public static void executeSql(String sqlCmd){
        try{
            Statement st=conn.createStatement();
            st.executeUpdate(sqlCmd);
        }
        catch(Exception ex){
            System.out.println(ex.getMessage());
        }
    }

    public static boolean searchRecord(String username){
        try{
            //String sqlCommand = "SELECT * FROM user WHERE username=? AND password=md5(?)";
            String sqlCommand = "SELECT username FROM users WHERE username=?";
            PreparedStatement preparedStatement = Database.getConnection().prepareStatement(sqlCommand);
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next())
                return true;
        }catch(Throwable t){
            t.printStackTrace();
        }

        return false;
    }

    public static boolean loginControl(String username, String password){
        try{
            //String sqlCommand = "SELECT * FROM user WHERE username=? AND password=md5(?)";
            String sqlCommand = "SELECT username FROM users WHERE username=? AND password=?";
            PreparedStatement preparedStatement = Database.getConnection().prepareStatement(sqlCommand);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next())
                return true;
        }catch(Throwable t){
            t.printStackTrace();
        }

        return false;
    }
}
