package Lesson3HW;

import org.junit.jupiter.api.BeforeAll;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class AbstractTest {

    static Properties prop = new Properties();
    private static InputStream configFile;
    private static String apiKey;
    private static String BaseURL;
    private static String RecipeURL;
    private static String hash;
    private static  String UserName;

    @BeforeAll
    static void initTest() throws IOException {
        configFile = new FileInputStream("src/main/resources/MyResources");
        prop.load(configFile);

        apiKey = prop.getProperty("apiKey");
        BaseURL = prop.getProperty("baseURL");
        RecipeURL = prop.getProperty("RecipeURL");
        hash = prop.getProperty("hash");
        UserName = prop.getProperty("username");
    }

    public static String getApiKey(){
        return apiKey;
    }

    public static String getBaseURL(){
        return BaseURL;
    }

    public static  String getRecipeURL(){
        return RecipeURL;
    }

    public static String getHash(){
        return hash;
    }

    public static String getUserName(){
        return UserName;
    }


}
