//
//  UserStateViewModel.swift
//  iosApp
//
//  Created by Jason Ballinger on 7/17/23.
//  Copyright © 2023 team2658. All rights reserved.
//

import Foundation
import shared

enum UserStateError: Error {
    case signInError, signOutError, createAccountError
}

@MainActor
class UserStateViewModel: ObservableObject {
    
    @Published var isLoggedIn = false
    @Published var isBusy = false
    
    func signIn(username: String, password: String) async -> Result<Bool, UserStateError> {
        isBusy = true
        do {
            
            print(username)
            print(password)
            
            let client = EmotionClient()
            let response = try await client.login(username: username, password: password)
            
            if (response != nil) {
                print(response?.firstName)
            } else {
                print("no response")
            }
            
//            try await Task.sleep(nanoseconds: 1_000_000_000)
//            isLoggedIn = true
//            isBusy = false
            return .success(false)
        } catch {
            isBusy = false
            return .failure(.signInError)
        }
    }
    
    func createAccount(firstname: String, lastname: String, email: String, password: String) async -> Result<Bool, UserStateError> {
        isBusy = true
        do {
            try await Task.sleep(nanoseconds: 1_000_000_000)
            isLoggedIn = true
            isBusy = false
            return .success(true)
        } catch {
            isBusy = false
            return .failure(.createAccountError)
        }
    }
    
    func signOut() async -> Result<Bool, UserStateError> {
        isBusy = true
        do {
            try await Task.sleep(nanoseconds: 1_000_000_000)
            isLoggedIn = false
            isBusy = false
            return .success(true)
        } catch {
            isBusy = false
            return .failure(.signOutError)
        }
    }
    
    func setLoggedOut() {
        isLoggedIn = false
    }
}
