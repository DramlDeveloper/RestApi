package rest.api.rest_service.service;

import rest.api.rest_service.dao.impl.PostDaoImpl;
import rest.api.rest_service.entity.PostEntity;
import rest.api.rest_service.service.dto.PostDtoIn;
import rest.api.rest_service.service.dto.PostDtoOut;

import java.util.List;

public class PostService {
    private final static PostService INSTANCE = new PostService();
    private final PostDaoImpl postDao = PostDaoImpl.getInstance();

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
        return postDao.findById(id).stream()
                .map(entry -> new PostDtoOut(entry.getId(),
                        entry.getTitle()))
                .findAny()
                .orElseThrow();
    }

    public PostDtoOut save(PostDtoIn postDtoIn) {
        var id = postDao.save(new PostEntity(
                postDtoIn.getTitle())
        ).getId();
        return new PostDtoOut(
                id, postDtoIn.getTitle());
    }

    public void deleteById(Long id) {
        postDao.deleteById(id);
    }

    public void deleteAll() {
        for (PostEntity postEntity : postDao.findAll()) {
            postDao.deleteById(postEntity.getId());
        }
    }

    public void update(PostDtoIn postDtoIn) {
        postDao.update(new PostEntity(
                postDtoIn.getId(),
                postDtoIn.getTitle()
        ));
    }


}
