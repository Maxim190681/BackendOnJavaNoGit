package Lesson4HW;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
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
    private static String language;
    protected static ResponseSpecification responseSpecificationGET;
    protected static RequestSpecification requestSpecificationGET;
    protected static ResponseSpecification responseSpecificationPOST;
    protected static RequestSpecification requestSpecificationPOST;
    protected static RequestSpecification requestSpecificationMealTest;
    private static String shopListURL;

    protected static ResponseSpecification responseSpecShoppingDEL;


    @BeforeAll
    static void initTest() throws IOException {
        configFile = new FileInputStream("src/main/resources/MyResources");
        prop.load(configFile);

        apiKey = prop.getProperty("apiKey");
        BaseURL = prop.getProperty("baseURL");
        RecipeURL = prop.getProperty("RecipeURL");
        hash = prop.getProperty("hash");
        language = prop.getProperty("language");
        shopListURL = prop.getProperty("shoplistURL");


        requestSpecificationGET = new RequestSpecBuilder()
                .addQueryParam("apiKey", apiKey)
                .build();

        responseSpecificationGET = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectContentType(ContentType.JSON)
                .build();

        requestSpecificationPOST = new RequestSpecBuilder()
                .addQueryParam("apiKey", apiKey)
                .addQueryParam("language", language)
                .build();

        requestSpecificationMealTest = new RequestSpecBuilder()
                .addQueryParam("apiKey", apiKey)
                .addQueryParam("hash", hash)
                .build();


        responseSpecShoppingDEL = new ResponseSpecBuilder()
                .expectContentType(ContentType.JSON)
                .expectStatusCode(404)
                .build();
    }
    public static String getApiKey(){
        return apiKey;
    }

    public static String getHash(){
        return hash;
    }

    public static String getBaseURL(){
        return BaseURL;
    }

    public static  String getRecipeURL(){
        return RecipeURL;
    }

    public RequestSpecification getRequestSpecification(){
        return requestSpecificationGET;
    }

    public ResponseSpecification getResponseSpecification(){
        return responseSpecificationGET;
    }

    public ResponseSpecification getResponseSpecificationPOST(){
        return  responseSpecificationPOST;
    }

    public RequestSpecification getRequestSpecificationPOST(){
        return requestSpecificationPOST;
    }

    public RequestSpecification getRequestSpecificationMealTest(){
        return requestSpecificationMealTest;
    }
    public static String getShopListURL(){
        return shopListURL;
    }

    public ResponseSpecification getResponseSpecShoppingDEL(){
        return responseSpecShoppingDEL;
    }

}
