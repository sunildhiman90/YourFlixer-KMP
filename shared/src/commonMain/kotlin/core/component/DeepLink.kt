package core.component

sealed interface DeepLink {
    object None : DeepLink
    class Web(val path: String) : DeepLink
}