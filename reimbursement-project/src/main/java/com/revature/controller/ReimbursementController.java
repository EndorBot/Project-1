package com.revature.controller;

import java.io.InputStream;
import java.util.List;

import org.apache.tika.Tika;

import com.revature.dto.ChangeReimbursementDTO;
import com.revature.dto.MessageDTO;
import com.revature.model.Reimbursement;
import com.revature.model.User;
import com.revature.service.AuthorizationService;
import com.revature.service.ReimbursementService;

import io.javalin.Javalin;
import io.javalin.http.Handler;
import io.javalin.http.UploadedFile;

public class ReimbursementController implements Controller {

	private AuthorizationService authService;
	private ReimbursementService reimbursementService;

	public ReimbursementController() {
		this.authService = new AuthorizationService();
		this.reimbursementService = new ReimbursementService();
	}

	private Handler getReimbursement = (ctx) -> {
		// guard this endpoint
		// roles permitted: Finance Manager, employee
		User currentlyLoggedInUser = (User) ctx.req.getSession().getAttribute("currentuser");
		this.authService.authorizeEmployeeAndFinanceManager(currentlyLoggedInUser);

		// If the above this.authService.authorizeEmployeeAndFinanceManager(...) method
		// did not throw an exception, that means
		// our program will continue to proceed to the below functionality
		List<Reimbursement> reimbursements = this.reimbursementService.getReimbursement(currentlyLoggedInUser);

		ctx.json(reimbursements);
	};

	// only Finance Manager (FM) can change reimbursement, it will have to be
	// approved or denied
	private Handler changeReimbursement = (ctx) -> {
		User currentlyLoggedInUser = (User) ctx.req.getSession().getAttribute("currentuser");
		this.authService.authorizeFinanceManager(currentlyLoggedInUser);

		String reimbursementId = ctx.pathParam("reimb_id"); // remember that "reimb_id" has to match what is in
															// "/reimbursements/{reimb_id}/amount"
		ChangeReimbursementDTO dto = ctx.bodyAsClass(ChangeReimbursementDTO.class); 
		// Taking the request body ->  putting the data into a new object

		Reimbursement changedReimbursement = this.reimbursementService.changeReimbursement(currentlyLoggedInUser,
				reimbursementId, dto.getStatus());
		ctx.json(changedReimbursement);
	};

	private Handler addReimbursement = (ctx) -> {
		// Protect endpoint
		User currentlyLoggedInUser = (User) ctx.req.getSession().getAttribute("currentuser");
		this.authService.authorizeEmployee(currentlyLoggedInUser);

		// might need other ones like submitted, resolved, status, type because those are NOT NULL
		String reimbursementAmount = ctx.formParam("reimb_amount");
		//String reimbursementSubmitted = ctx.formParam("reimb_submitted"); // if doing in the backend, Not needed
		//String reimbursementResolved = ctx.formParam("reimb_resolved"); // if doing in the backend, change to not null
		String type = ctx.formParam("reimb_type"); // remember can be Lodging, Travel, Food, or Other
		String reimbursementDesc = ctx.formParam("reimb_decription");

		// Used to utilizing DTO to use getStatus and getReimbursementResolved, now will do similar, but with Reimbursement model
		// ChangeReimbursementDTO dto = ctx.bodyAsClass(ChangeReimbursementDTO.class);
		/*
		 * Extracting file from HTTP Request
		 */
		UploadedFile file = ctx.uploadedFile("reimb_recipt");

		if (file == null) {
			ctx.status(400);
			ctx.json(new MessageDTO("Must have an image of the recipt to upload"));
			return; // to not continue on with the rest of the code, ending immediately
		}

		InputStream content = file.getContent(); // This is the most important. It is the actual content of the file

		Tika tika = new Tika();

		// We want to disallow users from uploading files that are not jpeg, gif, or png
		// So, in the controller layer, figure out the MIME type of the file
		// jpeg = image/jpeg
		// gif = image/gif
		// png = image/png
		String mimeType = tika.detect(content);

		// Service layer invocation
		// doesn't have to follow constructor of Reimbursement
		Reimbursement addedReimbursement = this.reimbursementService.addReimbursement(currentlyLoggedInUser, mimeType, reimbursementAmount,
				type, reimbursementDesc, content);
		ctx.json(addedReimbursement);
		ctx.status(201); // Content created
	};

	private Handler getImageFromReimbursementById = (ctx) -> {
		// protect endpoint
		User currentlyLoggedInUser = (User) ctx.req.getSession().getAttribute("currentuser");
		this.authService.authorizeEmployeeAndFinanceManager(currentlyLoggedInUser);

		String reimbursementId = ctx.pathParam("reimb_id");

		InputStream image = this.reimbursementService.getImageFromReimbursementById(currentlyLoggedInUser,
				reimbursementId);

		Tika tika = new Tika();
		String mimeType = tika.detect(image);

		ctx.contentType(mimeType); // specifying to the client what the type of the content actually is
		ctx.result(image); // Sending the image back to the client
	};

	@Override
	public void mapEndpoints(Javalin app) {
		app.get("/reimbursements", getReimbursement);
		app.patch("/reimbursements/{reimb_id}/amount", changeReimbursement);
		app.post("/reimbursements", addReimbursement);
		app.get("/reimbursement/{reimb_id}/image", getImageFromReimbursementById);
	}
}
