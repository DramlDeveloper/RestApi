package dao;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import rest.api.rest_service.dao.impl.PostDaoImpl;
import rest.api.rest_service.entity.PostEntity;
import rest.api.rest_service.util.PropertiesUtil;

import java.util.Optional;

@Testcontainers
@Tag("DockerRequired")
class PostDaoImplTest {
    public static PostDaoImpl postDao;
    @Container
    public static PostgreSQLContainer<?> container = new PostgreSQLContainer<>("postgres:14.1-alpine")
            .withDatabaseName("postgres")
            .withUsername(PropertiesUtil.getProperty("db.username"))
            .withPassword(PropertiesUtil.getProperty("db.password"));

    @BeforeAll
    static void beforeAll() {
        container.start();
        postDao = PostDaoImpl.getInstance();
    }

    @AfterAll
    static void afterAll() {
        container.stop();
    }


    @Test
    void checkSave() {
        String expectedTitle = "Архитектор";
        PostEntity postEntity = new PostEntity(
                expectedTitle
        );
        postEntity = postDao.save(postEntity);
        Optional<PostEntity> result = postDao.findById(postEntity.getId());

        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(expectedTitle, result.get().getTitle());
    }

    @DisplayName("findBy_ID")
    @ParameterizedTest
    @CsvSource(value = {
            "1, true",
            "30, false"
    })
    void findById(Long expectedId, Boolean expectedValue) {
        Optional<PostEntity> post = postDao.findById(expectedId);

        Assertions.assertEquals(expectedValue, post.isPresent());
        post.ifPresent(postEntity -> Assertions.assertEquals(expectedId, postEntity.getId()));
    }

    @Test
    void checkUpdate() {
        String expectedTitle = "Директор";

        PostEntity post = postDao.findById(1L).get();
        String oldTitle = post.getTitle();
        post.setTitle(expectedTitle);
        postDao.update(post);

        PostEntity result = postDao.findById(1L).get();

        Assertions.assertNotEquals(expectedTitle, oldTitle);
        Assertions.assertEquals(expectedTitle, result.getTitle());
    }
    @Test
    void deleteById() {
        Boolean expectedValue = true;
        int expectedSize = postDao.findAll().size();

        PostEntity tempPost = new PostEntity("Дизайнер");
        tempPost = postDao.save(tempPost);

        int resultSizeBefore = postDao.findAll().size();
        Assertions.assertNotEquals(expectedSize, resultSizeBefore);

        boolean resultDelete = postDao.deleteById(tempPost.getId());
        int resultSizeAfter = postDao.findAll().size();

        Assertions.assertEquals(expectedValue, resultDelete);
        Assertions.assertEquals(expectedSize, resultSizeAfter);
    }

    @Test
    void findAll() {
        int expectedSize = 1;
        int resultSize = postDao.findAll().size();

        Assertions.assertEquals(expectedSize, resultSize);
    }
}