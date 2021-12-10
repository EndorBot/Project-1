package com.revature.service;

import java.io.InputStream;
import java.security.InvalidParameterException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.revature.dao.ReimbursementDAO;
import com.revature.exception.ReimbursementAlreadyAwardedException;
import com.revature.exception.ReimbursementImageNotFoundException;
import com.revature.exception.ReimbursementNotFoundException;
import com.revature.exception.UnauthorizedException;
import com.revature.model.Reimbursement;
import com.revature.model.User;

public class ReimbursementService {

	private ReimbursementDAO reimbursementDao;

	public ReimbursementService() {
		this.reimbursementDao = new ReimbursementDAO();
	}

	// for Mockito
	public ReimbursementService(ReimbursementDAO reimbursementDao) {
		this.reimbursementDao = reimbursementDao;
	}

	// A employee can only view their own reimbursements, while a Finance Manager
	// can view all
	public List<Reimbursement> getReimbursement(User currentlyLoggedInUser) throws SQLException {
		List<Reimbursement> reimbursement = null;

		if (currentlyLoggedInUser.getUserRole().equals("Finance Manager")) {
			reimbursement = this.reimbursementDao.getAllReimbursement();
		} else if (currentlyLoggedInUser.getUserRole().equals("Employee")) {
			reimbursement = this.reimbursementDao.getAllReimbursementByEmployee(currentlyLoggedInUser.getId());
		}

		return reimbursement;
	}

	// If a reimbursement exists it should check if it already has a status and if it was resolved if it does then it throws ReimbursementAlreadyAwardedException 
	// or then the finance manger can approve or deny it and then set it a resolved date 
	public Reimbursement changeReimbursement(User currentlyLoggedInUser, String reimbursementId,
			String status)
			throws SQLException, ReimbursementAlreadyAwardedException, ReimbursementNotFoundException {
		try {
			int id = Integer.parseInt(reimbursementId);

			Reimbursement reimbursement = this.reimbursementDao.getReimbursementById(id);



			// 0
			if (reimbursement == null) {
				throw new ReimbursementNotFoundException(
						"Reimbursement with an id of " + reimbursementId + " was not found");
			}

			// 1
			if (reimbursement.getFinanceManagerId() == 0) { // if it's 0, it means that no Finance Manager has
															// reimbursed an amount yet
				this.reimbursementDao.changeReimbursement(id, status, currentlyLoggedInUser.getId());
			} else { // if it has already been given a reimbursement amount by a Finance Manager, and
						// we're trying to change the reimbursement amount here, that shouldn't be allowed
				throw new ReimbursementAlreadyAwardedException(
						"Reimbursement has already been given an amount, so the amount cannot be changed");
			}

			return this.reimbursementDao.getReimbursementById(id);
		} catch (NumberFormatException e) {// e is used in the ExceptionController
			throw new InvalidParameterException("Reimbursement id supplied must be an int");
		}
	}

	// Check if the mimetype is either JPEG, PNG, GIF, or PDF (WIP), if not throw a
	// InvalidParameterException
	public Reimbursement addReimbursement(User currentlyLoggedInUser, String mimeType, String reimbursementAmount,
			String type, String reimbursementDesc, InputStream content)
			throws SQLException {
		
		int rAmount = Integer.parseInt(reimbursementAmount);
		// if making submitted and resolved not null and handled in the backend, then
		// might want to change this.
		Set<String> allowedFileTypes = new HashSet<>();
		allowedFileTypes.add("image/jpeg");
		allowedFileTypes.add("image/png");
		allowedFileTypes.add("image/gif");
		// add pdf allowedFileTypes.add(" ");
		
		/*
		long unixTime = Instant.now().getEpochSecond();
		// convert the gotten unix timestamp into milliseconds
		long unixSeconds = unixTime;
		// format date
		Date date = new java.util.Date(unixSeconds * 1000L);
		// format timezone
		SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:MM:SS");
		sdf.setTimeZone(java.util.TimeZone.getTimeZone("GMT-5"));
		// put timestamp into reimbursementSubmitted
		reimbursementSubmitted = sdf.format(date);
		 */
		 

		if (!allowedFileTypes.contains(mimeType)) {
			throw new InvalidParameterException("When adding an recipt image, only PNG, JPEG, or GIF are allowed");
		}

		// Author, reimbursement description, reimbursement submitted, reimbursement
		// resolved, status, file content (bytes, 0s and 1s)
		int authorId = currentlyLoggedInUser.getId(); // whoever is logged in, will be the actual author of the submitted reimbursement

		Reimbursement addedReimbursement = this.reimbursementDao.addedReimbursement(rAmount, type, reimbursementDesc, content, authorId);

		return addedReimbursement;
	}

	public InputStream getImageFromReimbursementById(User currentlyLoggedInUser, String reimbursementId)
			throws SQLException, UnauthorizedException, ReimbursementImageNotFoundException {
		try {
			int id = Integer.parseInt(reimbursementId);

			// Check if they are an Employee
			if (currentlyLoggedInUser.getUserRole().equals("Employee")) {
				// Grab all of the reimbursements that belong to the Employee
				int userId = currentlyLoggedInUser.getId();
				List<Reimbursement> reimbursementThatBelongToEmployee = this.reimbursementDao
						.getAllReimbursementByEmployee(userId);

				Set<Integer> reimbursementIdsEncountered = new HashSet<>();
				for (Reimbursement r : reimbursementThatBelongToEmployee) {
					reimbursementIdsEncountered.add(r.getId());
				}

				// Check to see if the image they are trying to grab for a particular reimbursement
				// is actually their own reimbursement
				if (!reimbursementIdsEncountered.contains(id)) {
					throw new UnauthorizedException(
							"You cannot access the recipt images of reimbursements that do not belong to yourself");
				}
			}

			// Grab the image from the DAO, or if you're Finance Manager
			InputStream image = this.reimbursementDao.getImageFromReimbursementById(id);

			if (image == null) {
				throw new ReimbursementImageNotFoundException("Recipt image was not found for reimbursement id " + id);
			}

			return image;

		} catch (NumberFormatException e) { // e is used in the ExceptionController
			throw new InvalidParameterException("Reimbursement id supplied must be an int");
		}
	}

}
