package com.sri.ats.main;

import java.time.LocalDate;
import java.util.Optional;

import com.sri.ats.bean.Application;
import com.sri.ats.bean.User;
import com.sri.ats.service.ATSService;
import com.sri.ats.service.Impl.ATSServiceImplModified;

public class AddInterview {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Optional<User> user = null;

		User user1 = null;

		ATSService obj = new ATSServiceImplModified();

		user = obj.getUser("cab0fd4f-7995-4ed0-9019-b446f274540b");

		if (user.isPresent()) {
			user1 = user.get();
		}

		Application application = new Application();

		application.setCompanyName("google");
		application.setAppliedDate(LocalDate.parse("2025-07-27"));
		application.setJobTitle("SDE-I");
		application.setStatus("just applied");
		application.setUser(user1);
		ATSService obj1 = new ATSServiceImplModified();

		boolean isRegistered = obj1.addApplication(application);

		if (isRegistered) {
			System.out.println("application registered sucessfully");
		} else {
			System.out.println("application registration is failed");
		}

	}

}
