package edu.uit.dao;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.data.rest.core.annotation.*;
import edu.uit.models.YoutubeData;

@CrossOrigin(origins="*")
@RepositoryRestResource
public interface YoutubeDataRepository extends MongoRepository<YoutubeData, String>{
	YoutubeData findBy_id(ObjectId _id);
}
