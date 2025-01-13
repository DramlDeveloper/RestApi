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
        if (postEntity != null) {
            return new PostDtoOut(
                    postEntity.getId(),
                    postEntity.getTitle());

        }
        return null;
    }

    @Override
    public PostDtoOut mapPostDtoIn(PostDtoIn postDtoIn) {
        if (postDtoIn != null) {
            return new PostDtoOut(
                    postDtoIn.getId(),
                    postDtoIn.getTitle()
            );
        }
        return null;
    }

    @Override
    public PostEntity map(PostDtoIn postDtoOut) {
        if (postDtoOut != null) {
            return new PostEntity(
                    postDtoOut.getId(),
                    postDtoOut.getTitle()
            );
        }
        return null;
    }
}
