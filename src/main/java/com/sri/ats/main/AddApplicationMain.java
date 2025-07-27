package com.sri.ats.main;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.sri.ats.bean.Application;
import com.sri.ats.bean.User;
import com.sri.ats.service.ATSService;
import com.sri.ats.service.Impl.ATSServiceImplModified;

public class AddApplicationMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		List<Application> apps = null;

		ATSService obj1 = new ATSServiceImplModified();

		Optional<List<Application>> applications = obj1.getApplicationsByUserId("cab0fd4f-7995-4ed0-9019-b446f274540b",
				1, 3);

		if (applications.isPresent()) {

			apps = applications.get();

		}

		for (Application app : apps) {
			System.out.println(app.getCompanyName());
		}

	}

}
