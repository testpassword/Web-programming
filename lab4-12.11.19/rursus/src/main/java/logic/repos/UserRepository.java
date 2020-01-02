package logic.repos;

import logic.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * CRUD-репозиторий для сущности User.
 */
@Repository public interface UserRepository extends CrudRepository<User, Long> {

    boolean existsByEmail(String email);

    long removeByEmailAndPassword(String email, String password);

    User getByEmail(String email);
}