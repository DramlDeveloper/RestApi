package rest.api.rest_service.entity;


import java.util.Objects;


public class CompanyEntity {

    private Long id;
    private String name;
    private String city;

    public CompanyEntity(Long id, String name, String city) {
        this.id = id;
        this.name = name;
        this.city = city;
    }

    public CompanyEntity(String name, String city) {
        this.name = name;
        this.city = city;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCity() {
        return city;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        CompanyEntity that = (CompanyEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(city, that.city);
    }
}