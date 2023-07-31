package androidx.compose.desktop.ui.tooling.preview

// Workaround for supporting preview in commonMain, though currently its not supported in commonMain
// https://kotlinlang.slack.com/archives/C0346LWVBJ4/p1688980953370019
@Retention(AnnotationRetention.SOURCE)
@Target(
    AnnotationTarget.FUNCTION
)
annotation class Preview