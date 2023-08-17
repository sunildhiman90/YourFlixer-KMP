//
//  Koin.swift
//  iosApp
//
//  Created by Sunil Kumar on 15/08/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation
import shared


func startKoin() {

    let koinApplication = DependencyInjectionKt.doInitKoinIOS()
    // _koin is Koin instance
    _koin = koinApplication.koin
}


//koin is a global instance of Koin, use it to get dependencies
private var _koin: Koin_coreKoin? = nil
var koin: Koin_coreKoin {
    return _koin!
}


// Alternative way of using koin get dependency
//extension Koin_coreKoin {
//
//    func get() ->  RootComponent {
//        return koin.getDependency(objCClass: RootComponent.self) as! RootComponent
//    }
//
//    func get() ->  LifecycleRegistry {
//        return koin.getDependency(objCClass: LifecycleRegistry.self) as! LifecycleRegistry
//    }
//}
