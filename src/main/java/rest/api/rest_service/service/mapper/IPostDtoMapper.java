package rest.api.rest_service.service.mapper;

import rest.api.rest_service.entity.PostEntity;
import rest.api.rest_service.service.dto.PostDtoIn;
import rest.api.rest_service.service.dto.PostDtoOut;

public interface IPostDtoMapper {

    PostDtoOut map(PostEntity postEntity);

    PostDtoOut mapPostDtoIn(PostDtoIn postDtoIn);

    PostEntity map(PostDtoIn postDtoOut);
}