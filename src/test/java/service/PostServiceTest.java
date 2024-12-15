package service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import rest.api.rest_service.dao.Dao;
import rest.api.rest_service.dao.impl.CompanyDaoImpl;
import rest.api.rest_service.dao.impl.PostDaoImpl;
import rest.api.rest_service.entity.CompanyEntity;
import rest.api.rest_service.entity.PostEntity;
import rest.api.rest_service.exception.DaoException;
import rest.api.rest_service.service.CompanyService;
import rest.api.rest_service.service.PostService;
import rest.api.rest_service.service.dto.CompanyDtoIn;
import rest.api.rest_service.service.dto.CompanyDtoOut;
import rest.api.rest_service.service.dto.PostDtoIn;
import rest.api.rest_service.service.dto.PostDtoOut;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class PostServiceTest {

    @InjectMocks
    private static PostService postService;

    @Mock
    private static PostDaoImpl postDao;

    @Test
    void save_postService() {
        PostDtoIn dto = new PostDtoIn("Дизайнер");
        PostEntity postEntity = new PostEntity("Дизайнер");

        Mockito.when(postDao.save(Mockito.any(PostEntity.class))).thenReturn(postEntity);

        PostDtoOut savePost = postService.save(dto);
        Assertions.assertNotNull(savePost);
        Assertions.assertEquals( "Дизайнер", savePost.getTitle());
    }

    @Test
    void update_postService() {
        Long expectedId = 1L;
        PostDtoIn dto = new PostDtoIn(expectedId, "Архитектор");

        Mockito.when(postDao.update(Mockito.any(PostEntity.class))).thenReturn(true);

        Assertions.assertTrue(postService.update(dto));
        Assertions.assertEquals("Архитектор", postService.findById(expectedId).getTitle());
    }

    @Test
    void findById_postService() {

        Optional<PostEntity> postEntity =  Optional.of(new PostEntity());

        Mockito.when(postDao.findById(Mockito.anyLong())).thenReturn(postEntity);

        Assertions.assertEquals( "Архитектор", postService.findById(2L).getTitle());
    }

    @Test
    void deleteById_postService()  {
        Long expectedId = 1L;

        Mockito.when(postDao.deleteById(Mockito.anyLong())).thenReturn(true);

        Assertions.assertTrue(postService.deleteById(expectedId));
    }

    @Test
    void findByIdNotFound() {
        Optional<PostEntity> postEntity = Optional.empty();

        Mockito.when(postDao.findById(Mockito.anyLong())).thenReturn(postEntity);

        var exception = Assertions.assertThrows(DaoException.class, () -> {
            postService.findById(0L);
        });

       Assertions.assertEquals("Найти не удалось проверьте верны ли параметры", exception.getMessage());
    }

    @Test
    void findAll_postService() {
        List<PostEntity> postEntities = Arrays.asList(new PostEntity());

        Mockito.when(postDao.findAll()).thenReturn(postEntities);
    }
}