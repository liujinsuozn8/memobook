package com.ljs.mvc.bean;

public class CustomerSqlParam {
    public String id;
    public String name;
    public String email;

    public CustomerSqlParam() {
    }

    public CustomerSqlParam(String id, String name, String email, boolean criteriaCustomer) {
        if (criteriaCustomer){
            if (id == null || id.isEmpty()){
                this.id="%%";
            }else {
                this.id = "%"+id+"%";
            }
            if (name == null ||name.isEmpty()){
                this.name="%%";
            }else {
                this.name = "%"+name+"%";
            }
            if (email == null || email.isEmpty()){
                this.email="%%";
            }else {
                this.email = "%"+email+"%";
            }

        } else {
            this.id = id;
            this.name = name;
            this.email = email;
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
