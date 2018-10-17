package net.luisduarte.revolut.exercise

import org.hibernate.HibernateException
import org.hibernate.Session
import org.hibernate.SessionFactory
import javax.persistence.EntityManager


object HibernateUtil {
    @Throws(HibernateException::class)
    fun getSession(entityManager: EntityManager): Session {
        val sessionFactory = entityManager.entityManagerFactory.unwrap(SessionFactory::class.java)

        return try {
            sessionFactory.currentSession
        } catch (he: HibernateException) {
            sessionFactory.openSession()
        }
    }
}