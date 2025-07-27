package com.sri.ats.main;

import com.sri.ats.service.ATSService;
import com.sri.ats.service.Impl.ATSServiceImplModified;

public class UserNameExists {

	public static void main(String[] args) {

		ATSService obj = new ATSServiceImplModified();

		boolean isnameExists = obj.checkUsernameExists("kiran");

		if (isnameExists) {
			System.out.println("username already exists try another ");
		} else {
			System.out.println("username not exists");
		}

	}

}
