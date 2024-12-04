package rest.api.rest_service.entity;

public class PostEntity {

    private int id;
    private String title;

    private PostEntity(PostEntityBuilder buildr){

    }

    public int getId() {
        return id;
    }
    public String getTitle() {
        return title;
    }
    public static class PostEntityBuilder {
        private int id;
        private String title;

        PostEntityBuilder() {}
        public PostEntityBuilder id(int id) {
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
