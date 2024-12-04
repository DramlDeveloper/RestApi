package rest.api.rest_service.entity;

public class StaffEntity {

    private final int id;
    private final String firstName;
    private final String lastName;

    private StaffEntity(StaffEntityBuilder builder) {
        this.id = builder.id;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public static class StaffEntityBuilder {
        private int id;
        private String firstName;
        private String lastName;
        public StaffEntityBuilder() {
        }

        public StaffEntityBuilder setId(int id) {
            this.id = id;
            return this;
        }
        public StaffEntityBuilder setFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }
        public StaffEntityBuilder setLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }
        public StaffEntity build() {
            return new StaffEntity(this);
        }
    }
}
