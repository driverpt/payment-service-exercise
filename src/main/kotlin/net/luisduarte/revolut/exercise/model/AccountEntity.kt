package net.luisduarte.revolut.exercise.model

import org.hibernate.annotations.GenericGenerator
import java.math.BigDecimal
import java.util.*
import javax.persistence.*
import javax.validation.constraints.NotNull

@Entity
@Table(name = "accounts")
data class AccountEntity(
        @Id
        @GeneratedValue(generator = "UUID")
        @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
        @Column(name = "id", updatable = false, nullable = false)
        val id: UUID?,

        @Column(name = "balance")
        @NotNull
        val balance: BigDecimal,

        @Column(name = "owner")
        @NotNull
        val owner: String)