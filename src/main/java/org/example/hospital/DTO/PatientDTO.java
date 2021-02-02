package org.example.hospital.DTO;

import com.sun.istack.internal.NotNull;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

public class PatientDTO {
    @NotBlank
    @Length(max = 20)
    private String login;

    @NotBlank
    private String password;

    @NotBlank
    @Length(max = 20)
    private String name;

    @NotNull
    @Min(0)
    @Max(130)
    private Integer age;

    public PatientDTO(){}

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }


}
