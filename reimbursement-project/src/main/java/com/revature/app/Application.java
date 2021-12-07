package com.revature.app;

import com.revature.controller.AuthenticationController;
import com.revature.controller.Controller;
import com.revature.controller.ExceptionController;
import com.revature.controller.ReimbursementController;

import io.javalin.Javalin;

public class Application {

public static void main(String[] args) {
		
		Javalin app = Javalin.create((config) -> {
			config.enableCorsForAllOrigins();
			
			//config.addStaticFiles("static", Location.CLASSPATH); Leave commented until front end is implemented
		});
		
		mapControllers(app, new AuthenticationController(), new ReimbursementController()); //
		
		ExceptionController mapper = new ExceptionController();
		mapper.mapExceptions(app);
		
		app.start(8080);
	}
	
	public static void mapControllers(Javalin app, Controller... controllers) {
		
		for (int i = 0; i < controllers.length; i++) {
			controllers[i].mapEndpoints(app);
		}

	}
	
}
