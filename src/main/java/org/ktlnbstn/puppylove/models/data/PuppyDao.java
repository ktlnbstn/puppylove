package org.ktlnbstn.puppylove.models.data;

import org.ktlnbstn.puppylove.models.Puppy;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface PuppyDao extends CrudRepository<Puppy, Integer> {

}

