package rest.api.rest_service.entity;

import java.util.Objects;

public class PostEntity {
    private  Long id;
    private  String title;

    public PostEntity(String title) {
        this.title = title;
    }

    public PostEntity(Long id, String title) {
        this.id = id;
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

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        PostEntity that = (PostEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(title, that.title);
    }
}