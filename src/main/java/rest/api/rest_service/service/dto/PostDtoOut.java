package rest.api.rest_service.service.dto;

public class PostDtoOut {
    private Long id;
    private String title;

    public PostDtoOut() {
    }
    public PostDtoOut(Long id, String title) {
        this.id = id;
        this.title = title;
    }

    PostDtoOut(String title) {
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

}
