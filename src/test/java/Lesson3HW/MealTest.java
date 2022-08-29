package Lesson3HW;

import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class MealTest extends AbstractTest{

    @Test
    void MealTest(){
        String id = given()
                .queryParam("hash", getHash())
                .queryParam("apiKey", getApiKey())
                .body("{\n"
                        + " \"date\": 1660306288,\n"
                        + " \"slot\": 1,\n"
                        + " \"position\": 0,\n"
                        + " \"type\": \"INGREDIENTS\",\n"
                        + " \"value\": {\n"
                        + " \"ingredients\": [\n"
                        + " {\n"
                        + " \"name\": \"1 banana\"\n"
                        + " }\n"
                        + " ]\n"
                        + " }\n"
                        + "}")
                .when()
                .post("https://api.spoonacular.com/mealplanner/UserForApiTests/items")
                .then()
                .statusCode(200)
                .extract()
                .jsonPath()
                .get("id")
                .toString();

        given()
                .queryParam("hash", getHash())
                .queryParam("apiKey", getApiKey())
                .delete("https://api.spoonacular.com/mealplanner/UserForApiTests/items/" + id)
                .then()
                .statusCode(200);
    }

    @Test
    void GetMealPlanDay(){
       JsonPath response = given()
                .queryParam("apiKey", getApiKey())
                .queryParam("hash", getHash())
                .when()
                .get(getBaseURL() + "mealplanner/userforapitests/day/2020-06-01")
                .then()
                .extract().jsonPath();
        assertThat(response.get("status"), equalTo("failure"));
        assertThat(response.get("code"), equalTo(400));
        assertThat(response.get("message"), equalTo("No meals planned for that day"));
    }

    @Test
    void AddtoMealPlanRecipeType(){
        String id = given()
                .queryParam("hash", getHash())
                .queryParam("apiKey", getApiKey())
                .body("{\n"
                        +" \"date\": 1660306288,\n"
                        +" \"slot\": 1,\n"
                        +"\"position\": 0,\n"
                        +" \"type\": \"RECIPE\",\n"
                        +" \"value\": {\n"
                        +" \"id\": 296213,\n"
                        +" \"servings\": 2,\n"
                        +" \"title\": \"Spinach Salad with Roasted Vegetables and Spiced Chickpea\",\n"
                        +" \"imageType\": \"jpg\",\n"
                        +" }\n"
                        +"}")
                .when()
                .post("https://api.spoonacular.com/mealplanner/UserForApiTests/items")
                .then()
                .statusCode(200)
                .extract()
                .jsonPath()
                .get("id")
                .toString();

        JsonPath response = given()
                .queryParam("hash", getHash())
                .queryParam("apiKey", getApiKey())
                .delete("https://api.spoonacular.com/mealplanner/UserForApiTests/items/" + id)
                .then()
                .extract().jsonPath();
        assertThat(response.get("status"), equalTo("success"));
    }

}
