package Lesson5HW;

import Lesson5.api.ProductService;
import Lesson5.dto.Product;
import Lesson5.utils.RetrofitUtils;
import com.github.javafaker.Faker;
import lombok.SneakyThrows;
import okhttp3.ResponseBody;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import retrofit2.Response;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class CreateProductMyTest {

    static ProductService productService;
    Faker faker = new Faker();
    Product product = null;
    String category;
    int id;
    @SneakyThrows
    void tearDown() {
        Response<ResponseBody> response = productService.deleteProduct(id).execute();
        assertThat(response.isSuccessful(), CoreMatchers.is(true));
    }

    @BeforeAll
    static void beforeAll() {
        productService = RetrofitUtils.getRetrofit().create(ProductService.class);
    }

    void setUp() {
        product = new Product()
                .withTitle("TerminatorT1000")
                .withPrice((int) (Math.random() * 10000))
                .withCategoryTitle(category);
    }

    @Test
    @DisplayName("CreateProductMyTest")
    void createProductTest() throws IOException {
        category = "Electronic";
        setUp();
        Response<Product> response = productService.createProduct(product).execute();

        assertThat(response.code(), equalTo(201));
        assert response.body() != null;
        assertThat(response.body().getCategoryTitle(), equalTo(category));
        assertThat(response.isSuccessful(), CoreMatchers.is(true));

        id =  response.body().getId();
        tearDown();

    }

}
