package com.sri.ats.service;

import java.util.List;
import java.util.Optional;

import com.sri.ats.bean.Application;
import com.sri.ats.bean.Interview;
import com.sri.ats.bean.User;

public interface ATSService {

	// user services

	boolean registration(User user);

	boolean login(String username, String password);

	Optional<User> getUser(String id);

	boolean updateProfile(User user);

	boolean checkUsernameExists(String username);

	boolean checkEmailExists(String email);

	// application services

	boolean addApplication(Application application);

	boolean editApplication(Application application);

	boolean deleteApplication(String appId);

	Optional<List<Application>> getApplicationsByUserId(String userId,int pageNumber,int pageSize);

	Optional<Application> getApplication(String appId);

	// interview services

	boolean addInterview(Interview interview);

	boolean editInterview(Interview interview);

	boolean deleteInterview(String id);

	Optional<Interview> getInterview(String id);

	Optional<List<Interview>> getInterviewByUserId(String userId,int pageNumber, int pageSize);

}
