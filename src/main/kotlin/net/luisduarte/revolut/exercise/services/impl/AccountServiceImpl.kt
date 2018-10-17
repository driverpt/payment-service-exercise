package net.luisduarte.revolut.exercise.services.impl

import net.luisduarte.revolut.exercise.HibernateUtil
import net.luisduarte.revolut.exercise.model.AccountEntity
import net.luisduarte.revolut.exercise.services.AccountService
import org.hibernate.Session
import org.hibernate.SessionFactory
import java.util.*
import javax.inject.Inject
import javax.persistence.EntityManager

open class AccountServiceImpl: AccountService {
    override fun listAll(): List<AccountEntity> {
        val query = entityManager.criteriaBuilder.createQuery(AccountEntity::class.java)
        val root = query.from(AccountEntity::class.java)
        query.select(root)
        val session = HibernateUtil.getSession(entityManager)
        session.beginTransaction()
        return session.use<Session, List<AccountEntity>> { sess -> sess.createQuery(query).list() }
    }

    @Inject
    protected lateinit var entityManager: EntityManager

    override fun findById(uuid: String): AccountEntity? {
        val session = HibernateUtil.getSession(entityManager)
        val transaction = session.beginTransaction()
        val result = session.use { sess -> sess.get(AccountEntity::class.java, UUID.fromString(uuid)) }
        transaction.commit()
        return result
    }

    override fun create(entity: AccountEntity): AccountEntity {
        if (entity.id != null) {
            throw IllegalArgumentException()
        }

        val session = HibernateUtil.getSession(entityManager)
        val accountDAO = entity.copy(id = UUID.randomUUID())
        val transaction = session.beginTransaction()
        session.use { sess ->
            sess.save(accountDAO)
        }
        transaction.commit()
        return accountDAO
    }

    override fun update(entity: AccountEntity): AccountEntity {
        val session = HibernateUtil.getSession(entityManager)
        val transaction = session.beginTransaction()
        session.use { sess -> sess.saveOrUpdate(entity) }
        transaction.commit()
        return entity
    }
}