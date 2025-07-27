package com.sri.ats.bean;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "Application_Table")
public class Application {

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@Column(name = "App_Id", length = 36, updatable = false, nullable = false)
	private String id;

	@NotBlank
	@Size(max = 100)
	@Column(nullable = false, length = 100)
	private String companyName;

	@NotBlank
	@Size(max = 100)
	@Column(nullable = false, length = 100)
	private String jobTitle;

	@NotNull
	@Column(nullable = false)
	private LocalDate appliedDate;

	@NotBlank
	@Size(max = 100)
	@Column(nullable = false, length = 100)
	private String status;

	@Column(nullable = true, columnDefinition = "text")
	private String notes;

	@ManyToOne
	@JoinColumn(name = "User_Id", nullable = false, updatable = false)
	private User user;

	@OneToMany(mappedBy = "application", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Interview> interviews;

	@Override
	public String toString() {
		return "Application [ companyName=" + companyName + ", jobTitle=" + jobTitle + ", appliedDate=" + appliedDate
				+ ", status=" + status + ", notes=" + notes + "]";
	}

	public String getId() {
		return id;
	}

	public String getCompanyName() {
		return companyName;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public LocalDate getAppliedDate() {
		return appliedDate;
	}

	public String getStatus() {
		return status;
	}

	public String getNotes() {
		return notes;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public void setAppliedDate(LocalDate appliedDate) {
		this.appliedDate = appliedDate;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Interview> getInterviews() {
		return interviews;
	}

	public void setInterviews(List<Interview> interviews) {
		this.interviews = interviews;
	}

}
