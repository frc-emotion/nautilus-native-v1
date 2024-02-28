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
    @EnvironmentObject var env: EnvironmentModel
    @State private var username = ""
    @State private var password = ""
    @State private var loginErrorMsg = ""
    @State private var isBusy = false
    
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
                    isBusy = true
                    guard username != "" && password != "" else {
                        loginErrorMsg = "Please fill in all fields"
                        isBusy = false
                        return
                    }
                    Task {
                        loginErrorMsg = ""
                        let response = try await env.dh.users.login(username: username, password: password) { err in
                            loginErrorMsg = err.message
                        }
                        guard response != nil else {
                            if (loginErrorMsg == "") {
                                loginErrorMsg = "Unknown Error"
                            }
                            isBusy = false
                            return
                        }
                        env.updateUser(newUser: response)
                        isBusy = false
                    }
                }) {
                    HStack {
                        if (isBusy) {
                            AnyView(ProgressView())
                                .frame(height: 30.0)
                                .frame(maxWidth: .infinity)
                        } else {
                            Text("Login")
                                .frame(height: 30.0)
                                .frame(maxWidth: .infinity)
                        }
                    }
                }
                .padding(.horizontal)
                .buttonStyle(.borderedProminent)
                .keyboardShortcut(.defaultAction)
                .tint(isBusy ? Color.secondary : Color.accentColor)
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
