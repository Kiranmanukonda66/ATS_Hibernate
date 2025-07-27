package com.sri.ats.main;

import com.sri.ats.service.ATSService;
import com.sri.ats.service.Impl.ATSServiceImpl;

public class LoginMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		ATSService obj = new ATSServiceImpl();

		boolean islogin = obj.login("kiran", "kiran12");

		if (islogin) {
			System.out.println("user logged in sucessfully");
		} else {
			System.out.println("usre login is failed");
		}

	}

}
