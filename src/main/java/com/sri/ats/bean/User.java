package com.sri.ats.bean;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "User_Table")
public class User {

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@Column(name = "User_Id", length = 36, updatable = false, nullable = false)
	private String id;

	@NotBlank
	@Size(max = 100)
	@Column(nullable = false, length = 100)
	private String firstName;

	@Size(max = 100)
	@Column(length = 100)
	private String middleName;

	@NotBlank
	@Size(max = 100)
	@Column(nullable = false, length = 100)
	private String lastName;

	@NotBlank
	@Size(max = 100)
	@Column(nullable = false, length = 100)
	private String email;

	@NotBlank
	@Size(max = 100)
	@Column(nullable = false, length = 100)
	private String username;

	@NotBlank
	@Size(max = 100)
	@Column(nullable = false, length = 100)
	private String password;

	@NotBlank
	@Size(max = 100)
	@Column(nullable = false, length = 100)
	private String mobile;

	@Column(name = "otp", length = 10)
	private String otp;

	@Column(name = "otp_generated_time")
	private java.sql.Timestamp otpGeneratedTime;

	@Column(name = "otp_verified", length = 3)
	private String otpVerified = "no";

	@Column(name = "email_verified", length = 3)
	private String emialVerified = "no";

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Application> applications;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Interview> interviews;

	@Override
	public String toString() {
		return "User [firstName=" + firstName + ", middleName=" + middleName + ", lastName=" + lastName + ", email="
				+ email + ", username=" + username + ", otp=" + otp + ", otpGeneratedTime=" + otpGeneratedTime
				+ ", otpVerified=" + otpVerified + ", emialVerified=" + emialVerified + "]";
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public List<Application> getApplications() {
		return applications;
	}

	public void setApplications(List<Application> applications) {
		this.applications = applications;
	}

	public List<Interview> getInterviews() {
		return interviews;
	}

	public void setInterviews(List<Interview> interviews) {
		this.interviews = interviews;
	}

	public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}

	public java.sql.Timestamp getOtpGeneratedTime() {
		return otpGeneratedTime;
	}

	public void setOtpGeneratedTime(java.sql.Timestamp otpGeneratedTime) {
		this.otpGeneratedTime = otpGeneratedTime;
	}

	public String getOtpVerified() {
		return otpVerified;
	}

	public void setOtpVerified(String otpVerified) {
		this.otpVerified = otpVerified;
	}

	public String getEmialVerified() {
		return emialVerified;
	}

	public void setEmialVerified(String emialVerified) {
		this.emialVerified = emialVerified;
	}

}
