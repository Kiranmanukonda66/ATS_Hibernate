package com.sri.ats.util;

import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import org.apache.logging.log4j.LogManager;

public class HibernateUtil {

	private static final Logger logger = LogManager.getLogger(HibernateUtil.class);

	private static SessionFactory sessionFactory;

	static {

		try {

			if (sessionFactory == null) {
				sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
			}

		} catch (Exception e) {

			logger.error("HibernateUtil::getSessionFactory::{}", e.toString());

		}

	}

	public static SessionFactory getSessionFactory() {
		
		return sessionFactory;
	}

	public static void closeSessionFactory() {

		if (sessionFactory != null) {
			sessionFactory.close();
		}

	}

}
