package net.luisduarte.revolut.exercise.endpoints

import net.luisduarte.revolut.exercise.endpoints.model.AccountDTO
import net.luisduarte.revolut.exercise.model.AccountEntity
import net.luisduarte.revolut.exercise.services.AccountService
import java.math.BigDecimal
import java.net.URI
import java.util.*
import javax.inject.Inject
import javax.ws.rs.*
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response
import javax.ws.rs.core.Response.ok

@Path("/accounts")
@Produces(MediaType.APPLICATION_JSON)
class AccountsEndpoint(@Inject var service: AccountService) {

    @GET
    fun getAllAccounts(): Response {
        val result = service.listAll()
                .map { accountEntity -> AccountDTO(accountEntity.id.toString(), accountEntity.balance, accountEntity.owner) }
        return ok().entity(result).build()
    }

    @POST
    fun createAccount(accountDTO: AccountDTO): Response {
        var accountEntity = AccountEntity(null, BigDecimal.ZERO, accountDTO.owner)
        accountEntity = service.create(dao = accountEntity)
        return Response.seeOther(URI.create("/api/accounts/${accountEntity.id.toString()}")).build()
    }

    @GET
    @Path("/{accountId}")
    fun getAccountInfo(@PathParam("accountId") accountId: String): Response {
        val accountEntity: AccountEntity = service.findById(accountId) ?: return Response.status(Response.Status.NOT_FOUND).build()

        val result = AccountDTO(accountEntity.id.toString(), accountEntity.balance, accountEntity.owner)
        return Response.ok().entity(result).build()
    }

}