package Lesson6TestsHW;


import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class ChangeCategory {

    static SqlSession session;
    Long category_id;
    String category;
    String categoryNew;

    @BeforeAll
    static void beforeAll() throws IOException{
        session = null;
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        session = sqlSessionFactory.openSession();
    }

    @Test
    void ChangeCategory() {
        category_id = 1L;
        categoryNew = "4";

        db.dao.CategoriesMapper categoriesMapper
                = session.getMapper(db.dao.CategoriesMapper.class);

        //find change category by ID
        db.model.Categories selected = categoriesMapper.selectByPrimaryKey(category_id);

        //receive category
        category = selected.getTitle();

        //check twice
        assertThat(category != categoryNew, is(true));

        //change category name
        selected.setTitle(categoryNew);
        categoriesMapper.updateByPrimaryKey(selected);
        session.commit();

        //check change
        assertThat(selected.getTitle(), equalTo(categoryNew));

        // return old
        selected.setTitle(category);
        categoriesMapper.updateByPrimaryKey(selected);
        session.commit();

        //check change for old
        assertThat(selected.getTitle(), equalTo(category));
    }
    @AfterAll
    static void afterAll(){
        session.close();
    }

}
