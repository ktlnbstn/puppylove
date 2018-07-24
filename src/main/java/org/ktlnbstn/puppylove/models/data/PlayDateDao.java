package org.ktlnbstn.puppylove.models.data;

import org.ktlnbstn.puppylove.models.DogParks;
import org.ktlnbstn.puppylove.models.PlayDate;
import org.ktlnbstn.puppylove.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.ArrayList;

@Repository
@Transactional
public interface PlayDateDao extends CrudRepository<PlayDate, Integer> {

    PlayDate findById(Integer id);

}
