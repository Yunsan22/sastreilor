package com.example.sastreilor

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch




class MailBoxState(private val coroutineScope:CoroutineScope) {
    private val _response = MutableStateFlow(EmailResponse(""))
    val response : StateFlow<EmailResponse>
    get() =_response
    private val _valid = MutableStateFlow("")
    val valid: StateFlow<String>
    get() = _valid

    private val _smtpCheck = MutableStateFlow("")
    val smtpCheck : StateFlow<String>
    get() = _smtpCheck


    private val _disposable = MutableStateFlow("")
    val disposable : StateFlow<String>
    get() = _disposable

    private val _free = MutableStateFlow("")
    val free: StateFlow<String>
    get() = _free

    private val _score = MutableStateFlow("")
    val score: StateFlow<String>
    get() = _score

    private val _mxRecord = MutableStateFlow("")
    val mxRecord: StateFlow<String>
    get() = _mxRecord


    fun checkEmail(emailState:String){
        try {
            coroutineScope.launch {
                _response.value = API.service.checkMail(email=emailState,key=API.API_KEY)
                Log.d("email2","${response.value}")
                _valid.value = if(response.value.syntax_valid) "Email is valid" else "invalid Email"
                _smtpCheck.value = if (response.value.can_connect_smtp)"Email Exists" else "Email doesnt exist"
                _disposable.value = if(response.value.is_disposable) "Email is disposable" else " Email is not Disposable"
                _free.value = if (response.value.free)"email is free" else "email is paid"
                _mxRecord.value = if(response.value.mx_records) "domain can receive email" else "domain cant recieve email"
                _score.value = "Score is ${response.value.score}"
            }
        }catch (e:Exception){

        }
    }

}