package org.ktlnbstn.puppylove.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Set;

@Entity
public class PlayDate {

    @Id
    @GeneratedValue
    private int id;

    @NotNull
    private DogParks dogParkLocation;

    @NotNull
    private Date date;

    @NotNull
    private String description;

    @ManyToMany(mappedBy = "playDates")
    private Set<User> users;

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public Set<User> getUsers() {
        return users;
    }

    public DogParks getDogParkLocation() {
        return dogParkLocation;
    }

    public void setDogParkLocation(DogParks dogParkLocation) {
        this.dogParkLocation = dogParkLocation;
    }

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
}
