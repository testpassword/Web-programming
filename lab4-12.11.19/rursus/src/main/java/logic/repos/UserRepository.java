package logic.repos;

import logic.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository public interface UserRepository extends CrudRepository<User, Long> {

    boolean existsByEmail(String email);

    long removeByEmailEqualsAndPasswordEquals(User user);

    User getByEmail(String email);
}