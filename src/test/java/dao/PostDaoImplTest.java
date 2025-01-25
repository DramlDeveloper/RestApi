package dao;

import org.junit.jupiter.api.*;
import rest.api.rest_service.dao.IPostDao;
import rest.api.rest_service.dao.impl.PostDaoImpl;
import rest.api.rest_service.entity.PostEntity;

import java.util.Optional;

import static dao.PostgresContainerTest.container;

class PostDaoImplTest {

    public static IPostDao postDao;

    @BeforeEach
     void startContainers() {
        container.start();
        postDao = PostDaoImpl.getInstance();
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


    @Test
    void findById() {
        postDao.save(new PostEntity("Архитектор"));
        Optional<PostEntity> post = postDao.findById(1L);

        Assertions.assertTrue(post.isPresent());
        Assertions.assertEquals("Архитектор", post.get().getTitle());
        Assertions.assertEquals(1L, post.get().getId());
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
        int resultSize = postDao.findAll().size();

        Assertions.assertEquals(0, resultSize);
    }
}