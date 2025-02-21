package br.com.droidchat.ui.validator

interface FormValidator<T> {
    fun validate(formState: T): T
}