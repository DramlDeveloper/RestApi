package rest.api.rest_service.entity;

public class CompanyEntity {

    private final Long id;
    private final String name;
    private final String phone;


    private CompanyEntity(CompanyEntityBuilder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.phone = builder.phone;
    }


    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public static class CompanyEntityBuilder {
        private Long id;
        private String name;
        private String phone;

        public CompanyEntityBuilder() {
        }

        public CompanyEntityBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public CompanyEntityBuilder name(String name) {
            this.name = name;
            return this;
        }

        public CompanyEntityBuilder phone(String phone) {
            this.phone = phone;
            return this;
        }

        public CompanyEntity build() {
            return new CompanyEntity(this);
        }
    }
}
