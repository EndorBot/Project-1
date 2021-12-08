package com.revature.dao;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.revature.model.Reimbursement;
import com.revature.utility.JDBCUtility;

public class ReimbursementDAO {

	public List<Reimbursement> getAllReimbursement() throws SQLException {
		try (Connection con = JDBCUtility.getConnection()) {
			List<Reimbursement> reimbursements = new ArrayList<>();

			String sql = "SELECT reimb_id, reimb_amount, reimb_submitted, reimb_resolved, reimb_status, reimb_type, reimb_decription, reimb_author, reimb_resolver FROM ers_reimbursement";
			PreparedStatement pstmt = con.prepareStatement(sql);

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				int id = rs.getInt("reimb_id");
				int reimbursementAmount = rs.getInt("reimb_amount");
				String reimbursementSubmitted = rs.getString("reimb_submitted");
				String reimbursementResolved = rs.getString("reimb_resolved");
				String status = rs.getString("reimb_status");
				String reimbursementDesc = rs.getString("reimb_decription");
				int authorId = rs.getInt("reimb_author");
				int financeManagerId = rs.getInt("reimb_resolver");

				Reimbursement reimbursement = new Reimbursement(id, reimbursementAmount, reimbursementSubmitted,
						reimbursementResolved, status, reimbursementDesc, authorId, financeManagerId);

				reimbursements.add(reimbursement);
			}

			return reimbursements;
		}
	}

	public List<Reimbursement> getAllReimbursementByEmployee(int employeeId) throws SQLException {
		try (Connection con = JDBCUtility.getConnection()) {
			List<Reimbursement> reimbursements = new ArrayList<>();

			String sql = "SELECT reimb_id, reimb_amount, reimb_submitted, reimb_resolved, reimb_status, reimb_type, reimb_decription, reimb_author, reimb_resolver FROM ers_reimbursement WHERE reimb_author = ?";

			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, employeeId);

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				int id = rs.getInt("reimb_id");
				int reimbursementAmount = rs.getInt("reimb_amount");
				String reimbursementSubmitted = rs.getString("reimb_submitted");
				String reimbursementResolved = rs.getString("reimb_resolved");
				String status = rs.getString("reimb_status");
				String reimbursementDesc = rs.getString("reimb_decription");
				int authorId = rs.getInt("reimb_author");
				int financeManagerId = rs.getInt("reimb_resolver");

				Reimbursement reimbursement = new Reimbursement(id, reimbursementAmount, reimbursementSubmitted,
						reimbursementResolved, status, reimbursementDesc, authorId, financeManagerId);

				reimbursements.add(reimbursement);
			}

			return reimbursements;
		}
	}

	public Reimbursement getReimbursementById(int reimbursementId) throws SQLException {
		try (Connection con = JDBCUtility.getConnection()) {

			String sql = "SELECT reimb_id, reimb_amount, reimb_submitted, reimb_resolved, reimb_status, reimb_type, reimb_decription, reimb_author, reimb_resolver FROM ers_reimbursement WHERE reimb_id = ?";

			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, reimbursementId);

			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				int id = rs.getInt("reimb_id");
				int reimbursementAmount = rs.getInt("reimb_amount");
				String reimbursementSubmitted = rs.getString("reimb_submitted");
				String reimbursementResolved = rs.getString("reimb_resolved");
				String status = rs.getString("reimb_status");
				String reimbursementDesc = rs.getString("reimb_decription");
				int authorId = rs.getInt("reimb_author");
				int financeManagerId = rs.getInt("reimb_resolver");

				return new Reimbursement(id, reimbursementAmount, reimbursementSubmitted, reimbursementResolved, status,
						reimbursementDesc, authorId, financeManagerId);
			} else {
				return null;
			}

		}
	}

	public void changeReimbursement(int id, int reimbursementAmount, int financeManagerId) throws SQLException {
		try (Connection con = JDBCUtility.getConnection()) {
			String sql = "UPDATE ers_reimbursement " + "SET " + "reimb_amount = ?, " + "reimb_resolver = ? "
					+ "WHERE reimb_id = ?;";
			PreparedStatement pstmt = con.prepareStatement(sql);

			pstmt.setInt(1, reimbursementAmount);
			pstmt.setInt(2, financeManagerId);
			pstmt.setInt(3, id);

			int updatedCount = pstmt.executeUpdate();

			if (updatedCount != 1) {
				throw new SQLException("Something bad occurred when trying to update the reimbursement amount");
			}
		}

	}

	public Reimbursement addedReimbursement(String reimbursementSubmitted, String reimbursementResolved, String status,
			String reimbursementDesc, int authorId, InputStream image) throws SQLException {
		try (Connection con = JDBCUtility.getConnection()) {
			con.setAutoCommit(false); // Turn off autocommit because larger objects cannot be used with auto commit

			String sql = "INSERT INTO ers_reimbursement (reimb_submitted, reimb_resolved, reimb_status, reimb_type, reimb_decription, reimb_recipt, author_id)"
					+ " VALUES (?, ?, ?, ?, ?, ?, ?);";
			
			PreparedStatement pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, reimbursementSubmitted);
			pstmt.setString(2, reimbursementResolved);
			pstmt.setString(3, status);
			pstmt.setString(4, reimbursementDesc);
			pstmt.setBinaryStream(6, image); // for data type BYTEA in The SQL Database
			pstmt.setInt(7, authorId);

			int numberOfInsertedRecords = pstmt.executeUpdate();

			if (numberOfInsertedRecords != 1) {
				throw new SQLException("Issue occurred when adding the reimbursement");
			}

			ResultSet rs = pstmt.getGeneratedKeys();

			rs.next();
			int generatedId = rs.getInt(1);

			con.commit(); // COMMIT
			
			// Should follow the same pattern as in constructor of the Reimbursement model
			return new Reimbursement(generatedId, 0, reimbursementSubmitted, reimbursementResolved, status, reimbursementDesc, 0, authorId);
		}
	}

	public InputStream getImageFromReimbursementById(int id) throws SQLException{
		try (Connection con = JDBCUtility.getConnection()) {
			String sql = "SELECT reimb_recipt FROM ers_reimbursement WHERE reimb_id = ?";
			
			PreparedStatement pstmt = con.prepareStatement(sql);

			pstmt.setInt(1, id);

			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				InputStream image = rs.getBinaryStream("reimb_recipt");

				return image;
			}

			return null;
		}
	}

}
