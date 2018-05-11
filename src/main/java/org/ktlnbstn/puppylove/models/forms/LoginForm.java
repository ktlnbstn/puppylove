package org.ktlnbstn.puppylove.models.forms;

import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class LoginForm {

    @Email
    @Size(min = 0, message = "Not a valid email address")
    private String email;

    @NotNull
    @Pattern(regexp = "(\\S){4,20}", message = "Invalid password")
    private String password;

    public LoginForm() {}

    public String getEmail() {
        return email;
    }

    public void setEmail(String username) {
        if(username == "")
            username = null;
        else
            this.email = username;
    }

    public String getPassword() { return password; }

    public void setPassword(String password) {
        this.password = password;
    }
}
