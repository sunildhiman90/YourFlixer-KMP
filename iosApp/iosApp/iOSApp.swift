import SwiftUI
import shared

@main
struct iOSApp: App {

    @UIApplicationDelegateAdaptor(AppDelegate.self)
    var appDelegate: AppDelegate

    private var rootHolder: RootHolder { appDelegate.getRootHolder() }

    var body: some Scene {
        WindowGroup {
            GeometryReader { geo in
                ComposeViewControllerToSwiftUI(
                    topSafeArea: Float(geo.safeAreaInsets.top),
                    bottomSafeArea: Float(geo.safeAreaInsets.bottom),
                    rootComponent: rootHolder.root
                ).ignoresSafeArea()
                    .onReceive(NotificationCenter.default.publisher(for: UIApplication.didBecomeActiveNotification)) { _ in
#if DEBUG
                        debugPrint("Swift UIApplication: onResume")
#endif
                        LifecycleRegistryExtKt.resume(rootHolder.lifecycle)
                    }
                    .onReceive(NotificationCenter.default.publisher(for: UIApplication.willResignActiveNotification)) { _ in
#if DEBUG
                        debugPrint("Swift UIApplication: onPause")
#endif
                        LifecycleRegistryExtKt.pause(rootHolder.lifecycle)
                    }
                    .onReceive(NotificationCenter.default.publisher(for: UIApplication.didEnterBackgroundNotification)) { _ in
#if DEBUG
                        debugPrint("Swift UIApplication: onStop")
#endif
                        LifecycleRegistryExtKt.stop(rootHolder.lifecycle)
                    }
                    .onReceive(NotificationCenter.default.publisher(for: UIApplication.willTerminateNotification)) { _ in
#if DEBUG
                        debugPrint("Swift UIApplication: onDestroy")
#endif
                        LifecycleRegistryExtKt.destroy(rootHolder.lifecycle)
                        rootHolder.lifecycle.unsubscribe(callbacks: LifecycleCallbacksImpl())
                    }
            }
        }
    }
}

struct ComposeViewControllerToSwiftUI: UIViewControllerRepresentable {
    private let topSafeArea: Float
    private let bottomSafeArea: Float
    private let rootComponent: RootComponent

    init(topSafeArea: Float, bottomSafeArea: Float, rootComponent: RootComponent) {
        self.topSafeArea = topSafeArea
        self.bottomSafeArea = bottomSafeArea
        self.rootComponent = rootComponent
    }

    func makeUIViewController(context: Context) -> UIViewController {
        return Main_iosKt.MainViewController(
            topSafeArea: topSafeArea,
            bottomSafeArea: bottomSafeArea,
            root: rootComponent
        )
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {
    }
}


class AppDelegate: NSObject, UIApplicationDelegate {
    private var rootHolder: RootHolder?

    func application(_ application: UIApplication, shouldSaveSecureApplicationState coder: NSCoder) -> Bool {
        return true
    }

    func application(_ application: UIApplication, shouldRestoreSecureApplicationState coder: NSCoder) -> Bool {
        do {
            rootHolder = RootHolder()
            return true
        }
    }

    fileprivate func getRootHolder() -> RootHolder {
        if (rootHolder == nil) {
            rootHolder = RootHolder()
        }
        return rootHolder!
    }
}