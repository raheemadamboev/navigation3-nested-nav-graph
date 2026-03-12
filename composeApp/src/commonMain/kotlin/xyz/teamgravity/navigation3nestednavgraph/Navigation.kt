package xyz.teamgravity.navigation3nestednavgraph

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import androidx.savedstate.serialization.SavedStateConfiguration
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic

@Composable
fun Navigation() {
    val rootStack = rememberNavBackStack(
        configuration = SavedStateConfiguration {
            serializersModule = SerializersModule {
                polymorphic(NavKey::class) {
                    subclass(Route.Auth::class, Route.Auth.serializer())
                    subclass(Route.Home::class, Route.Home.serializer())
                }
            }
        },
        Route.Auth
    )
    NavDisplay(
        backStack = rootStack,
        entryDecorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator()
        ),
        entryProvider = entryProvider {
            entry<Route.Auth> {
                val stack = rememberNavBackStack(
                    configuration = SavedStateConfiguration {
                        serializersModule = SerializersModule {
                            polymorphic(NavKey::class) {
                                subclass(Route.Auth.Login::class, Route.Auth.Login.serializer())
                                subclass(Route.Auth.Register::class, Route.Auth.Register.serializer())
                            }
                        }
                    },
                    Route.Auth.Login
                )
                val viewmodel = viewModel { AuthSharedViewModel() }
                NavDisplay(
                    backStack = stack,
                    entryDecorators = listOf(
                        rememberSaveableStateHolderNavEntryDecorator(),
                        rememberViewModelStoreNavEntryDecorator()
                    ),
                    entryProvider = entryProvider {
                        entry<Route.Auth.Login> {
                            LoginScreen(
                                onNavigateRegister = {
                                    stack.add(Route.Auth.Register)
                                },
                                onNavigateHome = {
                                    rootStack.remove(Route.Auth)
                                    rootStack.add(Route.Home)
                                },
                                sharedViewmodel = viewmodel
                            )
                        }
                        entry<Route.Auth.Register> {
                            RegisterScreen(
                                onNavigateHome = {
                                    rootStack.remove(Route.Auth)
                                    rootStack.add(Route.Home)
                                },
                                sharedViewmodel = viewmodel
                            )
                        }
                    }
                )
            }
            entry<Route.Home> {
                HomeScreen()
            }
        }
    )
}