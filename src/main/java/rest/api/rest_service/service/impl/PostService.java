package rest.api.rest_service.service.impl;

import rest.api.rest_service.dao.IPostDao;
import rest.api.rest_service.dao.impl.PostDaoImpl;
import rest.api.rest_service.entity.PostEntity;
import rest.api.rest_service.exception.DaoException;
import rest.api.rest_service.service.IPostService;
import rest.api.rest_service.service.dto.PostDtoIn;
import rest.api.rest_service.service.dto.PostDtoOut;
import rest.api.rest_service.service.mapper.IPostDtoMapper;
import rest.api.rest_service.service.mapper.impl.PostDtoMapperImpl;

import java.util.List;
import java.util.NoSuchElementException;

public class PostService implements IPostService {
    private final static PostService INSTANCE = new PostService();
    private final IPostDao postDao = PostDaoImpl.getInstance();
    private final IPostDtoMapper postDtoMapper = PostDtoMapperImpl.getINSTANCE();

    public static PostService getInstance() {
        return INSTANCE;
    }


    private PostService() {
    }

    public List<PostDtoOut> findAll() {
        return postDao.findAll().stream()
                .map(postDtoMapper::map).toList();
    }

    public PostDtoOut findById(Long id) {
            return postDao.findById(id).stream()
                    .map(postDtoMapper::map)
                    .findAny()
                    .orElse(new PostDtoOut(0L, "not found"));
    }

    public PostDtoOut save(PostDtoIn postDtoIn) {
        var id = postDao.save(postDtoMapper.map(postDtoIn)).getId();
        return findById(id);
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
            postDao.update(postDtoMapper.map(postDtoIn));
            return postDtoMapper.mapPostDtoIn(postDtoIn);
        }
        return new PostDtoOut(0L, "not found");
    }
}