package com.revature.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.revature.exception.UnauthorizedException;
import com.revature.model.User;

public class AuthorizationServiceTest {

private AuthorizationService authService;
	
	@BeforeEach
	public void setup() {
		this.authService = new AuthorizationService();
	}
	
	/*
	 * authorizeFinanceManager
	 */
	@Test
	public void authorizeFinanceManager_negativeTest_userIsEmployeeButRequiresFinanceManagerPermissions() throws UnauthorizedException {
		User user = new User(1, "Rdant", "pass123", "Billy", "Bob", "rdant@gmail.com", "Employee"); 
		// id, username, pass, first, last, email, role
		
		Assertions.assertThrows(UnauthorizedException.class ,() -> {
			this.authService.authorizeFinanceManager(user);
		});
		
	}
	
	@Test
	public void authorizeFinanceManager_negativeTest_userIsNull() {		
		Assertions.assertThrows(UnauthorizedException.class, () -> {
			this.authService.authorizeFinanceManager(null);
		});
	}
	
	@Test
	public void authorizeFinanceManager_positiveTest_userIsAFinanceManager() throws UnauthorizedException {
		User user = new User(1, "Rdant", "pass123", "Billy", "Bob", "rdant@gmail.com", "Finance Manager");
		
		this.authService.authorizeFinanceManager(user);
	}
	
	/*
	 * authorizeEmployee
	 */
	@Test
	public void authorizeEmployee_negativeTest_userIsFinanceManagerButRequiresEmployeeRole() {
		User user = new User(1, "Rdant", "pass123", "Billy", "Bob", "rdant@gmail.com", "Finance Manager");
		
		Assertions.assertThrows(UnauthorizedException.class, () -> {
			this.authService.authorizeEmployee(user);
		});
		
	}

	@Test
	public void authorizeEmployee_negativeTest_userIsNull() {
		Assertions.assertThrows(UnauthorizedException.class, () -> {
			this.authService.authorizeEmployee(null);
		});
	}
	
	@Test
	public void authorizeEmployee_positiveTest_userIsAnEmployee() throws UnauthorizedException {
		User user = new User(1, "Rdant", "pass123", "Billy", "Bob", "rdant@gmail.com", "Employee");
		
		this.authService.authorizeEmployee(user);
	}
	
	/*
	 * authorizeEmployeeAndFinanceManager
	 */
	@Test
	public void authorizeEmployeeAndFinanceManager_negativeTest_userIsNotEmployeeOrFinanceManager() {
		User user = new User(1, "Rdant", "pass123", "Billy", "Bob", "rdant@gmail.com", "associate"); // the associate is for testing purposes
		
		Assertions.assertThrows(UnauthorizedException.class, () -> {
			this.authService.authorizeEmployeeAndFinanceManager(user);
		});
	}
	
	@Test
	public void authorizeEmployeeAndFinanceManager_negativeTest_userIsNull() {
		Assertions.assertThrows(UnauthorizedException.class, () -> {
			this.authService.authorizeEmployeeAndFinanceManager(null);
		});
	}
	
	@Test
	public void authorizeEmployeeAndFinanceManager_positiveTest_userIsEmployee() throws UnauthorizedException {
		User user = new User(1, "Rdant", "pass123", "Billy", "Bob", "rdant@gmail.com", "Employee");
		
		this.authService.authorizeEmployeeAndFinanceManager(user);
	}
	
	@Test
	public void authorizeEmployeeAndFinanceManager_positiveTest_userIsFinanceManager() throws UnauthorizedException {
		User user = new User(1, "Rdant", "pass123", "Billy", "Bob", "rdant@gmail.com", "Finance Manager");
		
		this.authService.authorizeEmployeeAndFinanceManager(user);
	}
	
}
