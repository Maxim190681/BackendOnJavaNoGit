package Lesson5HW;

import Lesson5.api.CategoryService;
import Lesson5.dto.GetCategoryResponse;
import Lesson5.utils.RetrofitUtils;
import lombok.SneakyThrows;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import retrofit2.Response;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class GetCategoryWithWrongId {

    static CategoryService categoryService;

    @BeforeAll
    static void beforeAll() {
        categoryService = RetrofitUtils.getRetrofit().create(CategoryService.class);
    }

    @SneakyThrows
    @Test
    @DisplayName("GetCategoryWithWrongId")
    void getCategoryNegativeTest() {
        Response<GetCategoryResponse> response = categoryService.getCategory(-1).execute();

        assertThat(response.isSuccessful(), CoreMatchers.is(false));
        assertThat(response.code(), equalTo(404));
    }

    @SneakyThrows
    @Test
    @DisplayName("GetCategoryWithZeroId")
    void GetCategoryWithZeroId() {
        Response<GetCategoryResponse> response = categoryService.getCategory(0).execute();

        assertThat(response.isSuccessful(), CoreMatchers.is(false));
        assertThat(response.code(), equalTo(404));
    }
}