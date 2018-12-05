package edu.uit.dao;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import edu.uit.models.YoutubeData;

public interface YoutubeDataRepository extends MongoRepository<YoutubeData, String>{
	YoutubeData findBy_id(ObjectId _id);
}
