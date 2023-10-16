package JUnit5.SpringBootApp2.repository;



import org.springframework.data.jpa.repository.JpaRepository;

import JUnit5.SpringBootApp2.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {


}
