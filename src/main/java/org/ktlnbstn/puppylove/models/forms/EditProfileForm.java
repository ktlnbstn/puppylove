package org.ktlnbstn.puppylove.models.forms;

import org.hibernate.validator.constraints.Email;
import org.ktlnbstn.puppylove.models.DogParks;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class EditProfileForm {

    @Email(message = "Not a valid email address.")
    @Size(min = 1, message = "Must be a valid email address.")
    private String email;

    @NotNull
    @Size(min = 2, max = 30, message = "Name must be between 2 - 30 characters.")
    private String name;

    @NotNull
    @Size(min = 0, max = 250, message = "Description must be between 0 - 250 characters.")
    String description;

    @NotNull
    private DogParks dogParkLocation;

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

    public DogParks getDogParkLocation() {
        return dogParkLocation;
    }

    public void setDogParkLocation(DogParks dogParkLocation) {
        this.dogParkLocation = dogParkLocation;
    }
}
