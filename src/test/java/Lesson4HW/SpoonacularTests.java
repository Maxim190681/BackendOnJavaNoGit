package Lesson4HW;

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
                .spec(getRequestSpecification())
                .when()
                .get(getRecipeURL())
                .then()
                .spec(getResponseSpecification());
        }

        @Test
        void getResultContainsData(){
        JsonPath response;
            response = given()
                    .spec(getRequestSpecification())
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
                    .spec(getRequestSpecification())
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
                    .spec(getRequestSpecification())
                    .when()
                    .get(getRecipeURL())
                    .then()
                    .assertThat()
                    .contentType(ContentType.JSON);
        }

        @Test
        void getZeroRecipeQty(){
            JsonPath response = given()
                    .spec(getRequestSpecification())
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
                    .spec(getRequestSpecificationPOST())
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
                .spec(getRequestSpecificationPOST())
                .when()
                .post(getBaseURL() + "recipes/cuisine?language=en")
                .then()
                .extract().jsonPath();
        assertThat(response.get("cuisine"), equalTo("Mediterranean"));
        }

    @Test
    void postLanguageRU(){
         given()
                 .spec(getRequestSpecification())
                 .queryParam("language", "ru")
                 .queryParam("title", "Gujarati Dry Mung Bean Curry")
                 .when()
                 .post(getBaseURL() + "recipes/cuisine?language=ru").prettyPeek()
                 .then()
                 .assertThat()
                 .statusCode(500);
         }

         @Test
         void postBurgerTitle(){
             JsonPath response = given()
                     .spec(getRequestSpecificationPOST())
                     .queryParam("title", "burger")
                     .when()
                     .post(getBaseURL() + "recipes/cuisine?language=en").prettyPeek()
                     .then()
                     .extract().jsonPath();
             assertThat(response.get("cuisine"), equalTo("American"));
         }

         @Test
         void PostIngredientListWittSpecialSymbol(){
             JsonPath response =given()
                     .spec(getRequestSpecificationPOST())
                     .queryParam("ingredientList", "@#$%$&$")
                     .when()
                     .post(getBaseURL() + "recipes/cuisine?language=en")
                     .then()
                     .extract().jsonPath();
             assertThat(response.get("cuisine"), equalTo("Mediterranean"));

         }
    }





