package com.revature.dto;

import java.util.Objects;

public class ChangeReimbursementDTO {

	private int reimbursementAmount;
	
	public ChangeReimbursementDTO() {
		super();
	}
	
	public ChangeReimbursementDTO(int reimbursementAmount) {
		super();
	}

	public int getReimbursementAmount() {
		return reimbursementAmount;
	}

	public void setReimbursementAmount(int reimbursementAmount) {
		this.reimbursementAmount = reimbursementAmount;
	}

	@Override
	public int hashCode() {
		return Objects.hash(reimbursementAmount);
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
		return reimbursementAmount == other.reimbursementAmount;
	}

	@Override
	public String toString() {
		return "ChangeReimbursementDTO [reimbursementAmount=" + reimbursementAmount + "]";
	}
	
	
}
