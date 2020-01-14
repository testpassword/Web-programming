package logic.repos;

import logic.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * CRUD-репозиторий для {@code User}.
 * @see User
 * @author Артемий Кульбако
 * @version 1.1
 */
@Repository public interface UserRepository extends CrudRepository<User, String> {

    boolean existsByEmail(String email);

    long deleteByEmail(String email);

    User getByEmail(String email);
}