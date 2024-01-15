//
//  UserStateViewModel.swift
//  iosApp
//
//  Created by Jason Ballinger on 7/17/23.
//  Copyright © 2023 team2658. All rights reserved.
//

import Foundation
import shared
import SwiftyJSON
import KeychainSwift

enum UserStateError: Error {
    case signInError(message: String), signOutError(message: String), createAccountError(message: String), setUserError(message: String), loadUserError(message: String)
}

@MainActor
class UserStateViewModel: ObservableObject {
    @Published var isBusy = false
    @Published var user: shared.User?
    
    init() {
        let defaults = UserDefaults.standard
        let keychain = KeychainSwift()
        
        guard let jsonUser = defaults.value(forKey: "User") else {
            return
        }
        
        guard let token = keychain.get("userToken") else {
            return
        }
        
        let meow = shared.User.Companion().fromJSON(json: jsonUser as? String)!.toMutable()
        meow.token = token
        
        user = meow.toImmutable()
    }
    
    func signIn(username: String, password: String) async -> Result<String, UserStateError> {
        var errorMsg = ""
        isBusy = true
        do {
            let client = EmotionClient()
            let response = try await client.login(username: username, password: password, errorCallback: { (errorMsgIn) -> () in
                if let errorMsgIn = errorMsgIn.data(using: .utf8, allowLossyConversion: false) {
                    Task {
                        let json = try JSON(data: errorMsgIn)
                        errorMsg = "Error: " + json["message"].stringValue
                    }
                }
            })
            
            if let response {
                let status = await setUser(userIn: response)
                switch (status) {
                case .success(_):
                    break
                case .failure(let error):
                    switch error {
                    case .setUserError(let message):
                        return .failure(.signInError(message: message))
                    default:
                        return .failure(.signInError(message: "Correct credentials, but Unknown error when setting user locally"))
                    }
                }
//                user = response
                isBusy = false
                return .success("Succeeded")
            } else {
                // TODO: Provide error message to user via dialog or screen
                isBusy = false
                return .failure(.signInError(message: errorMsg))
            }
        } catch {
            isBusy = false
            return .failure(.signInError(message: "Unknown or uncaught Error when attempting to Sign In"))
        }
    }

    func createAccount(firstname: String, lastname: String, username: String, email: String, password: String, subteam: shared.Subteam, phone: String, grade: Int32) async -> Result<Bool, UserStateError> {
        var errorMsg = ""
        do {
            let client = EmotionClient()
            let response = try await client.register(username: username, password: password, email: email, firstName: firstname, lastName: lastname, subteam: subteam, phone: phone, grade: grade, errorCallback: { (errorMsgIn) -> () in
                    errorMsg = errorMsgIn
            })
            
            if let response {
                let status = await setUser(userIn: response)
                switch (status) {
                case .success(_):
                    break
                case .failure(let error):
                    switch (error) {
                    case .setUserError(let message):
                        return .failure(.createAccountError(message: message))
                    default:
                        return .failure(.createAccountError(message: "Account created, but Unknown error setting user locally"))
                    }
                }
                return .success(true)
            } else {
                return .failure(.createAccountError(message: errorMsg))
            }
        } catch {
            return .failure(.createAccountError(message: "Unknown or uncaught Error when attempting to Create Account"))
        }
    }
    
    func signOut() async -> Result<Bool, UserStateError> {
        do {
            user = nil
            return .success(true)
        }
//        }  catch {
//            isBusy = false
//            return .failure(.signOutError)
//        }
    }
    
    // stores and sets user
    func setUser(userIn: shared.User) async -> Result<Bool, UserStateError> {
        let defaults = UserDefaults.standard
        let keychain = KeychainSwift()
        
        if (userIn.token != nil) {
            keychain.set(userIn.token!, forKey: "userToken")
        } else {
            return .failure(.setUserError(message: "User does not have a token"))
        }
        
        // tokenless
        let tklUser = userIn.toMutable()
        tklUser.token = nil
        
        // defaults is stored as json, needs to deserialize when setting user
        defaults.set(tklUser.toImmutable().toJSON(), forKey: "User")
        
        user = userIn
        return .success(true)
    }
}
