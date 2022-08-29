package Lesson4HW;

import io.restassured.path.json.JsonPath;
import lesson4.ResponseForMealTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class MealTest extends AbstractTest {

    @Test
    void MealTest(){
        String id = given()
                .spec(getRequestSpecificationMealTest())
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
                .spec(getResponseSpecification())
                .extract()
                .jsonPath()
                .get("id")
                .toString();

        given()
                .spec(getRequestSpecificationMealTest())
                .delete("https://api.spoonacular.com/mealplanner/UserForApiTests/items/" + id)
                .then()
                .spec(getResponseSpecification());
    }

    @Test
    void GetMealPlanDay(){
       JsonPath response = given()
                .spec(getRequestSpecificationMealTest())
                .when()
                .get(getBaseURL() + "mealplanner/userforapitests/day/2020-06-01")
                .then()
                .extract().jsonPath();
        assertThat(response.get("status"), equalTo("failure"));
        assertThat(response.get("code"), equalTo(400));
        assertThat(response.get("message"), equalTo("No meals planned for that day"));
    }

    @Test
    void AddToMealPlanRecipeType(){
        String id = given()
                .spec(getRequestSpecificationMealTest())
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
                .spec(getResponseSpecification())
                .extract()
                .jsonPath()
                .get("id")
                .toString();

        JsonPath response = given()
                .spec(getRequestSpecificationMealTest())
                .delete("https://api.spoonacular.com/mealplanner/UserForApiTests/items/" + id)
                .then()
                .extract().jsonPath();
        assertThat(response.get("status"), equalTo("success"));
    }

    @Test
    void getAccountInfoWithExternalEndpointTest() {
        ResponseForMealTest response = given().spec(requestSpecificationMealTest)
                .when()
                .formParam("title","Burger")
                .post("https://api.spoonacular.com/recipes/cuisine").prettyPeek()
                .then()
                .extract()
                .response()
                .body()
                .as(ResponseForMealTest.class);
        assertThat(response.getCuisine(), containsString("American"));
    }

}
