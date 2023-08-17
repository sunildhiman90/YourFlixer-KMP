package utils

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class AppDispatchers {
    val Main: CoroutineDispatcher = Dispatchers.Main.immediate
    val Default: CoroutineDispatcher = Dispatchers.Default
    val IO: CoroutineDispatcher = Dispatchers.Default
}