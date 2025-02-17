package se.myrthe.commonmodel.repository;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;
import se.myrthe.commonmodel.model.User;

@Repository
public interface UserRepository extends ListCrudRepository<User, Integer> {

}
