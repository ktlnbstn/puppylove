package org.ktlnbstn.puppylove.models.data;

import org.hibernate.validator.internal.constraintvalidators.bv.size.SizeValidatorForArraysOfLong;
import org.ktlnbstn.puppylove.models.DogParks;
import org.ktlnbstn.puppylove.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.ArrayList;

@Repository
@Transactional
public interface UserDao extends CrudRepository<User, Integer> {

    User findByEmail(String email);

    ArrayList<User> findByDogParkLocation(DogParks dogParkLocation);
}
