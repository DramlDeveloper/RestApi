package rest.api.rest_service.service.dto;

public class StaffDtoOut {

    private final Long id;
    private final String firstName;
    private final String lastName;
    private final String nameCompany;
    private final String titlePost;

    public StaffDtoOut(Long id, String firstName, String lastName, String nameCompany, String titlePost) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.nameCompany = nameCompany;
        this.titlePost = titlePost;
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

    public String getNameCompany() {
        return nameCompany;
    }

    public String getTitlePost() {
        return titlePost;
    }
}
