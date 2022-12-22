package com.example.sastreilor

import android.support.annotation.Keep
import kotlinx.serialization.SerialName


@Keep
@kotlinx.serialization.Serializable
data class EmailResponse(

    @SerialName("can_connect_smtp")
    val can_connect_smtp: Boolean?,
    @SerialName("did_you_mean")
    val did_you_mean: String?,
    @SerialName("domain")
    val domain: String?,
    @SerialName("email")
    val email: String?,
    @SerialName("free")
    val free: Boolean?,
    @SerialName("is_catch_all")
    val is_catch_all: Boolean?,
    @SerialName("is_deliverable")
    val is_deliverable: Boolean?,
    @SerialName("is_disabled")
    val is_disabled: String?,
    @SerialName("is_disposable")
    val is_disposable: Boolean?,
    @SerialName("is_inbox_full")
    val  is_inbox_full: String?,
    @SerialName("is_role_account")
    val is_role_account: Boolean?,
    @SerialName("mx_records")
    val mx_records: Boolean?,
    @SerialName("score")
    val score: Float?,
    @SerialName("syntax_valid")
    val syntax_valid: Boolean?,
    @SerialName("user")
    val user:String?
)
