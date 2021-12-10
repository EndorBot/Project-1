package com.revature.dto;

import java.util.Objects;

public class ChangeReimbursementDTO {

	private String reimbursementResolved; // since it will be taking in a type long for the unix epoch timestamp and turn it into a timestamp
	private String status;

	public ChangeReimbursementDTO() {
		super();
	}
	
	public ChangeReimbursementDTO(String status, String reimbursementResolved) {
		super();
		this.status = status;
		this.reimbursementResolved = reimbursementResolved;
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

	@Override
	public int hashCode() {
		return Objects.hash(reimbursementResolved, status);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ChangeReimbursementDTO other = (ChangeReimbursementDTO) obj;
		return Objects.equals(reimbursementResolved, other.reimbursementResolved)
				&& Objects.equals(status, other.status);
	}

	@Override
	public String toString() {
		return "ChangeReimbursementDTO [reimbursementResolved=" + reimbursementResolved + ", status=" + status + "]";
	}

}
