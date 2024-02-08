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
    @Published var user: shared.User?
    @Published var dh: shared.DataHandler
    
    init() {
        dh = shared.DataHandler(databaseDriverFactory: IosDatabaseDriver()) {
            return KeychainSwift().get("userToken")
        } setToken: { newToken in
            if (newToken != nil) {
                KeychainSwift().set(newToken!, forKey: "userToken")
            } else {
                KeychainSwift().delete("userToken")
            }
        }
        
        user = dh.users.loadLoggedIn()
    }
    
    func refreshUser() async throws -> String {
        guard let newUser = try await dh.users.refreshLoggedIn() else {
            return ""
        }
        user = newUser
    }
}
