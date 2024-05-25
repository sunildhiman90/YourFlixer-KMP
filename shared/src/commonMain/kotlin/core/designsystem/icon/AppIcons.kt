package core.designsystem.icon

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.AddBox
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.FileDownload
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.MapsUgc
import androidx.compose.material.icons.outlined.ModeComment
import androidx.compose.material.icons.outlined.Movie
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Send
import androidx.compose.ui.graphics.vector.ImageVector
import org.jetbrains.compose.resources.DrawableResource

object AppIcons {
    // Icons
    val ProfileCircle = Icons.Outlined.AccountCircle
    val ProfileCircleSelected = Icons.Filled.AccountCircle

    val Search = Icons.Outlined.Search
    val SearchSelected = Icons.Filled.Search
    val Reels = Icons.Outlined.Movie
    val ReelsSelected = Icons.Outlined.Movie
    val DownloadIcon = Icons.Outlined.FileDownload
    val DownloadIconSelected = Icons.Filled.FileDownload

    val MoreVert = Icons.Default.MoreVert
    val MenuIcon = Icons.Default.Menu
    val AddPostIcon = Icons.Outlined.AddBox
    val MessengerIcon = Icons.Outlined.MapsUgc
    val LikeIcon = Icons.Outlined.FavoriteBorder
    val SendIcon = Icons.Outlined.Send
    val CommentIcon = Icons.Outlined.ModeComment
    val MarkIcon = Icons.Outlined.BookmarkBorder


    val HomeIcon = Icons.Outlined.Home
    val HomeIconSelected = Icons.Filled.Home

    val Back = Icons.Outlined.ArrowBack


    // Drawables
    //const val CommentIcon = "comment_icon.png" // for ImageResourceIcon type example
    //const val SendIcon = "send_icon.png"
}

/**
 * A sealed class to make dealing with [ImageVector] and [DrawableRes] icons easier.
 */
sealed class AppIcon {
    data class ImageVectorIcon(val imageVector: ImageVector) : AppIcon()
    data class ImageResourceIcon(val id: DrawableResource) : AppIcon()
}
