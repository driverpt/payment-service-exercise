package net.luisduarte.revolut.exercise.services

import net.luisduarte.revolut.exercise.model.PaymentEntity

interface PaymentsService: IService<PaymentEntity, String> {
    override fun update(entity: PaymentEntity): PaymentEntity {
        throw UnsupportedOperationException()
    }

    fun listByAccount(id: String): List<PaymentEntity>
}