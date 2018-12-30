package edu.uit.dao;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import edu.uit.models.FacebookData;

public interface FacebookDataRepository extends MongoRepository<FacebookData, String>{
	FacebookData findBy_id(ObjectId _id);
}
