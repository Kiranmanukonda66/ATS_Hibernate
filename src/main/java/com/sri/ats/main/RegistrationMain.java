package com.sri.ats.main;

import com.sri.ats.bean.User;
import com.sri.ats.service.ATSService;
import com.sri.ats.service.Impl.ATSServiceImpl;

public class RegistrationMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		User user = new User();

		user.setFirstName("kiran");
		user.setLastName("manukonda");
		user.setEmail("kiranmanukonda@gmail.com");
		user.setUsername("kiran");
		user.setPassword("kiran12");
		user.setMobile("7893164676");

		ATSService obj = new ATSServiceImpl();

		boolean isRegistered = obj.registration(user);

		if (isRegistered) {
			System.out.println("user registered sucessfully");
		} else {
			System.out.println("usre registration is failed");
		}

	}

}
