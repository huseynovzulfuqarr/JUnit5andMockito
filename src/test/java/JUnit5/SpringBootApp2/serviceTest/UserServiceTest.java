package JUnit5.SpringBootApp2.serviceTest;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import JUnit5.SpringBootApp2.model.User;
import JUnit5.SpringBootApp2.repository.UserRepository;
import JUnit5.SpringBootApp2.service.UserService;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

	@InjectMocks
	private UserService userService;

	@Mock
	private UserRepository userRepository;

	private User user;
	private User user1;

	@BeforeEach
	public void init() {
		user = new User();
		user.setId(1);
		user.setFirstName("Cefer");
		user.setLastName("Huseynli");

		user1 = new User();
		user1.setId(2);
		user1.setFirstName("Huseyn");
		user1.setLastName("Qurbanli");

	}

	@Test
	public void userSaveTest() {
		

		when(this.userRepository.save(any(User.class))).thenReturn(user);

		User newUser = this.userService.addUser(user);

		assertThat(newUser).isNotEqualTo(null);
		assertThat(newUser.getFirstName()).isEqualTo("Cefer");
	}

	@Test
	public void getAllUsersTest() {

		List<User> list = new ArrayList<>();
		list.add(user1);
		list.add(user);

		when(this.userRepository.findAll()).thenReturn(list);

		List<User> listOfUsers = this.userService.getAllUsers();
		assertThat(listOfUsers.size()).isEqualTo(2);
	}

	@Test
	public void getUserByIdTest() {
		
		
		when(this.userRepository.findById(anyInt())).thenReturn(Optional.of(user));
		
		User existingUser=this.userService.getUserById(1);
		assertThat(existingUser).isNotEqualTo(null);
		assertThat(existingUser.getFirstName()).isEqualTo("Cefer");
	}

	@Test
	public void updateUserTest() {
	
		
		when(this.userRepository.findById(anyInt())).thenReturn(Optional.of(user));
		when(this.userRepository.save(any(User.class))).thenReturn(user);
		user.setFirstName("Babek");
		
		User updateUser=this.userService.updateUser(1, user);
		assertThat(updateUser).isNotEqualTo(null);
		assertThat(updateUser.getFirstName()).isEqualTo("Babek");
	
	}

	@Test
	public void deleteUserTest() {
		
		
		when(this.userRepository.findById(anyInt())).thenReturn(Optional.of(user));
		doNothing().when(this.userRepository).delete(any(User.class));
		
		this.userService.deleteUser(1);
		
		verify(this.userRepository, times(1)).delete(user);
	}

}
