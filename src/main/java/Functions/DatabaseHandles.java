package Functions;

import java.sql.*;

public class DatabaseHandles {

    public static void FunctionRefresh() {
        setConfigValue(DatabaseParameters.getBotPrefix(), "Prefix");
        setConfigValue(DatabaseParameters.getChannelID(), "Channel");
        setConfigValue(DatabaseParameters.getConsoleChannel(),"ConsoleChannel");
        setConfigValue(DatabaseParameters.getSudoPass(), "ConsolePass");
        setConfigValue(DatabaseParameters.getGuildID(), "GuildID");
    }

    public void initStatus() { // Method that will load all values of Database into the bot
        DatabaseParameters.setBotToken(getConfigValue("Token"));
        DatabaseParameters.setChannelID(getConfigValue("Channel"));
        DatabaseParameters.setBotPrefix((getConfigValue("Prefix")));
        DatabaseParameters.setSudoPass((getConfigValue("ConsolePass")));
        DatabaseParameters.setConsoleChannel((getConfigValue("ConsoleChannel")));
        DatabaseParameters.setGuildID(getConfigValue("GuildID"));
    }


    private static void setConfigValue(String parameter, String function){
        try(Connection connection = DriverManager.getConnection("jdbc:sqlite:" + DatabaseParameters.getFinalLocation());
            Statement statement = connection.createStatement()){
            // statement.execute("UPDATE Config SET Value = 'true' WHERE Function = 'ChatCalculateEnabled'");

            // Parameter update
            statement.execute("UPDATE Config SET Parameter = '" + parameter + "' WHERE Function = '" + function + "'");

        } catch (SQLException sqlException){
            sqlException.printStackTrace();
        }
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

    /**
     *
     * @param Date The current date
     * @param Time The current time
     * @param Name Name of the doer
     * @param Activity Their activity
     * @param ActivityReason The reason for that activity
     */
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
                    while(resultSet.next()){
                        return resultSet.getString("FullName");
                    }

                }
            }
        } catch (SQLException e) {
            System.out.println(e.getErrorCode() + " " + e.getMessage());
        }
         return "";
    }

    /**
     *
     * @param ID user ID
     * @return LoggedOn, OnBreak
     */
    public boolean[] checkActionEligibility(String ID){
        try {
            try (Connection connection = DriverManager.getConnection("jdbc:sqlite:" + DatabaseParameters.getFinalLocation());
                 Statement statement = connection.createStatement()) {

                // function
                statement.execute("SELECT * FROM Users WHERE DiscordID = '" + ID +"'");
                try (ResultSet resultSet = statement.getResultSet()) {
                    while(resultSet.next()){
                        return new boolean[]{Boolean.parseBoolean(resultSet.getString("LoggedOn")), Boolean.parseBoolean(resultSet.getString("OnBreak"))};
                    }

                }
            }
        } catch (SQLException e) {
            System.out.println(e.getErrorCode() + " " + e.getMessage());
        }
        return new boolean[]{false, false};
    }

    /**
     * Flips the boolean value of the current action eligibility
     * @param ID user ID
     * @param Function A = LoggedOn, B = OnBreak
     */
    public void updateEligibility(String ID, char Function){
        try(Connection connection = DriverManager.getConnection("jdbc:sqlite:" + DatabaseParameters.getFinalLocation());
            Statement statement = connection.createStatement()){
            // statement.execute("UPDATE Config SET Value = 'true' WHERE Function = 'ChatCalculateEnabled'");

            // Flips the boolean
            switch (Function){
                case 'A':
                case 'a':
                    statement.execute("UPDATE Users SET LoggedOn = '" + !checkActionEligibility(ID)[0]  + "' WHERE DiscordID = '" + ID + "'");
                    break;
                case 'B':
                case 'b':
                    statement.execute("UPDATE Users SET OnBreak = '" + !checkActionEligibility(ID)[1]  + "' WHERE DiscordID = '" + ID + "'");
                    break;
            }


        } catch (SQLException sqlException){
            sqlException.printStackTrace();
        }
    }
}
