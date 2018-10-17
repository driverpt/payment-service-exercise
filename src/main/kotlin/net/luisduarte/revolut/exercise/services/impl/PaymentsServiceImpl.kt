package net.luisduarte.revolut.exercise.services.impl

import net.luisduarte.revolut.exercise.HibernateUtil
import net.luisduarte.revolut.exercise.model.PaymentEntity
import net.luisduarte.revolut.exercise.services.PaymentsService
import org.hibernate.Session
import java.util.*
import javax.inject.Inject
import javax.persistence.EntityManager
import javax.persistence.criteria.Expression

open class PaymentsServiceImpl: PaymentsService {
    override fun listByAccount(id: String): List<PaymentEntity> {
        val criteriaBuilder = entityManager.criteriaBuilder
        val query = criteriaBuilder.createQuery(PaymentEntity::class.java)
        val root = query.from(PaymentEntity::class.java)
        query
                .select(root)
                .where(
                        criteriaBuilder
                                .or(
                                        criteriaBuilder.equal(root.get<Expression<*>>("from"), id),
                                        criteriaBuilder.equal(root.get<Expression<*>>("to"), id)
                                )
                )

        val session = HibernateUtil.getSession(entityManager)
        session.beginTransaction()
        return session.use<Session, List<PaymentEntity>> { sess -> sess.createQuery(query).list() }
    }

    override fun listAll(): List<PaymentEntity> {
        throw UnsupportedOperationException()
    }

    @Inject
    private lateinit var entityManager: EntityManager

    override fun findById(uuid: String): PaymentEntity? {
        val session = HibernateUtil.getSession(entityManager)
        val transaction = session.beginTransaction()
        val result = session.use { session -> session.get(PaymentEntity::class.java, UUID.fromString(uuid)) }
        transaction.commit()
        return result
    }

    override fun create(entity: PaymentEntity): PaymentEntity {
        if (entity.id != null) {
            throw IllegalArgumentException()
        }

        val session = HibernateUtil.getSession(entityManager)
        val paymentDAO = entity.copy(id = UUID.randomUUID())
        val transaction = session.beginTransaction()
        session.use { session -> session.saveOrUpdate(paymentDAO) }
        transaction.commit()
        return paymentDAO
    }
}