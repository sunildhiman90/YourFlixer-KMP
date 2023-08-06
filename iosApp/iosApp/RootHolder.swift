//
//  RootHolder.swift
//  iosApp
//
//  Created by Sunil Kumar on 07/08/23.
//  Copyright © 2023 orgName. All rights reserved.
//

// For controlling ios app lifecycle

import Foundation
import shared

class RootHolder {
    let lifecycle: LifecycleRegistry
    let root: RootComponent

    init() {
        lifecycle = LifecycleRegistryKt.LifecycleRegistry()

        root = DefaultRootComponent(
            componentContext: DefaultComponentContext(lifecycle: lifecycle),
            deepLink: DeepLinkNone.shared,
            webHistoryController: nil
        )

        LifecycleRegistryExtKt.create(lifecycle)
        lifecycle.subscribe(callbacks: LifecycleCallbacksImpl())
        
    }
}

class LifecycleCallbacksImpl: LifecycleCallbacks {
    func onCreate() {
#if DEBUG
                debugPrint("Compose RootComponent: onCreate")
#endif
    }
    
    func onDestroy() {
#if DEBUG
                debugPrint("RootComponent: onDestroy")
#endif
    }
    
    func onPause() {
#if DEBUG
                debugPrint("Compose RootComponent: onPause")
#endif
    }
    
    func onResume() {
#if DEBUG
                debugPrint("Compose RootComponent: onResume")
#endif
    }
    
    func onStart() {
#if DEBUG
                debugPrint("Compose RootComponent: onStart")
#endif
    }
    
    func onStop() {
#if DEBUG
                debugPrint("Compose RootComponent: onStop")
#endif
    }
    
}
