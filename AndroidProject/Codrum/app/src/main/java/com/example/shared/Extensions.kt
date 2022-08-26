package com.example.shared

import androidx.annotation.CheckResult
import androidx.core.widget.doOnTextChanged
import com.example.codrum.R
import com.example.shared.Extensions.Companion.BLANK_ERROR
import com.example.shared.Extensions.Companion.EMAIL_FORMAT_ERROR
import com.example.shared.Extensions.Companion.NICK_FORMAT_ERROR
import com.example.shared.Extensions.Companion.PASSWORD_FORMAT_ERROR
import com.example.shared.Extensions.Companion.emailPattern
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.*

class Extensions {
    companion object {
        const val BLANK_ERROR = 0
        const val EMAIL_FORMAT_ERROR = 1
        const val PASSWORD_FORMAT_ERROR = 2
        const val NICK_FORMAT_ERROR = 3
        val emailPattern = android.util.Patterns.EMAIL_ADDRESS
    }
}

@ExperimentalCoroutinesApi
@CheckResult
fun TextInputLayout.textChanges(): Flow<CharSequence?> {
    return callbackFlow {
        val listener = editText?.doOnTextChanged { text, _, _, _ -> trySend(text) }
        awaitClose { editText?.removeTextChangedListener(listener) }
    }.onStart { emit(editText?.text) }
}

fun TextInputLayout.checkNullOrFormat(checkFunction: () -> Boolean, type: Int): Boolean {
    var state = true
    if (editText?.text.toString().isNullOrEmpty()) {
        setError(BLANK_ERROR)
        state = false
    } else {
        if (!checkFunction()) {
            setError(type)
            state = false
        } else {
            error = null
        }
    }
    return state
}

fun isValidEmail(email: String) = emailPattern.matcher(email).matches()

fun isValidPasswd(passwd: String) = passwd.length in 6..15

fun isValidName(name: String) = name.length in 2..10

fun TextInputLayout.setError(type: Int) {
    error = when (type) {
        BLANK_ERROR -> context.getString(R.string.blank_error)
        EMAIL_FORMAT_ERROR -> context.getString(R.string.email_format_error)
        PASSWORD_FORMAT_ERROR -> context.getString(R.string.password_format_error)
        NICK_FORMAT_ERROR -> context.getString(R.string.name_format_error)
        else -> return
    }
}

fun TextInputLayout.initError() {
    textChanges().debounce(200L)
        .onEach {
            error = null
        }
        .launchIn(CoroutineScope(Dispatchers.Main))
}

fun MaterialButton.check(
    firstTextInputLayout: TextInputLayout,
    secondTextInputLayout: TextInputLayout
): Boolean {
    var first = true
    var second = true
    firstTextInputLayout.apply {
        first = checkNullOrFormat(
            { isValidEmail(editText?.text.toString()) },
            EMAIL_FORMAT_ERROR
        )
    }
    secondTextInputLayout.apply {
        second = checkNullOrFormat(
            { isValidPasswd(editText?.text.toString()) },
            PASSWORD_FORMAT_ERROR
        )
    }

    return first and second
}

fun MaterialButton.check(
    firstTextInputLayout: TextInputLayout,
    secondTextInputLayout: TextInputLayout,
    thirdTextInputLayout: TextInputLayout
): Boolean {
    var first = true
    var second = true
    var third = true

    firstTextInputLayout.apply {
        first = checkNullOrFormat(
            { isValidEmail(editText?.text.toString()) },
            EMAIL_FORMAT_ERROR
        )
    }
    secondTextInputLayout.apply {
        second = checkNullOrFormat(
            { isValidPasswd(editText?.text.toString()) },
            PASSWORD_FORMAT_ERROR
        )
    }
    thirdTextInputLayout.apply {
        third = checkNullOrFormat(
            { isValidName(editText?.text.toString()) },
            NICK_FORMAT_ERROR
        )
    }
    return first and second and third
}