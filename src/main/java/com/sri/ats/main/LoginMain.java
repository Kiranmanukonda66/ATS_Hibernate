package com.sri.ats.main;

import com.sri.ats.service.ATSService;
import com.sri.ats.service.Impl.ATSServiceImplModified;

public class LoginMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		ATSService obj = new ATSServiceImplModified();

		boolean otp = obj.verifyOtp("405190", "cab0fd4f-7995-4ed0-9019-b446f274540b");

		System.out.println(otp);

	}

}
