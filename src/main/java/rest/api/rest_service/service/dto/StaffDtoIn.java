package rest.api.rest_service.service.dto;

public class StaffDtoIn {

    private Long id;
    private String firstName;
    private String lastName;
    private Long postId;
    private Long companyId;

    public StaffDtoIn(String firstName, String lastName, Long postId, Long companyId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.postId = postId;
        this.companyId = companyId;
    }

    public StaffDtoIn(Long id, String firstName, String lastName, Long postId, Long companyId) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.postId = postId;
        this.companyId = companyId;
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Long getPostId() {
        return postId;
    }

    public Long getCompanyId() {
        return companyId;
    }
}
