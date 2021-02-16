package org.example.hospital.DTO;

import org.example.hospital.entity.Category;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotBlank;


public class DoctorDTO {
    @NotBlank(message = "label.loginBlank")
    @Length(max = 20, message = "label.loginTooLong")
    private String login;

    @NotBlank(message = "label.passwordBlank")
    private String password;

    private Category category;

    @NotBlank(message = "label.nameBlank")
    @Length(max = 20,message = "label.nameTooLong")
    private String name;

    public DoctorDTO(){}

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
