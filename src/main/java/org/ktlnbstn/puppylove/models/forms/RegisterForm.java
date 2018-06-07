package org.ktlnbstn.puppylove.models.forms;

import org.hibernate.validator.constraints.Email;
import org.ktlnbstn.puppylove.models.DogParks;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class RegisterForm {

    @Email(message = "Not a valid email address")
    @Size(min = 1, message = "Must be a valid email address")
    private String email;

    @NotNull
    @Size(min = 2, max = 30, message = "Invalid name")
    private String name;

    @NotNull
    private int age;

    @NotNull
    @Pattern(regexp = "(\\S){4,20}", message = "Password must have 4-20 characters")
    private String password;

    @NotNull(message = "Passwords do not match")
    private String verifyPassword;

    @NotNull
    private DogParks dogParkLocation;

    public RegisterForm() {}

    public String getVerifyPassword() {
        return verifyPassword;
    }

    public void setVerifyPassword(String verifyPassword) {
        this.verifyPassword = verifyPassword;
        checkPasswordForRegistration();
    }

    private void checkPasswordForRegistration() {
        if (!getPassword().equals(verifyPassword)) {
            verifyPassword = null;
        }
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public DogParks getDogParkLocation() {
        return dogParkLocation;
    }

    public void setDogParkLocation(DogParks dogParkLocation) {
        this.dogParkLocation = dogParkLocation;
    }
}
