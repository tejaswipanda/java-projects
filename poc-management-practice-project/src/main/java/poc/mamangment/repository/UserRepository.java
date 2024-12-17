package poc.mamangment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import poc.mamangment.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

	User findUserByEmail(String email);

}
