package Lesson4HW;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ShoppingList extends AbstractTest {
        private final String USER_NAME = "userforapitests";

    @Test
    void DeleteIDFromShoppingListWithoutCreateID(){
        JsonPath response = given()
                .spec(getRequestSpecificationMealTest())
                .when()
                .delete(getShopListURL()).prettyPeek()
                .then()
                .spec(getResponseSpecShoppingDEL())
                .extract().jsonPath();
         assertThat(response.get("status"), equalTo("failure"));
         assertThat(response.get("message"), equalTo("A shopping list item with id 15678 does not exist"));

    }

    @Test
    void addItemToShoppingList(){
        /* Response response = given()
                .spec(getRequestSpecificationMealTest())
                .when()
                .body(new AddToShoppingListRequest("burger", "meat", true))
                .post(getBaseURL() +"mealplanner/" + USER_NAME + "/shopping-list/items").prettyPeek()
                .then()
                .extract()
                .response()
                .body()
                .as(Response.class);
        assertThat(response.getName(), containsString("sesame flank steak salad"));
        assertThat(response.getAisle(),containsString("Salad"));*/
             given()
                 .spec(getRequestSpecificationMealTest())
                 .body("{\n"
                         + " \"item\": \"burger\",\n"
                         + " \"aisle\": \"meat\",\n"
                         + " \"parse\": true,\n"
                         + "}")
                 .when()
                 .post("https://api.spoonacular.com/mealplanner/userforapitests/shopping-list/items").prettyPeek()
                 .then()
                 .statusCode(200);
    }

    @Test
    void GetShoppingList(){
        given()
                .spec(getRequestSpecificationMealTest())
                .when()
                .get(getBaseURL() +"mealplanner/" + USER_NAME + "/shopping-list").prettyPeek()
                .then()
                .spec(getResponseSpecification());
    }

    @Test
    void DeleteIDFromShoppingList(){
        given()
                .queryParam("hash", getHash())
                .when()
                .delete("https://api.spoonacular.com/mealplanner/userforapitests/shopping-list/items/15678")
                .then()
                .assertThat()
                .statusCode(401)
                .contentType(ContentType.JSON);

    }
}
