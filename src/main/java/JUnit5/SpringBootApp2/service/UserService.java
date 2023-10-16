package JUnit5.SpringBootApp2.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import JUnit5.SpringBootApp2.model.User;
import JUnit5.SpringBootApp2.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	public User addUser(User user) {
		return this.userRepository.save(user);
	}
	
	public List<User> getAllUsers(){
		return this.userRepository.findAll();
	}
	
	public User getUserById(int id) {
		return this.userRepository.findById(id).orElseThrow(
				()->new RuntimeException("user not found"));
	}
	
	public User updateUser(int id,User user) {
		Optional<User> updateUser=this.userRepository.findById(id);
		if(updateUser.isPresent()) {
			User user1=updateUser.get();
			user1.setFirstName(user.getFirstName());
			user1.setLastName(user.getLastName());
			
			return this.userRepository.save(user1);
		}
	     return null;
	}
	
	public void deleteUser(int  id) {
		User existingUser = userRepository.findById(id).get();
		userRepository.delete(existingUser);
	}

}
