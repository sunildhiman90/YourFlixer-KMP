package utils

import androidx.compose.runtime.Composable
import com.seiko.imageloader.ImageLoader
import com.seiko.imageloader.component.setupDefaultComponents
import com.seiko.imageloader.intercept.bitmapMemoryCacheConfig
import com.seiko.imageloader.intercept.imageMemoryCacheConfig
import com.seiko.imageloader.intercept.painterMemoryCacheConfig
import okio.FileSystem


@Composable
actual fun generateImageLoader(): ImageLoader {
    return ImageLoader(
        //requestCoroutineContext = rememberCoroutineScope().coroutineContext, //without using this, its crashing on ios https://github.com/qdsfdhvh/compose-imageloader/issues/148
    ) {
        components {
            setupDefaultComponents()
        }
        interceptor {

            // cache 32MB bitmap
            bitmapMemoryCacheConfig {
                maxSize(32 * 1024 * 1024) // 32MB
            }
            // cache 50 image
            imageMemoryCacheConfig {
                maxSize(50)
            }
            // cache 50 painter
            painterMemoryCacheConfig {
                maxSize(50)
            }

            diskCacheConfig {
                directory(FileSystem.SYSTEM_TEMPORARY_DIRECTORY)
                maxSizeBytes(1024 * 1024 * 100)
            }
        }
    }
}