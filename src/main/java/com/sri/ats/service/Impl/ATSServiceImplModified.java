package com.sri.ats.service.Impl;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.sri.ats.bean.Application;
import com.sri.ats.bean.Interview;
import com.sri.ats.bean.User;
import com.sri.ats.service.ATSService;
import com.sri.ats.util.HibernateUtil;

public class ATSServiceImplModified implements ATSService {

	private static final Logger logger = LogManager.getLogger(ATSServiceImplModified.class);
	private static final Logger sensitiveLogger = LogManager.getLogger("sensitive");

	// 180000ms means 3 mins
	private static final long OPT_VALIDITY_DURATION = 3 * 60 * 1000;

	@Override
	public boolean registration(User user) {

		sensitiveLogger.debug("ATSSserviceImpl::registration::Input::password={}", user.getPassword());

		return saveOrUpdate(user);
	}

	@Override
	public boolean login(String username, String password) {

		boolean isUserExist = false;

		try (Session session = HibernateUtil.getSessionFactory().openSession()) {

			logger.info("ATSServiceImpl::login::Input::username={}", username);
			sensitiveLogger.debug("ATSServiceImpl::login::Input::password={}", password);

			Criteria criteria = session.createCriteria(User.class);

			criteria.add(Restrictions.eq("username", username));
			criteria.add(Restrictions.eq("password", password));

			Object user = criteria.uniqueResult();

			isUserExist = (user != null);

			logger.info("ATSServiceImpl::login::output::{}", user);

		} catch (Exception e) {

			logger.error("ATSServiceImpl::login::Exception::{}", e);

		}

		logger.info("ATSServiceImpl::login::output::isUserExist={}", isUserExist);

		return isUserExist;

	}

	@Override
	public Optional<User> getUser(String id) {

		return getObject(User.class, id);
	}

	@Override
	public boolean updateProfile(User user) {

		return saveOrUpdate(user);
	}

	@Override
	public boolean checkUsernameExists(String username) {

		return usernameAndEmailExist(username, null);
	}

	@Override
	public boolean checkEmailExists(String email) {

		return usernameAndEmailExist(null, email);

	}

	@Override
	public String generateOtp(String userId) {

		String otp = null;

		try {

			Optional<User> user = getObject(User.class, userId);

			if (user.isPresent()) {

				// gives number between 100000 to 999999
				otp = String.valueOf(new Random().nextInt(899999) + 100000);

				user.get().setOtp(otp);

				// system.currentTimeMillis()->will return the current time in milliseconds
				user.get().setOtpGeneratedTime(new Timestamp(System.currentTimeMillis()));

				saveOrUpdate(user.get());

			}

		} catch (Exception e) {

			logger.error("ATSServiceImpl::generateOtp::Exception:{}", e.toString(), e);

		}

		return otp;
	}

	@Override
	public boolean verifyOtp(String otp, String userId) {

		boolean isVerified = false;

		Optional<User> user = null;

		try {

			logger.info("ATSServiceImpl::verifyOtp::input::otp={},userid={}", otp, userId);

			user = getObject(User.class, userId);

			if (user.isPresent() && user.get().getOtp().equals(otp)) {

				long timeDiff = System.currentTimeMillis() - user.get().getOtpGeneratedTime().getTime();

				if (timeDiff <= OPT_VALIDITY_DURATION) {

					user.get().setOtpVerified("yes");

					user.get().setOtp(null);
					user.get().setOtpGeneratedTime(null);

					saveOrUpdate(user.get());

					isVerified = true;

				}

			}

			logger.info("ATSServiceImpl::verifyOtp::output::otpVerified={}", isVerified);

		} catch (Exception e) {
			logger.info("ATSServiceImpl::verifyOtp::Exception::{}", e.toString(), e);
		}

		return isVerified;
	}

	@Override
	public boolean addApplication(Application application) {

		return saveOrUpdate(application);
	}

	@Override
	public boolean editApplication(Application application) {

		return saveOrUpdate(application);
	}

	@Override
	public boolean deleteApplication(String appId) {

		return deleteRecord(Application.class, appId);

	}

	@Override
	public Optional<List<Application>> getApplicationsByUserId(String userId, int pageNumber, int pageSize) {

		return getMultipleObjects(Application.class, userId, "user", pageNumber, pageSize);

	}

	@Override
	public Optional<Application> getApplication(String appId) {

		return getObject(Application.class, appId);
	}

	@Override
	public boolean addInterview(Interview interview) {

		return saveOrUpdate(interview);
	}

	@Override
	public boolean editInterview(Interview interview) {

		return saveOrUpdate(interview);
	}

	@Override
	public boolean deleteInterview(String interviewId) {

		return deleteRecord(Interview.class, interviewId);

	}

	@Override
	public Optional<Interview> getInterview(String interviewId) {

		return getObject(Interview.class, interviewId);
	}

	@Override
	public Optional<List<Interview>> getInterviewByUserId(String userId, int pageNumber, int pageSize) {

		return getMultipleObjects(Interview.class, userId, "user", pageNumber, pageSize);
	}

	/**
	 * Utility method to saves and updates the record to the database.
	 *
	 * @param Object
	 * @return true if it is saved or updated the record , false if not done
	 */

	/*
	 * Thread.currentThread().getStackTrace() returns an array where:
	 * 
	 * [0] → getStackTrace method
	 * 
	 * [1] → current method (e.g., saveOrUpdate)
	 * 
	 * [2] → caller method (e.g., generateOtp or verifyOtp)
	 * 
	 */
	private static boolean saveOrUpdate(Object object) {

		boolean issaveOrUpdate = true;

		Transaction transaction = null;

		String callerMethodName = Thread.currentThread().getStackTrace()[2].getMethodName();

		try (Session session = HibernateUtil.getSessionFactory().openSession()) {

			logger.info("ATSServiceImpl::{}::input::{}", callerMethodName, object);

			transaction = session.beginTransaction();

			session.saveOrUpdate(object);

			transaction.commit();

		} catch (Exception e) {

			if (transaction != null && transaction.isActive()) {
				transaction.rollback();
			}

			issaveOrUpdate = false;

			logger.error("ATSServiceImpl::saveOrUpdate::Exception::{}", e.toString(), e);

		}

		logger.info("ATSServiceImpl::{}::output::issaveOrUpdate={}", callerMethodName, issaveOrUpdate);

		return issaveOrUpdate;
	}

	/**
	 * Generic method to fetch an entity object from the database using class and
	 * ID.
	 *
	 * @param clazz the class type of the entity (e.g., User.class)
	 * @param id    the primary key of the object to retrieve
	 * @return Optional containing the object if found, or Optional.empty()
	 *         otherwise
	 */
	private static <T> Optional<T> getObject(Class<T> clazz, String id) {

		T object = null;

		String callerMethodName = Thread.currentThread().getStackTrace()[2].getMethodName();

		try (Session session = HibernateUtil.getSessionFactory().openSession()) {

			logger.info("ATSServiceImpl::{}::input::id={}", callerMethodName, id);

			object = session.get(clazz, id);

			logger.info("ATSServiceImpl::{}::output::{}", callerMethodName, object);

			return Optional.ofNullable(object);

		} catch (Exception e) {
			logger.error("ATSServiceImpl::getObject::Exception::{}", e.toString(), e);

		}
		return Optional.empty();

	}

	/**
	 * Generic method to retrieve a list of all records of a given entity class.
	 *
	 * @param clazz the class type of the entity (e.g., User.class)
	 * @return List of all objects of the given class type
	 */
	private static <T> Optional<List<T>> getMultipleObjects(Class<T> clazz, String id, String feildName, int pageNumber,
			int pageSize) {

		List<T> list = null;

		String hql = "from " + clazz.getSimpleName() + " t where t." + feildName + ".id = :userId";

		String callerMethodName = Thread.currentThread().getStackTrace()[2].getMethodName();

		try (Session session = HibernateUtil.getSessionFactory().openSession()) {

			logger.info("ATSServiceImpl::{}::input::id={},pageNumber={}", callerMethodName, id, pageNumber);

			list = session.createQuery(hql, clazz).setParameter("userId", id)
					.setFirstResult((pageNumber - 1) * pageSize).setMaxResults(pageSize).list();

			logger.info("ATSServiceImpl::{}::output::{}", callerMethodName, list);

			return Optional.ofNullable(list);

		} catch (Exception e) {
			logger.error("ATSServiceImpl::getMultipleObjects::Exception::{}", e.toString(), e);
		}

		return Optional.empty();

	}

	/**
	 * Generic method to delete an object of a given class type by its ID.
	 *
	 * @param clazz the class type of the entity (e.g., User.class)
	 * @param id    the primary key of the object to delete
	 * @return true if deletion was successful, false otherwise
	 */
	private static <T> boolean deleteRecord(Class<T> clazz, String id) {

		boolean isDeleted = true;

		String callerMethodName = Thread.currentThread().getStackTrace()[2].getMethodName();

		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {

			logger.info("ATSServiceImpl::{}::input::id={}", callerMethodName, id);

			Object object = session.get(clazz, id);

			transaction = session.beginTransaction();

			if (object != null) {
				session.delete(object);
			}

			transaction.commit();

		} catch (Exception e) {

			if (transaction != null && transaction.isActive()) {
				transaction.rollback();
			}
			logger.error("ATSServiceImpl::deleteRecord::Exception::{}", e.toString(), e);

			isDeleted = false;
		}

		logger.info("ATSServiceImpl::{}::output::isDeleted={}", callerMethodName, isDeleted);

		return isDeleted;

	}

	/**
	 * Checks whether a given username or email already exists in the database.
	 *
	 * @param username the username to check
	 * @param email    the email to check
	 * @return true if the username or email does NOT exist, false if it exists
	 */
	private static boolean usernameAndEmailExist(String username, String email) {

		boolean isExits = true;

		User user = null;

		String callerMethodName = Thread.currentThread().getStackTrace()[2].getMethodName();

		try (Session session = HibernateUtil.getSessionFactory().openSession()) {

			logger.info("ATSServiceImpl::{}::input::username={},email={}", callerMethodName, username, email);

			Criteria criteria = session.createCriteria(User.class);

			if (username != null) {
				criteria.add(Restrictions.eq("username", username));
			}
			if (email != null) {
				criteria.add(Restrictions.eq("email", email));
			}
			user = (User) criteria.uniqueResult();

			isExits = (user != null);

			logger.info("ATSServiceImpl::{}::output::isExists={}", callerMethodName, isExits);

		} catch (Exception e) {
			logger.error("ATSServiceImpl::usernameAndEmailExist::Exception::{}", e.toString(), e);
		}

		return isExits;

	}

}
