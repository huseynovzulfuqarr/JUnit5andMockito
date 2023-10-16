package JUnit5.SpringBootApp2.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import JUnit5.SpringBootApp2.model.User;

@DataJpaTest
public class UserRepositoryTest {

	@Autowired
	private UserRepository userRepository;

	private User user;
	private User user1;

	@BeforeEach
	public void init() {
		user = new User();
		user.setFirstName("Cefer");
		user.setLastName("Huseynli");

		user1 = new User();
		user1.setFirstName("Azer");
		user1.setLastName("Qurbanli");
	}

	@Test
	@DisplayName("The User object should be add to the database")
	public void addUserTest() {

		User newUser = this.userRepository.save(user);

		assertThat(newUser).isNotEqualTo(null);
		assertThat(newUser.getFirstName()).isEqualTo("Cefer");
	}

	@Test
	@DisplayName("All users should be return")
	public void getAllUsersTest() {

		this.userRepository.save(user);

		this.userRepository.save(user1);

		List<User> listOfUsers = new ArrayList<>();
		listOfUsers.add(user);
		listOfUsers.add(user1);

		List<User> users = this.userRepository.findAll();

		assertThat(users.size()).isEqualTo(2);
		assertThat(users).isNotEqualTo(null);
	}

	@Test
	@DisplayName("User should be return as its id")
	public void getUserByIdTest() {

		this.userRepository.save(user);

		User existingUser = this.userRepository.findById(user.getId()).get();
		assertThat(existingUser).isNotEqualTo(null);
		assertThat(existingUser.getFirstName()).isEqualTo("Cefer");
	}

	@Test
	@DisplayName("User should be update")
	public void updateUserTest() {

		this.userRepository.save(user);

		User existingUser = this.userRepository.findById(user.getId()).get();
		existingUser.setFirstName("Huseyn");
		User newUser = this.userRepository.save(existingUser);

		assertThat(existingUser.getFirstName()).isEqualTo("Huseyn");
		assertThat(existingUser).isNotEqualTo(null);
	}

	@Test
	@DisplayName("The user object should be delete")
	public void deleteUserTest() {

		this.userRepository.save(user);
		int id = user.getId();

		this.userRepository.save(user1);

		this.userRepository.delete(user);

		Optional<User> existingUser = this.userRepository.findById(id);
		List<User> list = this.userRepository.findAll();
		assertThat(list.size()).isEqualTo(1);
		assertThat(existingUser).isEmpty();
	}
	/*
	 * @Test
	 * 
	 * @DisplayName("The User should be return its name") public void
	 * getUserByName() { User user = new User(); user.setFirstName("Cefer");
	 * user.setLastName("Huseynli"); this.userRepository.save(user);
	 * 
	 * User existingUser=this.userRepository.getUserByName("Cefer");
	 * assertThat(existingUser).isNotEqualTo(null);
	 * assertThat(existingUser.getLastName()).isEqualTo("Huseynli"); }
	 */

}
