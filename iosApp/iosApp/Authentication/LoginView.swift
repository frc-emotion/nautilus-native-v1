//
//  LoginView.swift
//  iosApp
//
//  Created by Jason Ballinger on 7/12/23.
//  Copyright © 2023 team2658. All rights reserved.
//

import Foundation
import SwiftUI

struct LoginView: View {
    
    @EnvironmentObject var vm: UserStateViewModel
    @State var username = ""
    @State var password = ""
    
    var body: some View {
        NavigationView {
            VStack {
                Spacer()
                
                Text("Log in")
                    .font(.largeTitle)
                    .fontWeight(.bold)
                
                TextField("Username", text: $username)
                    .padding(.horizontal)
                    .frame(height: 45.0)
                    .overlay(RoundedRectangle(cornerRadius: 5.0).strokeBorder(Color(UIColor.separator)))
                    .padding(.horizontal)
                    .autocapitalization(.none)
                    .disableAutocorrection(true)
                
                SecureField("Password", text: $password)
                    .padding(.horizontal)
                    .frame(height: 45.0)
                    .overlay(RoundedRectangle(cornerRadius: 5.0).strokeBorder(Color(UIColor.separator)))
                    .padding(.horizontal)
                
                HStack {
                    Spacer()
                    NavigationLink {
                        ForgotPasswordView()
                    } label: {
                        Text("Forgot password?")
                    }
                }
                .padding(.horizontal)
                .padding(.vertical, 10.0)
                
                Button (action: {
                    Task {
                        await vm.signIn(username: username, password: password)
                    }
                }) {
                    Text("Login")
                        .frame(height: 30.0)
                        .frame(maxWidth: .infinity)
                }
                .padding(.horizontal)
                .buttonStyle(.borderedProminent)
                
                
                Spacer()
                Divider()
                
                HStack {
                    Text("Don't have an account?")
                    NavigationLink {
                        AccountCreationView()
                    } label: {
                        Text("Create one.")
                    }
                }
                .padding(.top)
            }
        }
    }
}


struct LoginView_Previews: PreviewProvider {
    static var previews: some View {
        LoginView()
    }
}
