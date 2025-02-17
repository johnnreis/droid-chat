package br.com.droidchat.ui.feature.signup

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.rememberNestedScrollInteropConnection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import br.com.droidchat.R
import br.com.droidchat.ui.components.PrimaryButton
import br.com.droidchat.ui.components.ProfilePictureOptionsModalBottomSheet
import br.com.droidchat.ui.components.ProfilePictureSelector
import br.com.droidchat.ui.components.SecondaryTextField
import br.com.droidchat.ui.theme.BackgroundGradient
import br.com.droidchat.ui.theme.DroidChatTheme
import kotlinx.coroutines.launch

@Composable
fun SignUpRoute(
    viewModel: SignUpViewModel = viewModel()
) {
    val formState = viewModel.formState
    SignUpScreen(
        formState = formState,
        onFormEvent = viewModel::onFormEvent
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(
    formState: SignUpFormState,
    onFormEvent: (SignUpFormEvent) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = BackgroundGradient)

    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .nestedScroll(rememberNestedScrollInteropConnection())
                .imePadding()
                .windowInsetsPadding(WindowInsets.systemBars)
            ,

            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(56.dp))

            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = null
            )
            Spacer(modifier = Modifier.height(16.dp))

            Surface(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState()),

                shape = MaterialTheme.shapes.extraLarge.copy(
                    bottomStart = CornerSize(0.dp),
                    bottomEnd = CornerSize(0.dp)
                ),
                color = MaterialTheme.colorScheme.surface
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    ProfilePictureSelector(
                        imageUri = formState.profilePictureUri,
                        modifier = Modifier.clickable {
                            onFormEvent(
                                SignUpFormEvent.OpenProfilePictureOptionsModalBottomSheet
                            )
                        }
                    )

                    Spacer(modifier = Modifier.height(36.dp))
                    SecondaryTextField(
                        label = stringResource(id = R.string.feature_sign_up_first_name),
                        value = formState.firstName,
                        onValueChange = {
                            onFormEvent(SignUpFormEvent.FirstNameChanged(it))
                        }
                    )

                    Spacer(modifier = Modifier.height(22.dp))
                    SecondaryTextField(
                        label = stringResource(id = R.string.feature_sign_up_last_name),
                        value = formState.lastName,
                        onValueChange = { onFormEvent(SignUpFormEvent.LastNameChanged(it)) }
                    )

                    Spacer(modifier = Modifier.height(22.dp))
                    SecondaryTextField(
                        label = stringResource(id = R.string.feature_sign_up_email),
                        value = formState.email,
                        onValueChange = { onFormEvent(SignUpFormEvent.EmailChanged(it)) },
                        keyboardType = KeyboardType.Email
                    )

                    Spacer(modifier = Modifier.height(22.dp))

                    val extraTextStringResId = if (
                        formState.password.isNotEmpty()
                        && formState.password == formState.passwordConfirmation
                    ) {
                        R.string.feature_sign_up_passwords_match
                    } else null

                    SecondaryTextField(
                        label = stringResource(id = R.string.feature_sign_up_password),
                        value = formState.password,
                        onValueChange = { onFormEvent(SignUpFormEvent.PasswordChanged(it)) },
                        keyboardType = KeyboardType.Password,
                        extraText = extraTextStringResId?.let { stringResource(id = it) }
                    )

                    Spacer(modifier = Modifier.height(22.dp))
                    SecondaryTextField(
                        label = stringResource(id = R.string.feature_sign_up_password_confirmation),
                        value = formState.passwordConfirmation,
                        onValueChange = { onFormEvent(SignUpFormEvent.PasswordConfirmationChanged(it)) },
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done,
                        extraText = extraTextStringResId?.let { stringResource(id = it) }
                    )

                    Spacer(modifier = Modifier.height(36.dp))
                    PrimaryButton(
                        text = stringResource(R.string.feature_sign_up_button),
                        onClick = { onFormEvent(SignUpFormEvent.Submit) }
                    )

                }
            }
            val sheetState = rememberModalBottomSheetState()
            val scope = rememberCoroutineScope()

            if (formState.isProfilePictureModalBottomSheetOpen) {
                ProfilePictureOptionsModalBottomSheet(
                    onPictureSelected = { uri ->
                        onFormEvent(SignUpFormEvent.ProfilePhotoUriChanged(uri))
                        scope.launch {
                            sheetState.hide()
                        }.invokeOnCompletion {
                            if (!sheetState.isVisible) {
                                onFormEvent(SignUpFormEvent.ClosedProfilePictureOptionsModalBottomSheet)
                            }
                        }
                    },
                    onDismissRequest = {
                        onFormEvent(SignUpFormEvent.ClosedProfilePictureOptionsModalBottomSheet)
                    },
                    sheetState = sheetState,

                    )
            }
        }

    }
}


@Preview
@Composable
private fun SignUpScreenPreview() {
    DroidChatTheme {
        SignUpScreen(
            formState = SignUpFormState(),
            onFormEvent = {}
        )
    }
}