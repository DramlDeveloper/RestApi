package rest.api.rest_service.service.dto;

import org.codehaus.jackson.annotate.JsonPropertyOrder;

@JsonPropertyOrder({"name, address"})
public class CompanyDtoIn {
    private Long id;
    private String name;
    private String city;

    public CompanyDtoIn() {}

    public CompanyDtoIn(String name, String city) {
        this.name = name;
        this.city = city;
    }

    public CompanyDtoIn(Long id, String name, String city) {
        this.id = id;
        this.name = name;
        this.city = city;
    }

    public Long getId() {
        return id;
    }

    public String getName() {return name;}

    public String getCity() {return city;
    }

    public void setId(Long id) {this.id = id;}
}