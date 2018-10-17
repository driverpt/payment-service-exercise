package net.luisduarte.revolut.exercise.endpoints

import net.luisduarte.revolut.exercise.endpoints.model.PaymentDTO
import net.luisduarte.revolut.exercise.model.PaymentEntity
import net.luisduarte.revolut.exercise.services.AccountService
import net.luisduarte.revolut.exercise.services.PaymentsService
import javax.inject.Inject
import javax.ws.rs.*
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response
import javax.ws.rs.core.Response.ok

@Path("/payments")
@Produces(MediaType.APPLICATION_JSON)
class PaymentsEndpoint(@Inject
                       var service: PaymentsService,
                       @Inject
                       var accountService: AccountService
                       ) {

    @GET
    @Path("/{accountId}")
    fun getPaymentsForAccount(@PathParam("accountId") account: String): Response {
        val result = service.listByAccount(account)
                .map { paymentEntity ->
                    PaymentDTO(id = paymentEntity.id.toString()
                            , amount = paymentEntity.amount
                            , from = paymentEntity.from.id.toString()
                            , to = paymentEntity.to.id.toString()
                    )
                }
        return ok().entity(result).build()
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    fun makePayment(paymentInfo: PaymentDTO): Response {
        val fromAccount = accountService.findById(paymentInfo.from)
        val toAccount = accountService.findById(paymentInfo.to)

        if(fromAccount == null || toAccount == null) {
            return Response.status(Response.Status.BAD_REQUEST).build()
        }

        

        return ok().build()
    }
}