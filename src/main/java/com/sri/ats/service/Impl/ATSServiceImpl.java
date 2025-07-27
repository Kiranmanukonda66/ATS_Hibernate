package com.sri.ats.service.Impl;

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

public class ATSServiceImpl implements ATSService {

	private static final Logger logger = LogManager.getLogger(ATSServiceImpl.class);
	private static final Logger sensitiveLogger = LogManager.getLogger("sensitive");

	@Override
	public boolean registration(User user) {

		boolean isRegister = true;

		Transaction transaction = null;

		try (Session session = HibernateUtil.getSessionFactory().openSession()) {

			logger.info("ATSSserviceImpl::registration::Input::{}", user);

			sensitiveLogger.debug("ATSSserviceImpl::registration::Input::password={}", user.getPassword());

			transaction = session.beginTransaction();

			session.save(user);

			transaction.commit();

		} catch (Exception e) {

			if (transaction != null && transaction.isActive()) {
				transaction.rollback();
			}

			isRegister = false;

			logger.error("ATSServiceImpl::registration::Exception::{}", e);

		}

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

		User user = null;

		try (Session session = HibernateUtil.getSessionFactory().openSession()) {

			logger.info("ATSServiceImpl::getUser::input::userId={}", id);

			user = session.get(User.class, id);

			logger.info("ATSServiceImpl::getUser::output::{}", user);

			return Optional.ofNullable(user);

		} catch (Exception e) {
			logger.error("ATSServiceImpl::getUser::Exception::{}", e);

		}
		return Optional.empty();
	}

	@Override
	public boolean updateProfile(User user) {

		boolean isUpdated = true;

		Transaction transaction = null;

		try (Session session = HibernateUtil.getSessionFactory().openSession()) {

			logger.info("ATSServiceImpl::updateProfile::input::{}", user);

			transaction = session.beginTransaction();

			session.update(user);

			transaction.commit();

			logger.info("ATSServiceImpl::updateProfile::output::isUpdated={}", isUpdated);

		} catch (Exception e) {

			if (transaction != null && transaction.isActive()) {
				transaction.rollback();
			}

			isUpdated = false;

			logger.error("ATSServiceImpl::updateProfile::Exception::{}", e);
		}

		return isUpdated;
	}

	@Override
	public boolean checkUsernameExists(String username) {

		boolean isUserNameExits = true;

		User user = null;

		try (Session session = HibernateUtil.getSessionFactory().openSession()) {

			logger.info("ATSServiceImpl::chechUsernameExists::input::username={}", username);

			Criteria criteria = session.createCriteria(User.class);

			criteria.add(Restrictions.eq("username", username));

			user = (User) criteria.uniqueResult();

			isUserNameExits = (user != null);

			logger.info("ATSServiceImpl::chechUsernameExists::output::isUsernameExits={}", isUserNameExits);

		} catch (Exception e) {
			logger.error("ATSServiceImpl::chechUsernameExists::Exception::{}", e);
		}

		return isUserNameExits;
	}

	@Override
	public boolean checkEmailExists(String email) {

		boolean isEmailExits = true;

		User user = null;

		try (Session session = HibernateUtil.getSessionFactory().openSession()) {

			logger.info("ATSServiceImpl::checkEmailExists::input::email={}", email);

			Criteria criteria = session.createCriteria(User.class);

			criteria.add(Restrictions.eq("email", email));

			user = (User) criteria.uniqueResult();

			isEmailExits = (user != null);

			logger.info("ATSServiceImpl::checkEmailExists::output::isEmailExits={}", isEmailExits);

		} catch (Exception e) {
			logger.error("ATSServiceImpl::checkEmailExists::Exception::{}", e);
		}

		return isEmailExits;

	}

	@Override
	public boolean addApplication(Application application) {

		boolean isAdded = true;

		Transaction transaction = null;

		try (Session session = HibernateUtil.getSessionFactory().openSession()) {

			logger.info("ATSServiceImpl::addApplication::input::{}", application);

			transaction = session.beginTransaction();

			session.save(application);

			transaction.commit();

			logger.info("ATSServiceImpl::addApplication::output::isadded={}", isAdded);

		} catch (Exception e) {

			if (transaction != null && transaction.isActive()) {
				transaction.rollback();
			}

			isAdded = false;

			logger.error("ATSServiceImpl::addApplication::Exception::{}", e);
		}

		return isAdded;
	}

	@Override
	public boolean editApplication(Application application) {

		boolean isedited = true;

		Transaction transaction = null;

		try (Session session = HibernateUtil.getSessionFactory().openSession()) {

			logger.info("ATSServiceImpl::editApplication::input::{}", application);

			transaction = session.beginTransaction();

			session.update(application);

			transaction.commit();

			logger.info("ATSServiceImpl::editApplication::output::isedited={}", isedited);

		} catch (Exception e) {

			if (transaction != null && transaction.isActive()) {
				transaction.rollback();
			}

			isedited = false;

			logger.error("ATSServiceImpl::editApplication::Exception::{}", e);
		}

		return isedited;
	}

	@Override
	public boolean deleteApplication(String appId) {

		boolean isDeleted = true;

		Transaction transaction = null;

		try (Session session = HibernateUtil.getSessionFactory().openSession()) {

			logger.info("ATSServiceImpl::deleteApplication::input::applicationId={}", appId);

			Application application = session.get(Application.class, appId);

			transaction = session.beginTransaction();

			if (application != null) {
				session.delete(application);
			}

			transaction.commit();

			logger.info("ATSServiceImpl::deleteApplication::output::isDelted={}", isDeleted);

		} catch (Exception e) {

			if (transaction != null && transaction.isActive()) {
				transaction.rollback();
			}
			logger.error("ATSServiceImpl::deleteApplication::Exception::{}", e);

			isDeleted = false;
		}

		return isDeleted;

	}

	@Override
	public Optional<List<Application>> getApplicationsByUserId(String userId, int pageNumber, int pageSize) {

		List<Application> applications = null;

		try (Session session = HibernateUtil.getSessionFactory().openSession()) {

			logger.info("ATSServiceImpl::getApplicationsByUserId::input::userId={}", userId);

			applications = session.createQuery("from application a where a.user.id = :userId", Application.class)
					.setParameter("userId", userId).setFirstResult((pageNumber - 1) * pageSize).setMaxResults(pageSize)
					.list();

			logger.info("ATSServiceImpl::getApplicationsByUserId::output::{}", applications);

			return Optional.ofNullable(applications);

		} catch (Exception e) {
			logger.error("ATSServiceImpl::getApplicationsByUserId::Exception::{}", e);
		}

		return Optional.empty();

	}

	@Override
	public Optional<Application> getApplication(String appId) {

		Application application = null;

		try (Session session = HibernateUtil.getSessionFactory().openSession()) {

			logger.info("ATSServiceImpl::getApplication::input::appId={}", appId);

			application = session.get(Application.class, appId);

			logger.info("ATSServiceImpl::getApplication::output::{}", application);

			return Optional.ofNullable(application);

		} catch (Exception e) {
			logger.error("ATSServiceImpl::getApplication::Exception::{}", e);
		}

		return Optional.empty();

	}

	@Override
	public boolean addInterview(Interview interview) {

		boolean isAdded = true;

		Transaction transaction = null;

		try (Session session = HibernateUtil.getSessionFactory().openSession()) {

			logger.info("ATSServiceImpl::addInterview::input::{}", interview);

			transaction = session.beginTransaction();

			session.save(interview);

			transaction.commit();

			logger.info("ATSServiceImpl::addInterview::output::isadded={}", isAdded);

		} catch (Exception e) {

			if (transaction != null && transaction.isActive()) {
				transaction.rollback();
			}

			isAdded = false;

			logger.error("ATSServiceImpl::addInterview::Exception::{}", e);
		}

		return isAdded;

	}

	@Override
	public boolean editInterview(Interview interview) {

		boolean isedited = true;

		Transaction transaction = null;

		try (Session session = HibernateUtil.getSessionFactory().openSession()) {

			logger.info("ATSServiceImpl::editInterview::input::{}", interview);

			transaction = session.beginTransaction();

			session.update(interview);

			transaction.commit();

			logger.info("ATSServiceImpl::editInterview::output::isedited={}", isedited);

		} catch (Exception e) {

			if (transaction != null && transaction.isActive()) {
				transaction.rollback();
			}

			isedited = false;

			logger.error("ATSServiceImpl::editInterview::Exception::{}", e);
		}

		return isedited;

	}

	@Override
	public boolean deleteInterview(String interviewId) {

		boolean isDeleted = true;

		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {

			logger.info("ATSServiceImpl::deleteInterview::input::interviewId={}", interviewId);

			Interview interview = session.get(Interview.class, interviewId);

			transaction = session.beginTransaction();

			if (interview != null) {
				session.delete(interview);
			}

			transaction.commit();

			logger.info("ATSServiceImpl::deleteInterview::output::isDelted={}", isDeleted);

		} catch (Exception e) {

			if (transaction != null && transaction.isActive()) {
				transaction.rollback();
			}
			logger.error("ATSServiceImpl::deleteInterview::Exception::{}", e);

			isDeleted = false;
		}

		return isDeleted;

	}

	@Override
	public Optional<Interview> getInterview(String interviewId) {

		Interview interview = null;

		try (Session session = HibernateUtil.getSessionFactory().openSession()) {

			logger.info("ATSServiceImpl::getInterview::input::appId={}", interviewId);

			interview = session.get(Interview.class, interviewId);

			logger.info("ATSServiceImpl::getInterview::output::{}", interview);

			return Optional.ofNullable(interview);

		} catch (Exception e) {
			logger.error("ATSServiceImpl::getInterview::Exception::{}", e);
		}

		return Optional.empty();

	}

	@Override
	public Optional<List<Interview>> getInterviewByUserId(String userId, int pageNumber, int pageSize) {

		List<Interview> interviews = null;

		try (Session session = HibernateUtil.getSessionFactory().openSession()) {

			logger.info("ATSServiceImpl::getInterviewByUserId::input::userId={}", userId);

			User user = session.get(User.class, userId);

			if (user != null) {

				interviews = user.getInterviews();

			}

			logger.info("ATSServiceImpl::getInterviewByUserId::output::{}", interviews);

			return Optional.ofNullable(interviews);

		} catch (Exception e) {
			logger.error("ATSServiceImpl::getInterviewByUserId::Exception::{}", e);
		}

		return Optional.empty();

	}

}
