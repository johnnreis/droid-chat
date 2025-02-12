package br.com.droidchat.ui.feature.signin

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import br.com.droidchat.R
import br.com.droidchat.ui.components.PrimaryButton
import br.com.droidchat.ui.components.PrimaryTextField
import br.com.droidchat.ui.theme.BackgroundGradient
import br.com.droidchat.ui.theme.DroidChatTheme

@Composable
fun SignInRoute(
    viewModel: SignInViewModel = viewModel(),
) {
    val formState = viewModel.formState
    SignInScreen(
        formState = formState,
        onFormEvent = viewModel::onFormEvent
    )
}

@Composable
fun SignInScreen(
    formState: SignInFormState,
    onFormEvent: (SignInFormEvent) -> Unit,
) {

    DroidChatTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .background(brush = BackgroundGradient),

            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {

            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = null,
            )

            Spacer(modifier = Modifier.height(40.dp))


            PrimaryTextField(
                value = formState.email,
                onValueChange = {
                    onFormEvent(SignInFormEvent.EmailChanged(it))
                },
                modifier = Modifier
                    .padding(horizontal = dimensionResource(id = R.dimen.spacing_medium)),

                placeholder = stringResource(id = R.string.feature_login_email),
                leadingIcon = R.drawable.ic_envelope,
                keyboardType = KeyboardType.Email,
                errorMessage = formState.emailError?.let {
                    stringResource(id = it)
                }

            )

            Spacer(modifier = Modifier.height(20.dp))

            PrimaryTextField(
                value = formState.password,
                onValueChange = {
                    onFormEvent(SignInFormEvent.PasswordChanged(it))
                },
                modifier = Modifier
                    .padding(horizontal = dimensionResource(id = R.dimen.spacing_medium)),

                placeholder = stringResource(id = R.string.feature_login_password),
                leadingIcon = R.drawable.ic_lock,
                keyboardType = KeyboardType.Password,
                errorMessage = formState.passwordError?.let {
                    stringResource(id = it)
                }
            )

            Spacer(modifier = Modifier.height(48.dp))

            PrimaryButton(
                text = stringResource(id = R.string.feature_login_button),
                onClick = {
                    onFormEvent(SignInFormEvent.Submit)
                },
                modifier = Modifier
                    .padding(horizontal = dimensionResource(id = R.dimen.spacing_medium)),
                isLoading = formState.isLoading
            )
        }
    }
}

@Preview
@Composable
fun SignInScreenPreview() {
    DroidChatTheme {
        SignInScreen(
            formState = SignInFormState(),
            onFormEvent = {}
        )
    }
}
