package com.pp.trisscore.model.architecture

import com.pp.trisscore.model.classes.SettlementElement
import com.pp.trisscore.model.rows.FullSettlementApplication

data class SettlementInfo(
    val fullSettlementApplication: FullSettlementApplication,
    val settlementElements: List<SettlementElement>
)
