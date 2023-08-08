package utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import com.seiko.imageloader.ImageLoader
import com.seiko.imageloader.cache.memory.maxSizePercent
import com.seiko.imageloader.component.setupDefaultComponents
import okio.FileSystem


@Composable
actual fun generateImageLoader(): ImageLoader {
    return ImageLoader(
        requestCoroutineContext = rememberCoroutineScope().coroutineContext, //without using this, its crashing on ios https://github.com/qdsfdhvh/compose-imageloader/issues/148
    ) {
        components {
            setupDefaultComponents(imageScope)
        }
        interceptor {

            memoryCacheConfig {
                maxSizePercent(0.55)
            }

            diskCacheConfig {
                directory(FileSystem.SYSTEM_TEMPORARY_DIRECTORY)
                maxSizeBytes(1024 * 1024 * 100)
            }
        }
    }
}