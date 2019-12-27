package logic;

import models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository public interface UserRepository extends CrudRepository<User, Long> {

    boolean existsByEmail(String email);

    User getByEmailAndPassword(String email, String password);
}