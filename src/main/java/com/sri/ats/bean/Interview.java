package com.sri.ats.bean;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "Interview_Table")
public class Interview {

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@Column(name = "Interview_Id", length = 36, nullable = false, updatable = false)
	private String id;

	@NotNull
	@Column(nullable = false)
	private LocalDate interviewDate;

	@NotNull
	@Column(nullable = false)
	private LocalTime interviewTime;

	@NotBlank
	@Column(nullable = false, columnDefinition = "text")
	private String location;

	@Column(columnDefinition = "text")
	private String remarks;

	@NotBlank
	@Column(nullable = false)
	private int roundNumber;

	@NotBlank
	@Size(max = 50)
	@Column(nullable = false, length = 50)
	private String mode;

	@ManyToOne
	@JoinColumn(name = "User_Id", nullable = false, updatable = false)
	private User user;

	@ManyToOne
	@JoinColumn(name = "App_Id", nullable = false, updatable = false)
	private Application application;

	@Override
	public String toString() {
		return "Interview [interviewDate=" + interviewDate + ", interviewTime=" + interviewTime + ", location="
				+ location + ", remarks=" + remarks + ", roundNumber=" + roundNumber + ", mode=" + mode + "]";
	}

	public String getId() {
		return id;
	}

	public LocalDate getInterviewDate() {
		return interviewDate;
	}

	public LocalTime getInterviewTime() {
		return interviewTime;
	}

	public String getLocation() {
		return location;
	}

	public String getRemarks() {
		return remarks;
	}

	public int getRoundNumber() {
		return roundNumber;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setInterviewDate(LocalDate interviewDate) {
		this.interviewDate = interviewDate;
	}

	public void setInterviewTime(LocalTime interviewTime) {
		this.interviewTime = interviewTime;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public void setRoundNumber(int roundNumber) {
		this.roundNumber = roundNumber;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

}
