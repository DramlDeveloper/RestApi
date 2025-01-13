package rest.api.rest_service.service.impl;

import rest.api.rest_service.dao.IPostDao;
import rest.api.rest_service.dao.impl.PostDaoImpl;
import rest.api.rest_service.entity.PostEntity;
import rest.api.rest_service.exception.DaoException;
import rest.api.rest_service.service.IPostService;
import rest.api.rest_service.service.dto.PostDtoIn;
import rest.api.rest_service.service.dto.PostDtoOut;

import java.util.List;
import java.util.NoSuchElementException;

public class PostService implements IPostService {
    private static PostService INSTANCE = new PostService();
    private final IPostDao postDao = PostDaoImpl.getInstance();

    public static PostService getInstance() {
        return INSTANCE;
    }


    private PostService() {
    }

    public List<PostDtoOut> findAll() {
        return postDao.findAll().stream()
                .map(
                        postEntity -> new PostDtoOut(postEntity.getId(),
                                postEntity.getTitle())
                ).toList();
    }

    public PostDtoOut findById(Long id) {
        try {
            return postDao.findById(id).stream()
                    .map(entry -> new PostDtoOut(entry.getId(),
                            entry.getTitle()))
                    .findAny()
                    .orElseThrow();
        } catch (NoSuchElementException e) {
            throw new DaoException("Найти не удалось проверьте верны ли параметры");
        }

    }

    public PostDtoOut save(PostDtoIn postDtoIn) {
        var id = postDao.save(new PostEntity(
                postDtoIn.getTitle())
        ).getId();
        return new PostDtoOut(
                id, postDtoIn.getTitle());
    }

    public boolean deleteById(Long id) {
        return postDao.deleteById(id);
    }

    public void deleteAll() {
        for (PostEntity postEntity : postDao.findAll()) {
            postDao.deleteById(postEntity.getId());
        }
    }

    public PostDtoOut update(PostDtoIn postDtoIn) {
        if (postDtoIn != null) {
            postDao.update(new PostEntity(
                    postDtoIn.getId(),
                    postDtoIn.getTitle()
            ));
            return new PostDtoOut(
                    postDtoIn.getId(),
                    postDtoIn.getTitle()
            );
        }
        return new PostDtoOut(0L, "not found");
    }
}