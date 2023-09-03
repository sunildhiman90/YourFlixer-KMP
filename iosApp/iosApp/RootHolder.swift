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
    var rootComponent: RootComponent
    var lifecycleRegistry: LifecycleRegistry
    init(rootComponent: RootComponent, lifecycleRegistry: LifecycleRegistry) {
        self.rootComponent = rootComponent
        self.lifecycleRegistry = lifecycleRegistry
        LifecycleRegistryExtKt.create(lifecycleRegistry)
        lifecycleRegistry.subscribe(callbacks: LifecycleCallbacksImpl())
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
