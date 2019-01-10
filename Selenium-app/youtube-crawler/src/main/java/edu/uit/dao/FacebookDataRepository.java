package edu.uit.dao;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.data.rest.core.annotation.*;
import edu.uit.models.FacebookData;

@CrossOrigin(origins="*")
@RepositoryRestResource
public interface FacebookDataRepository extends MongoRepository<FacebookData, String>{
	FacebookData findBy_id(ObjectId _id);
}
