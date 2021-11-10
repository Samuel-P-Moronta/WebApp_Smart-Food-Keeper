package WEBAPP_SFK.models.enums;

import javax.persistence.criteria.CriteriaBuilder;

public enum Gender {
    FEMALE(1,"Femenino"),MALE(2,"Masculino");
    private Integer id;
    private String status;

    Gender(Integer id, String status){
        this.id = id;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
