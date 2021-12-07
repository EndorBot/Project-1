package com.revature.controller;

import io.javalin.Javalin;

public interface Controller {
	// the abstract controller interface
	public void mapEndpoints(Javalin app);
}
