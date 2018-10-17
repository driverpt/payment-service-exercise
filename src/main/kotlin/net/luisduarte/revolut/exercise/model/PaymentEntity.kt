package net.luisduarte.revolut.exercise.model

import org.hibernate.annotations.GenericGenerator
import java.math.BigDecimal
import java.util.*
import javax.persistence.*
import javax.validation.constraints.NotNull

@Entity
@Table(name = "payments")
data class PaymentEntity(
        @Id
        @GeneratedValue(generator = "UUID")
        @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
        @Column(name = "id", updatable = false, nullable = false)
        val id: UUID?,

        @Column
        @NotNull
        val amount: BigDecimal,

        @ManyToOne
        @NotNull
        val from: AccountEntity,

        @ManyToOne
        @NotNull
        val to: AccountEntity,

        @Column
        val description: String?
)