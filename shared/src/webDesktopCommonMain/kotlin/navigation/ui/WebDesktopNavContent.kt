package navigation.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.PermanentNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.arkivanov.decompose.router.stack.ChildStack
import core.designsystem.component.CommonAppBottomBar
import core.navigation.AppNavigationRail
import core.navigation.ModalNavigationDrawerContent
import core.navigation.PermanentNavigationDrawerContent
import core.navigation.TopLevelDestination
import features.downloads.ui.DownloadsContent
import features.home.ui.HomeContent
import features.itemdetail.ui.ItemDetailContent
import kotlinx.coroutines.launch
import logger.AppLogger
import features.profile.ui.ProfileContent
import root.WebDesktopRootComponent
import features.search.ui.SearchContent
import features.stream.ui.StreamVideoContent
import utils.AppContentType
import utils.AppNavigationContentPosition
import utils.AppNavigationType

//Separate NavContent for web, due to using single navigation stack for maintaining browser history
@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun WebDesktopNavContent(
    component: WebDesktopRootComponent,
    modifier: Modifier,
    appNavigationType: AppNavigationType,
    appContentType: AppContentType,
    lazyListScrollBar: (@Composable (LazyListState, Modifier) -> Unit)? = null,
    lazyGridScrollBar: (@Composable (LazyGridState, Modifier) -> Unit)? = null,
    scrollBar: (@Composable (ScrollState, Modifier) -> Unit)? = null
) {

    val childStack by component.childStack.subscribeAsState()
    val activeComponent = childStack.active.instance

    val topLevelDestinations = TopLevelDestination.values().asList()

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    // if activeDestination is not full screen and also not in topLevelDestinations, then its nested destination, we dont need to change selected tab,
    // we will handle that with RootComponent's lastSelectedTabDestination

    Scaffold(bottomBar = {
        AnimatedVisibility(appNavigationType == AppNavigationType.BOTTOM_NAVIGATION && !activeComponent.isFullscreen) {
            CommonAppBottomBar(
                modifier = Modifier.fillMaxWidth(),
                activeDestination = activeComponent.destination,
                topLevelDestinations = topLevelDestinations,
                lastActiveTabDestination = component.lastSelectedTabDestination,
                navigateToTopLevelDestination = {
                    if(it in topLevelDestinations) {
                        component.lastSelectedTabDestination = it
                    }
                    component.onBottomBarItemClicked(it)
                },
                windowInsetsPadding = WindowInsets(0.dp)
            )
        }
    }) { innerPadding ->

        if (appNavigationType == AppNavigationType.BOTTOM_NAVIGATION) {
            WebDesktopMainContent(
                childStack = childStack,
                modifier = modifier,
                appNavigationType = appNavigationType,
                appContentType = appContentType,
                scrollBar = scrollBar,
                lazyGridScrollBar = lazyGridScrollBar,
                lazyListScrollBar = lazyListScrollBar
            )
        } else if (appNavigationType == AppNavigationType.NAVIGATION_RAIL) {

            ModalNavigationDrawer(
                drawerContent = {
                    ModalNavigationDrawerContent(
                        activeDestination = activeComponent.destination,
                        navigationContentPosition = AppNavigationContentPosition.TOP,
                        topLevelDestinations = topLevelDestinations,
                        navigateToTopLevelDestination = {
                            component.onAppNavigationRailItemClicked(it)
                            scope.launch {
                                drawerState.close()
                            }
                        },
                        onDrawerClicked = {
                            scope.launch {
                                drawerState.close()
                            }
                        }
                    )
                },
                drawerState = drawerState
            ) {
                Row(modifier = modifier.padding(innerPadding)) {

                    AppLogger.d("appNavigationType_MainScreen=$appNavigationType")
                    AppLogger.d("before_navigateToTopLevelDestination=$activeComponent")

                    //TODO, need to move this(show hide navigation rail and same for bottom bar) logic to RootComponent, try to use as much as possible business logic from RootComponent
                    //Navigation Rail
                    AnimatedVisibility(visible = appNavigationType == AppNavigationType.NAVIGATION_RAIL && !activeComponent.isFullscreen) {

                        AppNavigationRail(
                            topLevelDestinations = topLevelDestinations,
                            navigateToTopLevelDestination = component::onAppNavigationRailItemClicked,
                            activeDestination = activeComponent.destination,
                            onDrawerClicked = {
                                scope.launch {
                                    drawerState.open()
                                }
                            }
                        )
                    }

                    WebDesktopMainContent(
                        childStack = childStack,
                        modifier = modifier,
                        appNavigationType = appNavigationType,
                        appContentType = appContentType,
                        scrollBar = scrollBar,
                        lazyGridScrollBar = lazyGridScrollBar,
                        lazyListScrollBar = lazyListScrollBar
                    )

                }
            }

        } else if (appNavigationType == AppNavigationType.PERMANENT_NAVIGATION_DRAWER) {
            Row(modifier = modifier.padding(innerPadding)) {
                // TODO check on custom width of PermanentNavigationDrawer: b/232495216

                AnimatedVisibility(
                    visible = appNavigationType == AppNavigationType.PERMANENT_NAVIGATION_DRAWER && !activeComponent.isFullscreen,
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    PermanentNavigationDrawer(
                        drawerContent = {
                            PermanentNavigationDrawerContent(
                                topLevelDestinations = topLevelDestinations,
                                navigateToTopLevelDestination = component::onAppNavigationRailItemClicked,
                                activeDestination = activeComponent.destination,
                                navigationContentPosition = AppNavigationContentPosition.TOP
                            )
                        }) {
                        WebDesktopMainContent(
                            childStack = childStack,
                            modifier = modifier,
                            appNavigationType = appNavigationType,
                            appContentType = appContentType,
                            scrollBar = scrollBar,
                            lazyGridScrollBar = lazyGridScrollBar,
                            lazyListScrollBar = lazyListScrollBar
                        )

                    }
                }
                AnimatedVisibility(
                    visible = appNavigationType == AppNavigationType.PERMANENT_NAVIGATION_DRAWER && activeComponent.isFullscreen,
                    enter = scaleIn(),
                    exit = scaleOut()
                ) {

                    WebDesktopMainContent(
                        childStack = childStack,
                        modifier = modifier,
                        appNavigationType = appNavigationType,
                        appContentType = appContentType,
                        scrollBar = scrollBar,
                        lazyGridScrollBar = lazyGridScrollBar,
                        lazyListScrollBar = lazyListScrollBar
                    )
                }

            }
        } else WebDesktopMainContent(
            childStack = childStack,
            modifier = modifier,
            appNavigationType = appNavigationType,
            appContentType = appContentType,
            scrollBar = scrollBar,
            lazyGridScrollBar = lazyGridScrollBar,
            lazyListScrollBar = lazyListScrollBar
        )

    }

}

@Composable
fun WebDesktopMainContent(
    childStack: ChildStack<*, WebDesktopRootComponent.RootChild>,
    modifier: Modifier,
    appNavigationType: AppNavigationType,
    appContentType: AppContentType,
    lazyListScrollBar: (@Composable (LazyListState, Modifier) -> Unit)? = null,
    lazyGridScrollBar: (@Composable (LazyGridState, Modifier) -> Unit)? = null,
    scrollBar: (@Composable (ScrollState, Modifier) -> Unit)? = null
) {

    Children(
        stack = childStack,
        // Workaround for https://issuetracker.google.com/issues/270656235
        animation = stackAnimation(fade()),
//            animation = tabAnimation(),
    ) {
        when (val child = it.instance) {

            is WebDesktopRootComponent.RootChild.HomeNavChild -> HomeContent(
                component = child.component,
                modifier = Modifier.fillMaxSize(),
                appNavigationType = appNavigationType,
                scrollBar = scrollBar,
                lazyListScrollBar = lazyListScrollBar,
                lazyGridScrollBar = lazyGridScrollBar,
            )

            is WebDesktopRootComponent.RootChild.SearchNavChild -> SearchContent(
                component = child.component,
                modifier = Modifier.fillMaxSize(),
//                appNavigationType = appNavigationType,
//                scrollBar = lazyGridScrollBar
            )


            is WebDesktopRootComponent.RootChild.DownloadsNavChild -> DownloadsContent(
                component = child.component, modifier = Modifier.fillMaxSize(),
                //appNavigationType = appNavigationType,
                //scrollBar = lazyListScrollBar
            )

            is WebDesktopRootComponent.RootChild.ProfileNavChild -> ProfileContent(
                component = child.component, modifier.fillMaxSize(),
                appNavigationType = appNavigationType,
                //scrollBar = scrollBar
            )


            is WebDesktopRootComponent.RootChild.StreamVideoChild -> StreamVideoContent(
                component = child.component, modifier = Modifier.fillMaxSize()
            )

            is WebDesktopRootComponent.RootChild.ItemDetailChild -> ItemDetailContent(
                component = child.component, modifier = Modifier.fillMaxSize()
            )
        }
    }

}