package net.luisduarte.revolut.exercise

import com.google.inject.Guice
import com.google.inject.Injector
import org.glassfish.hk2.api.ServiceLocator
import org.glassfish.jersey.InjectionManagerProvider
import org.jvnet.hk2.guice.bridge.api.GuiceBridge
import org.jvnet.hk2.guice.bridge.api.GuiceIntoHK2Bridge
import javax.ws.rs.core.Feature
import javax.ws.rs.core.FeatureContext

class GuiceFeature: Feature {

    companion object {
        val injector: Injector = Guice.createInjector(RestAPIModule())
    }

    override fun configure(context: FeatureContext?): Boolean {
        val injectionManager = InjectionManagerProvider.getInjectionManager(context)
        val serviceLocator = injectionManager.getInstance(ServiceLocator::class.java)
        GuiceBridge.getGuiceBridge().initializeGuiceBridge(serviceLocator)
        val guiceBridge = serviceLocator.getService(GuiceIntoHK2Bridge::class.java)
        guiceBridge.bridgeGuiceInjector(injector)
        return true
    }
}