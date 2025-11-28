package Server;

import java.io.FileInputStream;
import java.util.Properties;

public class ConfigLoader {

    private static Properties prop = new Properties();

    static {
        try(FileInputStream in = new FileInputStream("GameSettings.properties")) {
            prop.load(in);
        } catch (Exception e) {
            System.out.println("Error loading properties");
        }
    }

}
