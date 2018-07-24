package org.ktlnbstn.puppylove.models.forms;

import org.ktlnbstn.puppylove.models.DogParks;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

public class EditPlayDateForm {

    @NotNull
    private DogParks dogParkLocation;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private Date date;

    @NotNull
    @Size(min = 2, message = "Please provide a message.")
    private String description;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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
