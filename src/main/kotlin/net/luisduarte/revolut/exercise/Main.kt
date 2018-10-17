package net.luisduarte.revolut.exercise

import com.google.inject.persist.PersistService
import com.google.inject.servlet.GuiceFilter
import org.eclipse.jetty.server.Server
import org.eclipse.jetty.servlet.FilterHolder
import org.eclipse.jetty.servlet.ServletContextHandler
import org.eclipse.jetty.servlet.ServletContextHandler.NO_SESSIONS
import org.slf4j.LoggerFactory
import java.util.*
import javax.servlet.DispatcherType


lateinit var server: Server

fun main(args: Array<String>) {
    startServer(9090)
}

fun startServer(port: Int) {
    val injector = GuiceFeature.injector
    server = Server(port)
    val handler = ServletContextHandler(NO_SESSIONS)

    injector.getInstance(PersistService::class.java).start()

    handler.contextPath = "/"

    val jerseyServlet = handler.addServlet(
            org.glassfish.jersey.servlet.ServletContainer::class.java, "/api/*")
    jerseyServlet.initOrder = 0
    jerseyServlet.setInitParameter(
            "javax.ws.rs.Application",
            RestApi::class.java.name
    )

    val guiceFilter = FilterHolder(injector.getInstance(GuiceFilter::class.java))
    handler.addFilter(guiceFilter, "/*", EnumSet.allOf(DispatcherType::class.java))

    server.handler = handler

    try {
        server.start()
        server.join()
    } catch (ex: Exception) {
        LoggerFactory.getLogger("Main.java").error(null, ex)
    } finally {
        server.destroy()
    }
}