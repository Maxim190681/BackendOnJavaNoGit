package Lesson6TestsHW;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class CreateCategory {
    static SqlSession session;

    String category;

    @BeforeAll
    static void beforeAll() throws IOException {
        session = null;
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        session = sqlSessionFactory.openSession();
    }

    @Test
    void createCategory() {

        category = "new";

        db.dao.CategoriesMapper categoriesMapper
                = session.getMapper(db.dao.CategoriesMapper.class);

        //find category by id
        db.model.CategoriesExample categoriesExample
                = new db.model.CategoriesExample();
        categoriesExample.createCriteria().andTitleLike(category);
        List<db.model.Categories> list = categoriesMapper.selectByExample(categoriesExample);

        //check twice for  new category
        assertThat(list.size(), equalTo(0));

        //create
        db.model.Categories categories
                = new db.model.Categories();
        categories.setTitle(category);
        categoriesMapper.insert(categories);
        session.commit();

        //find
        categoriesExample = new db.model.CategoriesExample();
        categoriesExample.createCriteria().andTitleLike(category);
        list = categoriesMapper.selectByExample(categoriesExample);
        db.model.Categories selected = list.get(0);

        //check name
        assertThat(selected.getTitle(), equalTo(category));

        //delete new
        categoriesMapper.deleteByPrimaryKey(selected.getId());
        session.commit();
    }

    @AfterAll
    static void afterAll() {
        session.close();
    }
}
