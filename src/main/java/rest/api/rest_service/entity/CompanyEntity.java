package rest.api.rest_service.entity;


public class CompanyEntity {

    private final Long id;
    private final String name;
    private final String address;

    // Many-to-One
    private StaffEntity staff;


    private CompanyEntity(CompanyEntityBuilder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.address = builder.address;
    }


    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public static class CompanyEntityBuilder {
        private Long id;
        private String name;
        private String address;

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

        public CompanyEntityBuilder address(String address) {
            this.address = address;
            return this;
        }

        public CompanyEntity build() {
            return new CompanyEntity(this);
        }
    }
}
