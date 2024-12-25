package dao;

import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.HostConfig;
import com.github.dockerjava.api.model.PortBinding;
import com.github.dockerjava.api.model.Ports;
import org.junit.jupiter.api.*;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import rest.api.rest_service.dao.IPostDao;
import rest.api.rest_service.dao.impl.PostDaoImpl;
import rest.api.rest_service.entity.PostEntity;
import rest.api.rest_service.util.PropertiesUtil;

import java.util.Optional;

@Testcontainers
@Tag("DockerRequired")
class PostDaoImplTest {
    private static final String INIT_SQL = "script.sql";
    private static int containerPort = 5432;
    private static int localPort = 5432;
    public static IPostDao postDao;
    @Container
    public static PostgreSQLContainer<?> container = new PostgreSQLContainer<>("postgres:14.1-alpine")
            .withDatabaseName("postgres")
            .withUsername(PropertiesUtil.getProperty("db.username"))
            .withPassword(PropertiesUtil.getProperty("db.password"))
            .withExposedPorts(containerPort)
            .withCreateContainerCmdModifier(cmd -> cmd.withHostConfig(
                    new HostConfig().withPortBindings(new PortBinding(Ports.Binding.bindPort(localPort), new ExposedPort(containerPort)))
            ))
            .withInitScript(INIT_SQL);

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


    @Test
    void findById() {
        checkSave();
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