package com.sri.ats.main;

import com.sri.ats.service.ATSService;
import com.sri.ats.service.Impl.ATSServiceImplModified;

public class EmailExistsMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		ATSService obj = new ATSServiceImplModified();

		boolean isEmailExists = obj.checkEmailExists("kiran@gmail.com");

		if (isEmailExists) {
			System.out.println("email already exists try another ");
		} else {
			System.out.println("email not exists");
		}

	}

}
