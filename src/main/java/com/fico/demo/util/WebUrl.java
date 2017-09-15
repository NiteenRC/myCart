package com.fico.demo.util;

public interface WebUrl {
	String USER = "user";
	String USER_FORGET_PASSWORD = "user/forgetPassword/";
	String USER_FORGET_PASSWORD_EMAILID = "user/forgetPassword/{emailId}";
	String USER_BY_EMAILID_PASSWORD = "user/byEmailAndPassword";

	String CATEGORY_BY_NAME = "/category/byName/";
	String CATEGORY = "/category";

	String DOCTOR = "/doctor";
	String DOCTORS = "/doctors";
	String DOCTOR_AND_CATEGORYID = "/doctor/{categoryID}";
	String DOCTOR_BY_CATEGORYID = "/doctor/by/{categoryID}";
	String DOCTOR_BY_DOCTORID = "/doctor/{doctorID}";
}
