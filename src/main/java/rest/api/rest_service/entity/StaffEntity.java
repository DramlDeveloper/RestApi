package rest.api.rest_service.entity;


import java.util.Objects;

public class StaffEntity {

    private Long id;
    private String firstName;
    private String lastName;

    // Many-to-One
    private PostEntity post;
    private CompanyEntity company;

    public StaffEntity(Long id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public StaffEntity(Long id, String firstName, String lastName, PostEntity post, CompanyEntity company) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.post = post;
        this.company = company;
    }

    public StaffEntity(String firstName, String lastName, PostEntity post, CompanyEntity company) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.post = post;
        this.company = company;
    }

    public StaffEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public PostEntity getPost() {
        return post;
    }

    public void setPost(PostEntity post) {
        this.post = post;
    }

    public CompanyEntity getCompany() {
        return company;
    }

    public void setCompany(CompanyEntity company) {
        this.company = company;
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

    @Override
    public String toString() {
        return id + " " + firstName + " " + lastName + " " + post + " " + company;
    }
}
