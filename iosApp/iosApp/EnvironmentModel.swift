//
//  EnvironmentModel.swift
//  E-Motion
//
//  Created by Jason Ballinger on 1/30/24.
//  Copyright © 2024 team2658. All rights reserved.
//

import Foundation
import shared
import SwiftyJSON
import KeychainSwift

@MainActor
class EnvironmentModel: ObservableObject {
    @Published var user: shared.TokenUser?
    @Published var dh: shared.DataHandler
    @Published var errorMessage: String?
    @Published var errorCode: Int?
    
    init() {
        dh = shared.DataHandler(routeBase: "https://staging.team2658.org", databaseDriverFactory: IosDatabaseDriver()) {
            return KeychainSwift().get("userToken")
        } setToken: { newToken in
            if (newToken != nil) {
                KeychainSwift().set(newToken!, forKey: "userToken")
            } else {
                KeychainSwift().delete("userToken")
            }
        }
        
        user = dh.users.loadLoggedIn(onError: {e in
            self.errorMessage = e.message
            self.errorCode = e.code?.intValue
        })
    }
    
    func refreshUser() async throws {
        user = try await dh.users.refreshLoggedIn(onError: {e in self.errorMessage = e.message; self.errorCode = e.code?.intValue})
    }
}
