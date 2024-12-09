package rest.api.rest_service.service.dto;

public class CompanyDtoOut {
   private Long id;
   private String description;

   public CompanyDtoOut() {}

    public CompanyDtoOut(String description) {
        this.description = description;
    }

    public CompanyDtoOut(Long id, String description) {
        this.id = id;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public String getDescription() {
       return description;
    }

    @Override
    public String toString() {
        return "CompanyDtoOut [id=" + id + ", description=" + description + "]";
    }
}