package JUnit5.SpringBootApp2.controllerTest;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import com.fasterxml.jackson.databind.ObjectMapper;

import JUnit5.SpringBootApp2.model.User;
import JUnit5.SpringBootApp2.service.UserService;

@WebMvcTest
public class UserControllerTest {
	
	@MockBean
	private UserService userService;
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	private User user;
	private User user1;
	
	@BeforeEach
	public void init() {
		user=new User();
		user.setId(1);
		user.setFirstName("Cefer");
		user.setLastName("Huseynli");
		
		user1 = new User();
		user1.setId(2);
		user1.setFirstName("Huseyn");
		user1.setLastName("Qurbanli");
	}
	
	@Test
	public void addUserTest() throws  Exception {
		
		
		when(this.userService.addUser(any(User.class))).thenReturn(user);
		
		this.mockMvc.perform(post("/users/save")
				.contentType(MediaType.APPLICATION_JSON)
				.content(this.objectMapper.writeValueAsString(user)))
		.andExpect(status().isCreated())
		.andExpect(jsonPath("$.firstName", is(user.getFirstName())))
		.andExpect(jsonPath("$.lastName", is(user.getLastName())));
 } 
	
	@Test
	public void getAllUsersTest() throws Exception {
		
		
		
		
		List<User> users=new ArrayList<>();
		users.add(user);
		users.add(user1);
		
		when(this.userService.getAllUsers()).thenReturn(users);
		
		this.mockMvc.perform(get("/users/allUsers"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.size()", is(users.size())));
		}
	@Test
	public void getUserByIdTest() throws Exception {
		
		
		when(this.userService.getUserById(anyInt())).thenReturn(user);
		
		this.mockMvc.perform(get("/users/user/{id}",1))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.firstname", is(user.getFirstName())))
		.andExpect(jsonPath("$.lastName", is(user.getLastName())));
	}
	
	@Test
	public void updateUserTest() throws Exception {
		
		
		when(this.userService.updateUser(anyInt(), any(User.class))).thenReturn(user);
		
		this.mockMvc.perform(put("/users/update/{id}",1)
				.contentType(MediaType.APPLICATION_JSON)
				.content(this.objectMapper.writeValueAsBytes(user)))
		.andExpect(jsonPath("$.firstName", is(user.getFirstName())))
		.andExpect(jsonPath("$.lastName", is(user.getLastName())));
		
	}

		@Test
	public void deleteUserTest() throws Exception {
		
		doNothing().when(this.userService).deleteUser(anyInt());
		
		this.mockMvc.perform(delete("/users/delete/{id}",1))
		.andExpect(status().isNoContent());
	}
	

}
