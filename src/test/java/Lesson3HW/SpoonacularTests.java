package Lesson3HW;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.lessThan;

public class SpoonacularTests extends AbstractTest {


    @Test
    void getResultContainsDataStatusCode(){
        given()
                .queryParam("apiKey", getApiKey())
                .when()
                .get(getRecipeURL())
                .then()
                .statusCode(200);
        }

        @Test
        void getResultContainsData(){
        JsonPath response;
            response = given()
                    .queryParam("apiKey", getApiKey())
                    .when()
                    .get(getRecipeURL())
                    .then()
                    .extract().jsonPath();
            assertThat(response.get("totalResults"),equalTo(5219));
            assertThat(response.get("offset"), equalTo(0));
        }

        @Test
        void getQtyOfRecipeUserRequest(){
            JsonPath response = given()
                    .queryParam("apiKey", getApiKey())
                    .queryParam("number", "3")
                    .when()
                    .get(getRecipeURL())
                    .then()
                    .extract().jsonPath();
            assertThat(response.get("number"), equalTo(3));
        }

        @Test
        void getResponseContainsJSON(){
            given()
                    .queryParam("apiKey", getApiKey())
                    .when()
                    .get(getRecipeURL())
                    .then()
                    .assertThat()
                    .contentType(ContentType.JSON);
        }

        @Test
        void getZeroRecipeQty(){
            JsonPath response = given()
                    .queryParam("apiKey", getApiKey())
                    .queryParam("number", "1")
                    .when()
                    .get(getRecipeURL())
                    .then()
                    .extract().jsonPath();
            assertThat(response.get("number"), lessThan(2));
            assertThat(response.get("offset"), equalTo(0));
            assertThat(response.get("totalResults"), equalTo(5219) );
        }

        @Test
        void postCuisineCountryId646043(){
            JsonPath response = given()
                    .queryParam("apiKey", getApiKey())
                    .queryParam("language", "en")
                    .queryParam("title", "Gujarati Dry Mung Bean Curry")
                    .when()
                    .post(getBaseURL() + "recipes/cuisine?language=en")
                    .then()
                    .extract().jsonPath();
            assertThat(response.get("cuisine"), equalTo("Indian"));
          }

    @Test
    void postCheckContains(){
        JsonPath response = given()
                .queryParam("apiKey", getApiKey())
                .queryParam("language", "en")
                .when()
                .post(getBaseURL() + "recipes/cuisine?language=en")
                .then()
                .extract().jsonPath();
        assertThat(response.get("cuisine"), equalTo("Mediterranean"));
        }

    @Test
    void postLanguageRU(){
         given()
                 .queryParam("apiKey", getApiKey())
                 .queryParam("language", "ru")
                 .queryParam("title", "Gujarati Dry Mung Bean Curry")
                 .when()
                 .post(getBaseURL() + "recipes/cuisine?language=ru")
                 .then()
                 .assertThat()
                 .statusCode(500);
         }

         @Test
         void postBurgerTitle(){
             JsonPath response = given()
                     .queryParam("apiKey", getApiKey())
                     .queryParam("language", "en")
                     .queryParam("title", "burger")
                     .when()
                     .post(getBaseURL() + "recipes/cuisine?language=en")
                     .then()
                     .extract().jsonPath();
             assertThat(response.get("cuisine"), equalTo("American"));
         }

         @Test
         void postingredientListWittSpecialSymbol(){
             JsonPath response =given()
                     .queryParam("apiKey", getApiKey())
                     .queryParam("language", "en")
                     .queryParam("ingredientList", "@#$%$&$")
                     .when()
                     .post(getBaseURL() + "recipes/cuisine?language=en")
                     .then()
                     .extract().jsonPath();
             assertThat(response.get("cuisine"), equalTo("Mediterranean"));

         }
    }





