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
    private String location;

    @NotNull
    private String dogPark;

    @NotNull
    private Date playDate;

    @NotNull
    private String description;

    @ManyToMany(mappedBy = "playDates")
    private Set<User> users;

    public void setUsers(Set<User> users) { this.users = users; }

    public Set<User> getUsers() { return users; }

    @ManyToMany(mappedBy = "playDates")
    private Set<Puppy> puppies;

    public void setPuppies(Set<Puppy> puppies) { this.puppies = puppies; }

    public Set<Puppy> getPuppies() { return puppies; }

    public String getLocation() { return location; }

    public void setLocation(String location) { this.location = location; }

    public String getDogPark() { return dogPark; }

    public void setDogPark(String dogPark) { this.dogPark = dogPark; }

    public Date getPlayDate() { return playDate; }

    public void setPlayDate(Date playDate) { this.playDate = playDate; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }
}
