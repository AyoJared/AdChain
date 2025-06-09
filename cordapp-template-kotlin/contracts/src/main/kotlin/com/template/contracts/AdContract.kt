package com.template.contracts

import com.template.states.AdState
import net.corda.core.contracts.CommandData
import net.corda.core.contracts.Contract
import net.corda.core.contracts.requireSingleCommand
import net.corda.core.contracts.requireThat
import net.corda.core.transactions.LedgerTransaction

// ************
// * Contract *
// ************
class AdContract : Contract {
    companion object {
        const val ID = "com.template.contracts.AdContract"
    }

    override fun verify(tx: LedgerTransaction) {
        val command = tx.commands.requireSingleCommand<Commands.Create>()
        val output = tx.outputsOfType<AdState>().first()

        when (command.value) {
            is Commands.Create -> requireThat {
                "No inputs should be consumed when creating an Ad." using (tx.inputs.isEmpty())
                "Only one output state should be created." using (tx.outputs.size == 1)
                "The advertiser and publisher must be different entities." using (output.advertiser != output.publisher)
                "The budget must be greater than zero." using (output.budget > 0)
                "The ad description must not be blank." using (output.adDescription.isNotBlank())
            }
        }
    }

    interface Commands : CommandData {
        class Create : Commands
    }
}
