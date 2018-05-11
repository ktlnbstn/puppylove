package org.ktlnbstn.puppylove.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
public class Puppy {

    @Id
    @GeneratedValue
    private int id;

    @NotNull
    @Size(min=2, max=15)
    private String name;

    @NotNull
    @Size(min=2, max=15)
    private String breed;

    @NotNull
    private String ageMonth;

    @NotNull
    private int ageYear;

    @NotNull
    @Size(min=2, max=15)
    private String size;

    @ManyToOne
    private User user;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "puppy_playdate",
            joinColumns =  @JoinColumn(name = "puppy_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "playdate_id", referencedColumnName = "id"))
    private Set<PlayDate> playDates;

    public Set<PlayDate> getPlayDates(){ return playDates; }

    public void setPlayDates(Set<PlayDate> playDates){ this.playDates = playDates; }

    public Puppy(String name, String breed, String ageMonth, int ageYear, String size, User user) {
        this.name = name;
        this.breed = breed;
        this.ageMonth = ageMonth;
        this.ageYear = ageYear;
        this.size = size;
        this.user = user;
    }

    public Puppy() { }

    public int getId() {

        return id;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {

        this.name = name;
    }

    public String getAgeMonth() {

        return ageMonth;
    }

    public void setAgeMonth(String ageMonth) {

        this.ageMonth = ageMonth;
    }

    public int getAgeYear() {

        return ageYear;
    }

    public void setAgeYear(int ageYear) {

        this.ageYear = ageYear;
    }

    public String getBreed() {

        return breed;
    }

    public void setBreed(String breed) {

        this.breed = breed;
    }

    public String getSize() {

        return size;
    }

    public void setSize(String size) {

        this.size = size;
    }

    public User getUser() {

        return user;
    }

    public void setUser(User user) {

        this.user = user;
    }

}


