package rest.api.rest_service.service.dto;

public class PostDtoIn {
    private Long id;
    private String title;

    public PostDtoIn() {}

    public PostDtoIn(Long id, String title) {
        this.id = id;
        this.title = title;
    }
    public PostDtoIn(String title) {
        this.title = title;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
}
