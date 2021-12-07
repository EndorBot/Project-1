package com.revature.model;

import java.util.Objects;

public class Reimbursement {
	
	private int id;
	private int reimbursementAmount;
	private String reimbursementSubmitted;
	private String reimbursementResolved;
	private String status;
	private String reimbursementDesc;
	private int financeManagerId;
	private int authorId;

	public Reimbursement() {
		super();
	}

	public Reimbursement(int id, int reimbursementAmount, String reimbursementSubmitted, String reimbursementResolved, String status,  String reimbursementDesc, int financeManagerId, int authorId) {
		super();
		this.id = id;
		this.reimbursementAmount = reimbursementAmount;
		this.reimbursementSubmitted = reimbursementSubmitted;
		this.reimbursementResolved = reimbursementResolved;
		this.status = status;
		this.reimbursementDesc = reimbursementDesc;
		this.financeManagerId = financeManagerId;
		this.authorId = authorId;
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
				reimbursementResolved, reimbursementSubmitted, status);
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
				&& Objects.equals(status, other.status);
	}

	@Override
	public String toString() {
		return "Reimbursement [id=" + id + ", reimbursementAmount=" + reimbursementAmount + ", reimbursementSubmitted="
				+ reimbursementSubmitted + ", reimbursementResolved=" + reimbursementResolved + ", status=" + status
				+ ", reimbursementDesc=" + reimbursementDesc + ", financeManagerId=" + financeManagerId + ", authorId="
				+ authorId + "]";
	}
	
	
}

