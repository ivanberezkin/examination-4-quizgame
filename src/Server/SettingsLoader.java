package Server;

import java.io.FileInputStream;
import java.util.Properties;

public class SettingsLoader {

    private static Properties prop = new Properties();

    static {
        try(FileInputStream in = new FileInputStream("GameSettings.properties")) {
            prop.load(in);
        } catch (Exception e) {
            System.out.println("Error loading properties");
        }
    }
    public static int getRoundsPerGame(){
        return Integer.parseInt(prop.getProperty("rounds", "5"));
    }
    public static int getQuestionsPerRound(){
        return Integer.parseInt(prop.getProperty("questions", "3"));
    }
    public static int getMaxPlayers() {
        return Integer.parseInt(prop.getProperty("players", "2"));
    }

}
