package com.sri.ats.service.Impl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

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

	@Override
	public boolean registration(User user) {

		logger.info("ATSSserviceImpl::registration::Input::{}", user);

		sensitiveLogger.debug("ATSSserviceImpl::registration::Input::password={}", user.getPassword());

		boolean isRegister = saveOrUpdate(user);

		logger.info("ATSSserviceImpl::registration::output::isRegister={}", isRegister);

		return isRegister;
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

		logger.info("ATSServiceImpl::getUser::input::userId={}", id);

		Optional<User> user = getSingleObject(User.class, id);

		logger.info("ATSServiceImpl::getUser::output::{}", user.orElse(null));

		return user;
	}

	@Override
	public boolean updateProfile(User user) {

		logger.info("ATSServiceImpl::updateProfile::input::{}", user);

		boolean isUpdated = saveOrUpdate(user);

		logger.info("ATSServiceImpl::updateProfile::output::isUpdated={}", isUpdated);

		return isUpdated;
	}

	@Override
	public boolean checkUsernameExists(String username) {

		logger.info("ATSServiceImpl::chechUsernameExists::input::username={}", username);

		boolean isUserNameExits = usernameAndEmailExist(username, null);

		logger.info("ATSServiceImpl::chechUsernameExists::output::isUsernameExits={}", isUserNameExits);

		return isUserNameExits;
	}

	@Override
	public boolean checkEmailExists(String email) {

		logger.info("ATSServiceImpl::checkEmailExists::input::email={}", email);

		boolean isEmailExits = usernameAndEmailExist(null, email);

		logger.info("ATSServiceImpl::checkEmailExists::output::isEmailExits={}", isEmailExits);

		return isEmailExits;

	}

	@Override
	public boolean addApplication(Application application) {

		logger.info("ATSServiceImpl::addApplication::input::{}", application);

		boolean isAdded = saveOrUpdate(application);

		logger.info("ATSServiceImpl::addApplication::output::isadded={}", isAdded);

		return isAdded;
	}

	@Override
	public boolean editApplication(Application application) {

		logger.info("ATSServiceImpl::editApplication::input::{}", application);

		boolean isUpdated = saveOrUpdate(application);

		logger.info("ATSServiceImpl::editApplication::output::isupdated={}", isUpdated);

		return isUpdated;
	}

	@Override
	public boolean deleteApplication(String appId) {

		logger.info("ATSServiceImpl::deleteApplication::input::applicationId={}", appId);

		boolean isDeleted = deleteRecord(Application.class, appId);

		logger.info("ATSServiceImpl::deleteApplication::output::isDelted={}", isDeleted);

		return isDeleted;

	}

	@Override
	public Optional<List<Application>> getApplicationsByUserId(String userId, int pageNumber, int pageSize) {

		logger.info("ATSServiceImpl::getApplicationsByUserId::input::userId={}", userId);

		Optional<List<Application>> result = getMultipleObjects(Application.class, userId, "user", pageNumber,
				pageSize);

		logger.info("ATSServiceImpl::getApplicationsByUserId::output::{}", result.orElse(Collections.emptyList()));

		return result;
	}

	@Override
	public Optional<Application> getApplication(String appId) {

		logger.info("ATSServiceImpl::getApplication::input::appId={}", appId);

		Optional<Application> result = getSingleObject(Application.class, appId);

		logger.info("ATSServiceImpl::getApplication::output::{}", result.orElse(null));

		return result;
	}

	@Override
	public boolean addInterview(Interview interview) {

		logger.info("ATSServiceImpl::addInterview::input::{}", interview);

		boolean isAdded = saveOrUpdate(interview);

		logger.info("ATSServiceImpl::addInterview::output::isadded={}", isAdded);

		return isAdded;
	}

	@Override
	public boolean editInterview(Interview interview) {

		logger.info("ATSServiceImpl::editInterview::input::{}", interview);

		boolean isUpdated = saveOrUpdate(interview);

		logger.info("ATSServiceImpl::editInterview::output::isedited={}", isUpdated);

		return isUpdated;
	}

	@Override
	public boolean deleteInterview(String interviewId) {

		logger.info("ATSServiceImpl::deleteInterview::input::interviewId={}", interviewId);

		boolean isDeleted = deleteRecord(Interview.class, interviewId);

		logger.info("ATSServiceImpl::deleteInterview::output::isDelted={}", isDeleted);

		return isDeleted;

	}

	@Override
	public Optional<Interview> getInterview(String interviewId) {

		logger.info("ATSServiceImpl::getInterview::input::appId={}", interviewId);

		Optional<Interview> result = getSingleObject(Interview.class, interviewId);

		logger.info("ATSServiceImpl::getInterview::output::{}", result.orElse(null));

		return result;
	}

	@Override
	public Optional<List<Interview>> getInterviewByUserId(String userId, int pageNumber, int pageSize) {

		logger.info("ATSServiceImpl::getInterviewByUserId::input::userId={}", userId);

		Optional<List<Interview>> result = getMultipleObjects(Interview.class, userId, "user", pageNumber, pageSize);

		logger.info("ATSServiceImpl::getInterviewByUserId::output::{}", result.orElse(Collections.emptyList()));

		return result;
	}

	/**
	 * Utility method to saves and updates the record to the database.
	 *
	 * @param Object
	 * @return true if it is saved or updated the record , false if not done
	 */
	private static boolean saveOrUpdate(Object object) {

		boolean issaveOrUpdate = true;

		Transaction transaction = null;

		try (Session session = HibernateUtil.getSessionFactory().openSession()) {

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
	private static <T> Optional<T> getSingleObject(Class<T> clazz, String id) {

		T object = null;

		try (Session session = HibernateUtil.getSessionFactory().openSession()) {

			object = session.get(clazz, id);

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

		try (Session session = HibernateUtil.getSessionFactory().openSession()) {

			list = session.createQuery(hql, clazz).setParameter("userId", id)
					.setFirstResult((pageNumber - 1) * pageSize).setMaxResults(pageSize).list();

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

		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {

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

		try (Session session = HibernateUtil.getSessionFactory().openSession()) {

			Criteria criteria = session.createCriteria(User.class);

			if (username != null) {
				criteria.add(Restrictions.eq("username", username));
			}
			if (email != null) {
				criteria.add(Restrictions.eq("email", email));
			}
			user = (User) criteria.uniqueResult();

			isExits = (user != null);

		} catch (Exception e) {
			logger.error("ATSServiceImpl::usernameAndEmailExist::Exception::{}", e.toString(), e);
		}

		return isExits;

	}

}
