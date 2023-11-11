package features.profile.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import features.profile.ProfileComponent
import utils.AppNavigationType

@Composable
internal fun ProfileContent(
    component: ProfileComponent,
    modifier: Modifier = Modifier,
    appNavigationType: AppNavigationType
) {

    val uriHandler = LocalUriHandler.current

    val isNonTabProfile = component.isBackEnabled
    ProfileScreen(
        isNonTabProfile = isNonTabProfile,
        onLinkClick = {
            uriHandler.openUri(it)
        },
        appNavigationType = appNavigationType,
        onBackPressed = {
            component.onBackClicked()
        },
    )
}

//@Preview
@Composable
internal fun ProfilePreview() {
    ProfileContent(
        component = PreviewProfileComponent(),
        appNavigationType = AppNavigationType.BOTTOM_NAVIGATION
    )
}

internal class PreviewProfileComponent() : ProfileComponent {

    override val isBackEnabled: Boolean = false

    override fun onBackClicked() {}
}
