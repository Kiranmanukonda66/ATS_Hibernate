package com.sri.ats.main;

import java.util.Optional;

import com.sri.ats.bean.User;
import com.sri.ats.service.ATSService;
import com.sri.ats.service.Impl.ATSServiceImplModified;

public class UpdateUserMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Optional<User> user = null;

		ATSService obj = new ATSServiceImplModified();

		user = obj.getUser("cab0fd4f-7995-4ed0-9019-b446f274540b");

		if (user.isPresent()) {
			System.out.println(user.get());
		} else {
			System.out.println("user not found");
		}

	}

}
