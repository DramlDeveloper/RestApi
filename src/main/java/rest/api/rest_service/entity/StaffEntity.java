package rest.api.rest_service.entity;


import java.util.Objects;

public class StaffEntity {

    private int id;
    private String firstName;
    private String lastName;

    // Many-to-One
    private PostEntity post;
    private CompanyEntity company;

    public StaffEntity(int id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public StaffEntity() {
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

    public void setId(int id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        StaffEntity that = (StaffEntity) o;
        return id == that.id && Objects.equals(firstName, that.firstName)
                             && Objects.equals(lastName, that.lastName)
                             && Objects.equals(post, that.post)
                             && Objects.equals(company, that.company);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, post, company);
    }
}
