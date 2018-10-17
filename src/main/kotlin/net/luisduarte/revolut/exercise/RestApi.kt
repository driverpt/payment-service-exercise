package net.luisduarte.revolut.exercise

import com.google.inject.Injector
import com.google.inject.persist.PersistService
import org.glassfish.hk2.api.ServiceLocator
import org.glassfish.jersey.InjectionManagerProvider
import org.glassfish.jersey.internal.inject.InjectionManager
import org.glassfish.jersey.jackson.JacksonFeature
import org.glassfish.jersey.logging.LoggingFeature
import org.glassfish.jersey.server.ResourceConfig
import org.glassfish.jersey.server.spi.Container
import org.jvnet.hk2.guice.bridge.api.GuiceBridge
import javax.inject.Inject
import javax.ws.rs.ApplicationPath
import org.jvnet.hk2.guice.bridge.api.GuiceIntoHK2Bridge
import javax.ws.rs.core.Feature
import org.glassfish.jersey.servlet.ServletContainer
import org.glassfish.jersey.server.spi.ContainerLifecycleListener



@ApplicationPath("/api/*")
class RestApi: ResourceConfig() {

    init {
        register(object : ContainerLifecycleListener {
            override fun onStartup(container: Container) {
                val servletContainer = container as ServletContainer

                val injectionManager = container.getApplicationHandler().injectionManager
                val serviceLocator = injectionManager.getInstance(ServiceLocator::class.java)
                GuiceBridge.getGuiceBridge().initializeGuiceBridge(serviceLocator)
                val guiceBridge = serviceLocator.getService(GuiceIntoHK2Bridge::class.java)
                guiceBridge.bridgeGuiceInjector(GuiceFeature.injector)
            }

            override fun onReload(container: Container) {}
            override fun onShutdown(container: Container) {}
        })
        //register(GuiceFeature::class.java)
        packages(true,"net.luisduarte.revolut.exercise")
        register(RestAPIModule())
        register(LoggingFeature::class.java)
        register(JacksonFeature::class.java)

    }
}