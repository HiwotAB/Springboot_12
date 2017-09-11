package com.hiwotab.springboot12.repositories;

import com.hiwotab.springboot12.model.Actor;
import org.springframework.data.repository.CrudRepository;

public interface ActorRepostory extends CrudRepository<Actor,Long> {
}
