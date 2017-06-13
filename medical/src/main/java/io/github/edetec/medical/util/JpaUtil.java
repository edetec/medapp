package io.github.edetec.medical.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class JpaUtil {
	private static final SessionFactory sessionFactory;
	private static final ThreadLocal<Session> threadLocal;

	static {
		try {
			threadLocal = new ThreadLocal<Session>();
			sessionFactory = new Configuration().configure().buildSessionFactory();
		} catch (Throwable ex) {
			System.err.println("Initial SessionFactory creation failed." + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}

	private static SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public static Session getSession() {
		Session session = threadLocal.get();

		if (session == null || !session.isOpen()) {
			session = sessionFactory.openSession();
			threadLocal.set(session);
		}

		return session;
	}

	public static void closeSession() {
		Session session = threadLocal.get();

		if (session != null) {
			session.close();
		}
	}

}
