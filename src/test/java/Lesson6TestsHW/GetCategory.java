package Lesson6TestsHW;

import lesson6HW.api.CategoryService;
import lesson6HW.dto.CategoryResponse;

import lesson6HW.utils.RetrofitUtils;
import lombok.SneakyThrows;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import retrofit2.Response;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;


public class GetCategory {

    static CategoryService categoryService;

    @BeforeAll
    static void beforeAll() {
        categoryService = RetrofitUtils.getRetrofit().create(CategoryService.class);
    }

    @SneakyThrows
    @Test
    void getCategory() {
        Response<CategoryResponse> response = categoryService.getCategory(1).execute();

        assertThat(response.isSuccessful(), CoreMatchers.is(true));
        assertThat(response.code(), equalTo(200));
        assert response.body() != null;
        assertThat(response.body().getId(), equalTo(1));
        String category1 = response.body().getTitle();
        response.body().getProducts().forEach(product ->
                assertThat(product.getCategoryTitle(), equalTo(category1)));

        response = categoryService.getCategory(2).execute();

        assertThat(response.isSuccessful(), CoreMatchers.is(true));
        assertThat(response.code(), equalTo(200));
        assert response.body() != null;
        assertThat(response.body().getId(), equalTo(2));
        String category2 = response.body().getTitle();
        response.body().getProducts().forEach(product ->
                assertThat(product.getCategoryTitle(), equalTo(category2)));
    }

}
