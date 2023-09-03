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
                    rootComponent: rootHolder.rootComponent
                ).ignoresSafeArea()
                    .onReceive(NotificationCenter.default.publisher(for: UIApplication.didBecomeActiveNotification)) { _ in
#if DEBUG
                        debugPrint("Swift UIApplication: onResume")
#endif
                        LifecycleRegistryExtKt.resume(rootHolder.lifecycleRegistry)
                    }
                    .onReceive(NotificationCenter.default.publisher(for: UIApplication.willResignActiveNotification)) { _ in
#if DEBUG
                        debugPrint("Swift UIApplication: onPause")
#endif
                        LifecycleRegistryExtKt.pause(rootHolder.lifecycleRegistry)
                    }
                    .onReceive(NotificationCenter.default.publisher(for: UIApplication.didEnterBackgroundNotification)) { _ in
#if DEBUG
                        debugPrint("Swift UIApplication: onStop")
#endif
                        LifecycleRegistryExtKt.stop(rootHolder.lifecycleRegistry)
                    }
                    .onReceive(NotificationCenter.default.publisher(for: UIApplication.willTerminateNotification)) { _ in
#if DEBUG
                        debugPrint("Swift UIApplication: onDestroy")
#endif
                        LifecycleRegistryExtKt.destroy(rootHolder.lifecycleRegistry)
                        rootHolder.lifecycleRegistry.unsubscribe(callbacks: LifecycleCallbacksImpl())
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
    
    func application(
        _ application: UIApplication,
        didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]?
    ) -> Bool {
        
        // start koin before RootHolder, it will initialize rootComponent and lifecycleRegistry
        
        startKoin()
        rootHolder = RootHolder(rootComponent: koin.rootComponent,  lifecycleRegistry: koin.lifecycleRegistry)

        return true
    }

    func application(_ application: UIApplication, shouldSaveSecureApplicationState coder: NSCoder) -> Bool {
        return true
    }

    func application(_ application: UIApplication, shouldRestoreSecureApplicationState coder: NSCoder) -> Bool {
        do {
            
            //startKoin()
            
            //rootHolder = RootHolder(rootComponent: koin.rootComponent,  lifecycle: koin.lifecycleRegistry)
            return true
        }
    }

    fileprivate func getRootHolder() -> RootHolder {
        if (rootHolder == nil) {
            rootHolder = RootHolder(rootComponent: koin.rootComponent,  lifecycleRegistry: koin.lifecycleRegistry)
        }
        return rootHolder!
    }
}
