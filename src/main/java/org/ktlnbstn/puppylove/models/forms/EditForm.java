package org.ktlnbstn.puppylove.models.forms;

import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class EditForm {

    @Email(message = "Not a valid email address")
    @Size(min = 1, message = "Must be a vaild email address")
    private String email;

    @NotNull
    @Size(min = 2, max = 30, message = "Invalid name")
    private String name;

    @NotNull
    @Size(min = 0, max = 250)
    String description;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
