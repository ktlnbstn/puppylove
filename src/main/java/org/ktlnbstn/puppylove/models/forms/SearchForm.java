package org.ktlnbstn.puppylove.models.forms;

import org.ktlnbstn.puppylove.models.DogParks;

import javax.validation.constraints.NotNull;

public class SearchForm {

    @NotNull
    private DogParks dogParkLocation;

    public DogParks getDogParkLocation() { return dogParkLocation; }

    public void setDogParkLocation(DogParks dogParkLocation) { this.dogParkLocation = dogParkLocation; }

}
