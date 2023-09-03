import org.koin.core.Koin
import org.koin.core.parameter.parametersOf
import kotlin.reflect.KClass

expect fun getPlatformName(): String

@Suppress("UNCHECKED_CAST")
fun <T> Koin.getDependency(clazz: KClass<*>): T {
    return get(clazz, null) { parametersOf(clazz.simpleName) } as T
}