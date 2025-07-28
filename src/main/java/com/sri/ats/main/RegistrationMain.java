package com.sri.ats.main;

import com.sri.ats.bean.User;
import com.sri.ats.service.ATSService;
import com.sri.ats.service.Impl.ATSServiceImplModified;

public class RegistrationMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		User user = new User();

		user.setFirstName("vinay");
		user.setLastName("manukonda");
		user.setEmail("vinay@gmail.com");
		user.setUsername("vinay");
		user.setPassword("vinay12");
		user.setMobile("6302131671");

		ATSService obj = new ATSServiceImplModified();

		boolean isRegistered = obj.registration(user);

		if (isRegistered) {
			System.out.println("user registered sucessfully");
		} else {
			System.out.println("usre registration is failed");
		}

	}

}
