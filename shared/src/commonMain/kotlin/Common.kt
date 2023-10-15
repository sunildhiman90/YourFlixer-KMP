import org.koin.core.Koin
import org.koin.core.parameter.parametersOf
import kotlin.reflect.KClass

expect fun getPlatformName(): String

@Suppress("UNCHECKED_CAST")
fun <T> Koin.getDependency(clazz: KClass<*>): T {
    return get(clazz, null) { parametersOf(clazz.simpleName) } as T
}

object AppConstants {
    const val TMDB_API_ACCESS_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJkMmQ1MmFmMDJkNjc4NmExZTE0MGI2MDZjMTk4MDgxYSIsInN1YiI6IjYxMDhlNGIzMmY4ZDA5MDA0OGU5YzdjNCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.mQWYD4zw2Nhiuwmyayn0UvZeuu_AGRzpom8g1X06YZs"
    const val BASE_URL = "https://api.themoviedb.org/3/"
    const val IMAGES_BASE_URL = "https://image.tmdb.org/t/p/"
    const val IMAGES_SIZE_SUFFIX = "original/"
}
