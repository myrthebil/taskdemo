package se.myrthe.commonmodel.repository;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;
import se.myrthe.commonmodel.model.User;

/**
 * Repository interface for {@link User} entities.
 * <p>
 * This interface extends {@link ListCrudRepository} to provide basic CRUD
 * (Create, Read, Update, Delete) operations for {@link User} objects, along
 * with additional methods for list-based operations. It is marked with the
 * {@code @Repository} annotation, indicating that it is a Spring Data
 * repository responsible for accessing the underlying database for {@link User}
 * entities.
 *
 * @see User
 * @see ListCrudRepository
 */
@Repository
public interface UserRepository extends ListCrudRepository<User, Integer> {

}
