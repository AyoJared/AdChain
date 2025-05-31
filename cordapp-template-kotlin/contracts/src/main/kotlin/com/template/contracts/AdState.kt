package com.template.contracts

import net.corda.core.contracts.ContractState
import net.corda.core.contracts.BelongsToContract
import net.corda.core.identity.Party

@BelongsToContract(AdContract::class)
data class AdState(
    val advertiser: Party, // Company Ad Promoting 
    val publisher: Party, // Host of Ad Prommotion 
    val adDescription: String,  // Info on Ad
    val budget: Int, // How much spent 
    val isApproved: Boolean = false, // Whether accepted by Publisher  
    override val participants: List<Party> = listOf(advertiser, publisher) // List of All parties that can see state and sign 
) : ContractState
