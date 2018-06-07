package org.ktlnbstn.puppylove.models.forms;

import javax.validation.constraints.NotNull;

public class SearchForm {
    //TODO allllll needs to be changed to findbydogpark

    @NotNull
    private String location;

    public String getLocation() { return location; }

    public void setLocation(String location) { this.location = location; }
}
