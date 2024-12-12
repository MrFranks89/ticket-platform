package it.ticket.platform.model;

import jakarta.validation.constraints.NotNull;

public class TicketAssignmentRequest {

	@NotNull(message = "AdminId non può essere nullo")
	private Long adminId;

	@NotNull(message = "TicketId non può essere nullo")
	private Long ticketId;

	@NotNull(message = "OperatoreId non può essere nullo")
	private Long operatoreId;

	public Long getAdminId() {
		return adminId;
	}

	public void setAdminId(Long adminId) {
		this.adminId = adminId;
	}

	public Long getTicketId() {
		return ticketId;
	}

	public void setTicketId(Long ticketId) {
		this.ticketId = ticketId;
	}

	public Long getOperatoreId() {
		return operatoreId;
	}

	public void setOperatoreId(Long operatoreId) {
		this.operatoreId = operatoreId;
	}
}
