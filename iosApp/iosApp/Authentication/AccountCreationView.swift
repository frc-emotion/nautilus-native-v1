//
//  AccountCreationView.swift
//  iosApp
//
//  Created by Jason Ballinger on 7/12/23.
//  Copyright © 2023 team2658. All rights reserved.
//

import SwiftUI

struct AccountCreationView: View {
    
    @EnvironmentObject var vm: UserStateViewModel
    @State var firstname = ""
    @State var lastname = ""
    @State var email = ""
    @State var username = ""
    @State var password = ""
    @State var passwordConfirm = ""
    
    var body: some View {
        NavigationView {
            VStack {
                Text("Create Account")
                    .font(.largeTitle)
                    .fontWeight(.bold)
                
                HStack {
                    TextField("First name", text: $firstname)
                        .padding(.horizontal)
                        .frame(height: 45.0)
                        .overlay(RoundedRectangle(cornerRadius: 5.0).strokeBorder(Color(UIColor.separator)))
                        .padding(.leading)
                        .autocapitalization(.none)
                        .disableAutocorrection(true)
                    
                    TextField("Last name", text: $lastname)
                        .padding(.horizontal)
                        .frame(height: 45.0)
                        .overlay(RoundedRectangle(cornerRadius: 5.0).strokeBorder(Color(UIColor.separator)))
                        .padding(.trailing)
                        .autocapitalization(.none)
                        .disableAutocorrection(true)
                }
                
                TextField("Email", text: $email)
                    .padding(.horizontal)
                    .frame(height: 45.0)
                    .overlay(RoundedRectangle(cornerRadius: 5.0).strokeBorder(Color(UIColor.separator)))
                    .padding(.horizontal)
                    .autocapitalization(.none)
                    .disableAutocorrection(true)
                
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
                
                SecureField("Confirm Password", text: $passwordConfirm)
                    .padding(.horizontal)
                    .frame(height: 45.0)
                    .overlay(RoundedRectangle(cornerRadius: 5.0).strokeBorder(Color(UIColor.separator)))
                    .padding(.horizontal)
                
                Button (action: {
                    Task {
                        if (password == passwordConfirm){
                            await vm.createAccount(firstname: firstname, lastname: lastname, email: email, password: password)
                        } else {
                            // error popup
                        }
                    }
                }) {
                    Text("Create Account")
                        .frame(height: 30.0)
                        .frame(maxWidth: .infinity)
                }
                .padding(.horizontal)
                .padding(.top, 15)
                .buttonStyle(.borderedProminent)
            }
        }
    }
}

struct AccountCreationView_Previews: PreviewProvider {
    static var previews: some View {
        AccountCreationView()
    }
}
