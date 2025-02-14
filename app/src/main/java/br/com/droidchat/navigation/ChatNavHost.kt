package br.com.droidchat.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import br.com.droidchat.navigation.extensions.slideInTo
import br.com.droidchat.navigation.extensions.slideOutTo
import br.com.droidchat.ui.feature.signin.SignInRoute
import br.com.droidchat.ui.feature.signup.SignUpRoute
import br.com.droidchat.ui.feature.splash.SplashRoute
import kotlinx.serialization.Serializable

sealed interface Routes {
    @Serializable object SplashRoute
    @Serializable object SignInRoute
    @Serializable object SignUpRoute
}

@Composable
fun ChatNavHost() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Routes.SplashRoute) {
        composable<Routes.SplashRoute> {
           SplashRoute(
               onNavigateToSignIn = {
                   navController.navigate(
                       route = Routes.SignInRoute,
                       navOptions = navOptions {
                           popUpTo(Routes.SplashRoute) {
                               inclusive = true
                           }
                       }
                   )
               }
           )
        }
        composable<Routes.SignInRoute>(
            enterTransition = {
                this.slideInTo(
                   direction = AnimatedContentTransitionScope.SlideDirection.Right
                )
            },
            exitTransition = {
                this.slideOutTo(
                    direction = AnimatedContentTransitionScope.SlideDirection.Left
                )
            }
        ) {
            SignInRoute(
                navigateToSignUp = {
                    navController.navigate(Routes.SignUpRoute)
                }
            )
        }
        composable<Routes.SignUpRoute> (
            enterTransition = {
                this.slideInTo(
                    direction = AnimatedContentTransitionScope.SlideDirection.Left
                )
            },
            exitTransition = {
                this.slideOutTo(
                    direction = AnimatedContentTransitionScope.SlideDirection.Right
                )
            }
        ) {
            SignUpRoute()
        }
    }
}