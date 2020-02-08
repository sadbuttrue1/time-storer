package me.sadbuttrue.repository;

import me.sadbuttrue.model.Time;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TimeRepository extends MongoRepository<Time, String> {
}
