package rest.api.rest_service.entity;

import java.util.List;

public class PostEntity {

    private final Long id;
    private final String title;

    // One-to-Many
    private List<StaffEntity> staff;

    private PostEntity(PostEntityBuilder buildr) {
        this.id = buildr.id;
        this.title = buildr.title;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public static class PostEntityBuilder {
        private Long id;
        private String title;

        PostEntityBuilder() {
        }

        public PostEntityBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public PostEntityBuilder title(String title) {
            this.title = title;
            return this;
        }

        public PostEntity build() {
            return new PostEntity(this);
        }
    }
}
