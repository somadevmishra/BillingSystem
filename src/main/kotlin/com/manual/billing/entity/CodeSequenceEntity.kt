package com.manual.billing.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "code_sequence")
data class CodeSequenceEntity(

    @Id
    @Column(name = "sequence_name")
    val sequenceName: String,

    @Column(name = "next_value")
    var nextValue: Long
)