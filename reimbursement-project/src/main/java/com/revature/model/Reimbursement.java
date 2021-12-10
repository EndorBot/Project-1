package com.revature.model;

import java.sql.Timestamp;
import java.util.Objects;

public class Reimbursement {
	// contains all the same credentials as the database
	private int id;
	private int reimbursementAmount;
	private String reimbursementSubmitted; // since it will be taking in a type long for the unix epoch timestamp and turn it into a timestamp
	private String reimbursementResolved; // since it will be taking in a type long for the unix epoch timestamp and turn it into a timestamp
	private String status;
	private String type;
	private String reimbursementDesc;
	private int financeManagerId;
	private int authorId;

	public Reimbursement() {
		super();
	}

	public Reimbursement(int id, int reimbursementAmount, String reimbursementSubmitted, String reimbursementResolved,
			String status, String type, String reimbursementDesc, int authorId, int financeManagerId) {
		super();
		this.id = id;
		this.reimbursementAmount = reimbursementAmount;
		this.reimbursementSubmitted = reimbursementSubmitted;
		this.reimbursementResolved = reimbursementResolved;
		this.status = status; // not null, needs something
		this.type = type;
		this.reimbursementDesc = reimbursementDesc; // not null, needs something
		this.authorId = authorId;
		this.financeManagerId = financeManagerId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getReimbursementAmount() {
		return reimbursementAmount;
	}

	public void setReimbursementAmount(int reimbursementAmount) {
		this.reimbursementAmount = reimbursementAmount;
	}

	public String getReimbursementSubmitted() {
		return reimbursementSubmitted;
	}

	public void setReimbursementSubmitted(String reimbursementSubmitted) {
		this.reimbursementSubmitted = reimbursementSubmitted;
	}

	public String getReimbursementResolved() {
		return reimbursementResolved;
	}

	public void setReimbursementResolved(String reimbursementResolved) {
		this.reimbursementResolved = reimbursementResolved;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getReimbursementDesc() {
		return reimbursementDesc;
	}

	public void setReimbursementDesc(String reimbursementDesc) {
		this.reimbursementDesc = reimbursementDesc;
	}

	public int getFinanceManagerId() {
		return financeManagerId;
	}

	public void setFinanceManagerId(int financeManagerId) {
		this.financeManagerId = financeManagerId;
	}

	public int getAuthorId() {
		return authorId;
	}

	public void setAuthorId(int authorId) {
		this.authorId = authorId;
	}

	@Override
	public int hashCode() {
		return Objects.hash(authorId, financeManagerId, id, reimbursementAmount, reimbursementDesc,
				reimbursementResolved, reimbursementSubmitted, status, type);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Reimbursement other = (Reimbursement) obj;
		return authorId == other.authorId && financeManagerId == other.financeManagerId && id == other.id
				&& reimbursementAmount == other.reimbursementAmount
				&& Objects.equals(reimbursementDesc, other.reimbursementDesc)
				&& Objects.equals(reimbursementResolved, other.reimbursementResolved)
				&& Objects.equals(reimbursementSubmitted, other.reimbursementSubmitted)
				&& Objects.equals(status, other.status) && Objects.equals(type, other.type);
	}

	@Override
	public String toString() {
		return "Reimbursement [id=" + id + ", reimbursementAmount=" + reimbursementAmount + ", reimbursementSubmitted="
				+ reimbursementSubmitted + ", reimbursementResolved=" + reimbursementResolved + ", status=" + status
				+ ", type=" + type + ", reimbursementDesc=" + reimbursementDesc + ", financeManagerId="
				+ financeManagerId + ", authorId=" + authorId + "]";
	}

}
