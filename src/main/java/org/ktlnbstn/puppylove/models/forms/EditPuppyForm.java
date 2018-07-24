package org.ktlnbstn.puppylove.models.forms;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class EditPuppyForm {

    @NotNull
    @Size(min=2, max=15, message = "Name must be between 2 - 15 characters.")
    private String name;

    @NotNull
    @Size(min=2, max=15)
    private String size;

    @NotNull
    @Size(min=0, max=250, message = "Description must be between 0 - 250 characters.")
    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
