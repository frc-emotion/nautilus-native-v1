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

enum UserStateError: Error {
    case signInError(message: String), signOutError(message: String), createAccountError(message: String)
}

@MainActor
class UserStateViewModel: ObservableObject {
    
    @Published var isLoggedIn = false
    @Published var isBusy = false
    let defaults = UserDefaults.standard
    
    func signIn(username: String, password: String) async -> Result<String, UserStateError> {
        isBusy = true
        var errorMsg = ""
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
                let responseJson: String = response.toJSON()
                defaults.set(responseJson, forKey: "User")
                isLoggedIn = true
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
        isBusy = true
        var errorMsg = ""
        do {
            let client = EmotionClient()
            let response = try await client.register(username: username, password: password, email: email, firstName: firstname, lastName: lastname, subteam: subteam, phone: phone, grade: grade, errorCallback: { (errorMsgIn) -> () in
                    errorMsg = errorMsgIn
            })
            
            if let response {
                let responseJson: String = response.toJSON()
                defaults.set(responseJson, forKey: "User")
                isLoggedIn = true
                isBusy = false
                return .success(true)
            } else {
                isBusy = false
                return .failure(.createAccountError(message: errorMsg))
            }
        } catch {
            isBusy = false
            return .failure(.createAccountError(message: "Unknown or uncaught Error when attempting to Create Account"))
        }
    }
    
    func signOut() async -> Result<Bool, UserStateError> {
        isBusy = true
        do {
            defaults.removeObject(forKey: "User")
            isLoggedIn = false
            isBusy = false
            return .success(true)
        } // catch {
//            isBusy = false
//            return .failure(.signOutError)
//        }
    }
    
    func setLoggedOut() {
        isLoggedIn = false
    }
}
