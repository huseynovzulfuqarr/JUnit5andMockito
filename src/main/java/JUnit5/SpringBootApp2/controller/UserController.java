package JUnit5.SpringBootApp2.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import JUnit5.SpringBootApp2.model.User;
import JUnit5.SpringBootApp2.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/allUsers")
	@ResponseStatus(value = HttpStatus.OK)
	public List<User> getAllUsers(){
		return this.userService.getAllUsers();
	}
	
    @GetMapping("/user/{id}")
    @ResponseStatus(value = HttpStatus.OK)
	public User getUserById(@PathVariable("id") int id) {
		return this.userService.getUserById(id);
	}
    
    @PostMapping("/save")
    @ResponseStatus(value = HttpStatus.CREATED)
    public User saveUser(@RequestBody User user) {
    	return this.userService.addUser(user);
    }
    
    @PutMapping("/update/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public User updateUser(@PathVariable("id") int id,@RequestBody User user) {
    	return this.userService.updateUser(id, user);
    }
    
    @DeleteMapping("/delete/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable int id) {
    	this.userService.deleteUser(id);
    }

}
