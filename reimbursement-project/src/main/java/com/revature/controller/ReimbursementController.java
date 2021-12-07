package com.revature.controller;

import java.util.List;

import com.revature.dto.ChangeReimbursementDTO;
import com.revature.model.Reimbursement;
import com.revature.model.User;
import com.revature.service.AuthorizationService;
import com.revature.service.ReimbursementService;

import io.javalin.Javalin;
import io.javalin.http.Handler;

public class ReimbursementController {

	private AuthorizationService authService;
	private ReimbursementService reimbursementService;

	public ReimbursementController() {
		this.authService = new AuthorizationService();
		this.reimbursementService = new ReimbursementService();
	}

	private Handler getReimbursement = (ctx) -> {
		// guard this endpoint
		// roles permitted: trainer, associate
		User currentlyLoggedInUser = (User) ctx.req.getSession().getAttribute("currentuser");
		this.authService.authorizeEmployeeAndFinanceManager(currentlyLoggedInUser);

		// If the above this.authService.authorizeAssociateAndTrainer(...) method did
		// not throw an exception, that means
		// our program will continue to proceed to the below functionality
		List<Reimbursement> reimbursements = this.reimbursementService.getReimbursement(currentlyLoggedInUser);

		ctx.json(reimbursements);
	};
	
	// only Finance Manager (FM) can change reimbursement
	
	private Handler changeReimbursement = (ctx) -> {
		User currentlyLoggedInUser = (User) ctx.req.getSession().getAttribute("currentuser");
		this.authService.authorizeFinanceManager(currentlyLoggedInUser);
		
		String reimbursementId = ctx.pathParam("reimb_id"); // remember that "reimb_id" has to match what is in "/assignments/{reimb_id}/grade"
		ChangeReimbursementDTO dto = ctx.bodyAsClass(ChangeReimbursementDTO.class); // Taking the request body -> putting the data into a new object
		
		Reimbursement changedReimbursement= this.reimbursementService.changeGrade(currentlyLoggedInUser, reimbursementId, dto.getReimbursement());
		ctx.json(changedReimbursement);
	};

	@Override
	public void mapEndpoints(Javalin app) {
		app.get("/assignments", getReimbursement);
		app.patch("/assignments/{reimb_id}/grade", changeReimbursement);
		app.post("/assignments", addReimbursement);
		app.get("/assignments/{reimb_id}/image", getImageFromReimbursementById);
	}
}
