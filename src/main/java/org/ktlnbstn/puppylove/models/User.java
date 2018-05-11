package org.ktlnbstn.puppylove.models;

import org.hibernate.validator.constraints.Email;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.*;

@Entity
public class User {

    @Id
    @GeneratedValue
    int id;

    @NotNull
    @Size(min = 2, max = 25, message = "Name must be between 2 and 25 characters")
    String name;

    @Email
    @Size(min = 1, message = "Invalid email")
    String email;

    @NotNull
    private String pwHash;
    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @NotNull
    int age;

    @NotNull
    @Size(min = 0, max = 250)
    String description;

    @NotNull
    @Size(min = 0, max = 25)
    String location;

    public User() {
    }

    public User(String name, int age, String email, String password) {
        this.name = name;
        this.age = age;
        this.email = email;
        this.pwHash = hashPassword(password);
        this.description = "";
        this.location = "";
    }

    @OneToMany
    @JoinTable(name = "user_puppy",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "puppy_id", referencedColumnName = "id"))

    private Set<Puppy> puppies;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "user_playdate",
            joinColumns =  @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "playdate_id", referencedColumnName = "id"))

    private Set<PlayDate> playDates;

    public Set<PlayDate> getPlayDates(){ return playDates; }

    public void setPlayDates(Set<PlayDate> playDates){
        this.playDates = playDates;
    }

    public String getLocation() { return location; }

    public void setLocation(String location) { this.location = location; }

    public Set<Puppy> getPuppies(){ return this.puppies; }

    public void setPuppies(Set<Puppy> puppies) {
        this.puppies = puppies;
    }

    private static String hashPassword(String password) {
        return encoder.encode(password);
    }

    public boolean isMatchingPassword(String password) {
        return encoder.matches(password, pwHash);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        age = age;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void addPuppy(Puppy puppy) {
        this.puppies.add(puppy);
    }

    public void removePuppy(Puppy puppy) {
        this.puppies.remove(puppy);
    }
}

