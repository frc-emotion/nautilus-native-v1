//
//  LoginView.swift
//  iosApp
//
//  Created by Jason Ballinger on 7/12/23.
//  Copyright © 2023 team2658. All rights reserved.
//

import Foundation
import SwiftUI
import shared

struct LoginView: View {
    @EnvironmentObject var vm: UserStateViewModel
    @State var username = ""
    @State var password = ""
    @State var loginErrorMsg = ""
    let client = shared.EmotionClient()
    
    var body: some View {
        NavigationStack {
            VStack {
                Spacer()
                
                Text("Log in")
                    .font(.largeTitle)
                    .fontWeight(.bold)
                
                if (loginErrorMsg != "") {
                    Text("\(loginErrorMsg)")
                        .fontWeight(.bold)
                        .foregroundColor(Color.red)
                        .padding(.bottom, 20)
                        .padding(.top, 1)
                }
                
                TextField("Username", text: $username)
                    .padding(.horizontal)
                    .frame(height: 45.0)
                    .overlay(RoundedRectangle(cornerRadius: 5.0).strokeBorder(Color(UIColor.separator)))
                    .padding(.horizontal)
                    .autocapitalization(.none)
                    .disableAutocorrection(true)
                    .submitLabel(.next)
                
                SecureField("Password", text: $password)
                    .padding(.horizontal)
                    .frame(height: 45.0)
                    .overlay(RoundedRectangle(cornerRadius: 5.0).strokeBorder(Color(UIColor.separator)))
                    .padding(.horizontal)
                    .submitLabel(.done)
                
//                HStack {
//                    Spacer()
//                    NavigationLink {
//                        ForgotPasswordView()
//                    } label: {
//                        Text("Forgot password?")
//                    }
//                }
//                .padding(.horizontal)
//                .padding(.vertical, 10.0)
                
                Button (action: {
                    Task {
                        let result = await vm.signIn(username: username, password: password)
                        switch result {
                        case .success(_):
                            loginErrorMsg = ""
                            // do nothing
                            break
                        case .failure(let error):
                            switch error {
                            case .signInError(let message):
                                loginErrorMsg = message
                                break
                            default:
                                print("Unknown error type")
                            }
                        }
                    }
                }) {
                    HStack {
                        Text("Login")
                            .frame(height: 30.0)
                            .frame(maxWidth: .infinity)
                        // TODO: Only let button be pressed once while waiting for response, progres view
//                        #if !DEBUG
//                        if (vm.isBusy) {
//                            ProgressView()
//                        }
//                        #endif
                    }
                }
                .padding(.horizontal)
                .buttonStyle(.borderedProminent)
                .keyboardShortcut(.defaultAction)
                
                Spacer()
            }
        }
    }
}


struct LoginView_Previews: PreviewProvider {
    static var previews: some View {
        LoginView()
    }
}
