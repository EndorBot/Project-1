package com.revature.service;

import com.revature.exception.UnauthorizedException;
import com.revature.model.User;

// Authorization checks the current user for its permissions to enable them to do certain things.
public class AuthorizationService {

	public void authorizeEmployeeAndFinanceManager(User user) throws UnauthorizedException {
		// If the user is not either a employee role or Finance manager role
		if (user == null || !(user.getUserRole().equals("Employee") || user.getUserRole().equals("Finance Manager"))) {
			throw new UnauthorizedException(
					"You must be logged in and have a role of Employee or Finance Manager for this resource");
		}
	}

	public void authorizeFinanceManager(User user) throws UnauthorizedException {
		if (user == null || !user.getUserRole().equals("Finance Manager")) {
			throw new UnauthorizedException("You must be logged in as Finance Manager for this resource");
		}
	}

	public void authorizeEmployee(User user) throws UnauthorizedException {
		if (user == null || !user.getUserRole().equals("Employee")) {
			throw new UnauthorizedException("You must be logged in as Employee for this resource");
		}

	}

}
