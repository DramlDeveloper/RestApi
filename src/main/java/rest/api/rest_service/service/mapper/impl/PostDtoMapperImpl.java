package rest.api.rest_service.service.mapper.impl;

import rest.api.rest_service.entity.PostEntity;
import rest.api.rest_service.service.dto.PostDtoIn;
import rest.api.rest_service.service.dto.PostDtoOut;
import rest.api.rest_service.service.mapper.IPostDtoMapper;

public class PostDtoMapperImpl implements IPostDtoMapper {

    private static final IPostDtoMapper INSTANCE = new PostDtoMapperImpl();

    public static IPostDtoMapper getINSTANCE() {
        return INSTANCE;
    }

    @Override
    public PostDtoOut map(PostEntity postEntity) {
        PostDtoOut postDtoOut = new PostDtoOut();
        if (postEntity != null) {
            postDtoOut = new PostDtoOut(
                    postEntity.getId(),
                    postEntity.getTitle());
        }
        return postDtoOut;
    }

    @Override
    public PostDtoOut mapPostDtoIn(PostDtoIn postDtoIn) {
            return new PostDtoOut(
                    postDtoIn.getId(),
                    postDtoIn.getTitle()
            );
    }

    @Override
    public PostEntity map(PostDtoIn postDtoIn) {
        return new PostEntity(
                postDtoIn.getId(),
                postDtoIn.getTitle()
        );
    }
}
