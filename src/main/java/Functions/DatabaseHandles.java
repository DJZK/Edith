package Functions;

import java.sql.*;

public class DatabaseHandles {

    public void initStatus() { // Method that will load all values of Database into the bot
        DatabaseParameters.setBotToken(getConfigValue("Token"));
    }


    private String getConfigValue(String function){
        // Method that will access the database in read manner because
        // manners are very important here
        String Value = "";
        try {
            try (Connection connection = DriverManager.getConnection("jdbc:sqlite:" + DatabaseParameters.getFinalLocation());
                 Statement statement = connection.createStatement()) {

                // function
                statement.execute("SELECT * FROM Config WHERE Function = '" + function +"'");
                try (ResultSet resultSet = statement.getResultSet()) {
                    while (resultSet.next()) {
                        Value = resultSet.getString("Parameter");
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getErrorCode() + " " + e.getMessage());
        }
        return Value ;
    }

    public void writeActivity(String Date, String Time, String Name, String Activity, String ActivityReason){
        try(Connection connection = DriverManager.getConnection("jdbc:sqlite:" + DatabaseParameters.getFinalLocation());
            Statement statement = connection.createStatement()){

            statement.execute("INSERT INTO Activity (Date, Time, Name, Activity, Reason) " +
                    "VALUES ('" + Date + "', '" + Time + "', '" + Name + "', '" + Activity + "', '" + ActivityReason + "')");

        }

        catch (SQLException sqlException){
            System.out.println("Something happened! " + sqlException.getMessage());
        }
    }

    // Will see if user exist in UserDatabase otherwise, would return error
    public String findUser (String ID){
        try {
            try (Connection connection = DriverManager.getConnection("jdbc:sqlite:" + DatabaseParameters.getFinalLocation());
                 Statement statement = connection.createStatement()) {

                // function
                statement.execute("SELECT * FROM Users WHERE DiscordID = '" + ID +"'");
                try (ResultSet resultSet = statement.getResultSet()) {
                    while (resultSet.next()) {

                        return resultSet.getString("FullName");
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getErrorCode() + " " + e.getMessage());
        }
        return null;
    }
}
