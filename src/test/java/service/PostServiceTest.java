package service;

import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import rest.api.rest_service.dao.IPostDao;
import rest.api.rest_service.dao.impl.PostDaoImpl;
import rest.api.rest_service.entity.PostEntity;
import rest.api.rest_service.exception.DaoException;
import rest.api.rest_service.service.IPostService;
import rest.api.rest_service.service.dto.PostDtoIn;
import rest.api.rest_service.service.dto.PostDtoOut;
import rest.api.rest_service.service.impl.PostService;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


class PostServiceTest {
    private static IPostService postService;
    private static IPostDao mockPostDao;
    private static PostDaoImpl oldPostDao;
    private static PostService mockPostService;


    private static void setMock(IPostDao mock) {
        try {
            Field instance = PostDaoImpl.class.getDeclaredField("INSTANCE");
            instance.setAccessible(true);
            oldPostDao = (PostDaoImpl) instance.get(instance);
            instance.set(instance, mock);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @BeforeAll
    static void beforeAll() {
        mockPostDao = Mockito.mock(IPostDao.class);
        setMock(mockPostDao);
        postService = PostService.getInstance();
        mockPostService = Mockito.mock(PostService.class);
    }

    @AfterAll
    static void afterAll() throws Exception {
        Field instance = PostService.class.getDeclaredField("INSTANCE");
        instance.setAccessible(true);
    }

    @BeforeEach
    void setUp() {
        Mockito.reset(mockPostDao);
    }

    @Test
    void save_postService() {
        PostDtoIn dto = new PostDtoIn("Дизайнер");
        PostEntity postEntity = new PostEntity("Дизайнер");

        Mockito.when(mockPostDao.save(Mockito.any(PostEntity.class))).thenReturn(postEntity);
        Mockito.when(mockPostService.save(dto)).thenReturn(new PostDtoOut());

    }

    @Test
    void update_postService() {
        Long expectedId = 1L;
        PostDtoIn dto = new PostDtoIn(expectedId, "Архитектор");
        PostService postService = Mockito.mock(PostService.class);

        Mockito.when(mockPostDao.update(Mockito.any(PostEntity.class))).thenReturn(true);
        Mockito.when(postService.update(dto)).thenReturn((Mockito.mock(PostDtoOut.class)));
    }

    @Test
    void findById_postService() {
        Optional<PostEntity> postEntity = Optional.of(new PostEntity(1L, "Архитектор"));

        Mockito.when(mockPostDao.findById(Mockito.anyLong())).thenReturn(postEntity);

        Assertions.assertEquals("Архитектор", postService.findById(1L).getTitle());
    }

    @Test
    void findByIdNotFound() {
        Optional<PostEntity> postEntity = Optional.empty();

        Mockito.when(mockPostDao.findById(Mockito.anyLong())).thenReturn(postEntity);

        var exception = Assertions.assertThrows(DaoException.class, () ->
                postService.findById(0L)
        );

        Assertions.assertEquals("Найти не удалось проверьте верны ли параметры", exception.getMessage());
    }

    @Test
    void findAll_postService() {
        List<PostEntity> postEntities = Arrays.asList(new PostEntity());
        List<PostDtoOut> postDtoOuts = Arrays.asList(new PostDtoOut());

        Mockito.when(mockPostDao.findAll()).thenReturn(postEntities);

        Assertions.assertEquals(postDtoOuts.get(0).getTitle(), postService.findAll().get(0).getTitle());
    }

    @Test
    void deleteById_postService() {
        Long expectedId = 1L;

        Mockito.when(mockPostDao.deleteById(Mockito.anyLong())).thenReturn(true);

        Assertions.assertTrue(postService.deleteById(expectedId));
    }
}