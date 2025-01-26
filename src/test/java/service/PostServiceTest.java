package service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import rest.api.rest_service.dao.IPostDao;
import rest.api.rest_service.entity.PostEntity;
import rest.api.rest_service.service.dto.PostDtoIn;
import rest.api.rest_service.service.dto.PostDtoOut;
import rest.api.rest_service.service.impl.PostService;

import java.util.Arrays;
import java.util.Optional;


public class PostServiceTest {
    PostDtoIn dtoIn = new PostDtoIn(1L, "Дизайнер");
    PostDtoOut dtoOut = new PostDtoOut(1L, "Дизайнер");
    PostEntity postEntity = new PostEntity(1L, "Дизайнер");

    @Mock
    private IPostDao postDao;
    @InjectMocks
    private PostService postService;

    @BeforeEach
    void beforeEach() {
        MockitoAnnotations.initMocks(this);

    }


    @Test
    void save_postService() {
        Mockito.when(postDao.save(postEntity)).thenReturn(postEntity);

        Assertions.assertEquals("Дизайнер", postService.save(dtoIn).getTitle());
        Assertions.assertEquals(1L, postService.save(dtoIn).getId());

    }
    @Test
    void findById_postService() {
        Optional<PostEntity> postEntityOptional = Optional.of(new PostEntity(1L, "Дизайнер"));

        Mockito.when(postDao.findById(Mockito.anyLong())).thenReturn(postEntityOptional);

        Assertions.assertEquals("Дизайнер", postService.findById(dtoOut.getId()).getTitle());
        Assertions.assertEquals(1L, postService.findById(dtoOut.getId()).getId());
    }

    @Test
    void update_postService() {
        Mockito.doReturn(true).when(postDao).update(postEntity);

        Assertions.assertEquals(dtoOut.getTitle(), postService.update(dtoIn).getTitle());
        Assertions.assertEquals(dtoOut.getId(), postService.update(dtoIn).getId());
    }

    @Test
    void update_postServiceNotFound() {
        Mockito.doReturn(true).when(postDao).update(postEntity);
        PostDtoOut notFound = new PostDtoOut(0L, "not found");

        Assertions.assertEquals(notFound.getTitle(), postService.update(null).getTitle());

    }

    @Test
    void deleteById_postService() {
        Long expectedId = 1L;

        Mockito.when(postDao.deleteById(expectedId)).thenReturn(true);

        Assertions.assertTrue(postService.deleteById(expectedId));
    }

    @Test
    void findAll_postService() {
        Mockito.when(postDao.findAll()).thenReturn(Arrays.asList(
                new PostEntity(2L, "Дизайнер"),
                new PostEntity(3L, "Архитектор")
        ));

        Assertions.assertEquals(2, postService.findAll().size());
    }
}