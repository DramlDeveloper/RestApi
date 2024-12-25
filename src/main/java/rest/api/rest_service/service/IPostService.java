package rest.api.rest_service.service;

import rest.api.rest_service.service.dto.PostDtoIn;
import rest.api.rest_service.service.dto.PostDtoOut;

import java.util.List;

public interface IPostService {
    List<PostDtoOut> findAll();

    PostDtoOut findById(Long id);

    PostDtoOut save(PostDtoIn postDtoIn);

    boolean deleteById(Long id);

    boolean update(PostDtoIn postDtoIn);
}
