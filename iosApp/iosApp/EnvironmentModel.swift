//
//  EnvironmentModel.swift
//  E-Motion
//
//  Created by Jason Ballinger on 1/30/24.
//  Copyright © 2024 team2658. All rights reserved.
//

import Foundation
import Combine
import shared
import SwiftyJSON
import KeychainSwift

@MainActor
class EnvironmentModel: ObservableObject {
    @Published var user: shared.TokenUser?
    @Published var dh: shared.DataHandler
    @Published var errorMessage: String?
    @Published var errorCode: Int?
    private var cancellable: AnyCancellable?
    
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
        
        cancellable = $user.receive(on: DispatchQueue.main).sink { [weak self] newUser in
            self?.objectWillChange.send() // Trigger objectWillChange to notify SwiftUI
        }
    }
    
    func login(username: String, password: String) async throws {
        let response = try await dh.users.login(username: username, password: password) { err in
            return
        }
        user = response
    }
    
//    this is not allowed?
//    func logout() async throws {
//        dh.users.logout()
//        do {
//            try await refreshUser()
//        } catch {}
//    }
    
    func updateUser(newUser: TokenUser?) {
        user = newUser
    }
    
    func refreshUser() async throws {
        updateUser(newUser: try await dh.users.refreshLoggedIn(onError: {e in self.errorMessage = e.message; self.errorCode = e.code?.intValue}))
    }
}
