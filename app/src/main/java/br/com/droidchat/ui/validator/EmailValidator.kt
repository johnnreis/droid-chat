package br.com.droidchat.ui.validator

object EmailValidator {
    private const val EMAIL_REGEX = "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
            "\\@" +
            "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
            "(" +
            "\\." +
            "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
            ")+"
    fun isEmailValid(value: String) : Boolean {
        return EMAIL_REGEX.toRegex().matches(value)
    }
}