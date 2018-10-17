package net.luisduarte.revolut.exercise

import com.google.inject.AbstractModule
import com.google.inject.persist.jpa.JpaPersistModule
import com.google.inject.servlet.GuiceFilter
import net.luisduarte.revolut.exercise.services.AccountService
import net.luisduarte.revolut.exercise.services.PaymentsService
import net.luisduarte.revolut.exercise.services.impl.AccountServiceImpl
import net.luisduarte.revolut.exercise.services.impl.PaymentsServiceImpl

class RestAPIModule: AbstractModule() {
    override fun configure() {
        install(JpaPersistModule("TestPersistenceUnit"))
        bind(GuiceFilter::class.java)
        bind(AccountService::class.java).to(AccountServiceImpl::class.java)
        bind(PaymentsService::class.java).to(PaymentsServiceImpl::class.java)
    }


}