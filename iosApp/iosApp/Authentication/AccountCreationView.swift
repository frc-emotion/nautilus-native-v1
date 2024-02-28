//
//  AccountCreationView.swift
//  iosApp
//
//  Created by Jason Ballinger on 7/12/23.
//  Copyright © 2023 team2658. All rights reserved.
//

import SwiftUI
import shared

//struct NewSubteam: Hashable, Identifiable {
//    static func == (lhs: NewSubteam, rhs: NewSubteam) -> Bool {
//        return lhs.value == rhs.value && lhs.name == rhs.name && lhs.id == rhs.id
//    }
//    
//    var id: Int
//    var value: shared.Subteam
//    var name: String
//}

struct AccountCreationView: View {
    @EnvironmentObject var env: EnvironmentModel
    @State private var firstname = ""
    @State private var lastname = ""
    @State private var email = ""
    @State private var username = ""
    @State private var phone = ""
    @State private var grade = ""
    @State private var password = ""
    @State private var passwordConfirm = ""
//    @State var subteam = shared.Subteam.none
    @State private var subteamString = "None"
    @State private var errorMsg = ""
    @State private var isBusy = false
    @FocusState private var numpadFocused: Bool
    
//    let realSubteams = [NewSubteam(id: 0, value: shared.Subteam.build, name: "Build"), NewSubteam(id: 1, value: shared.Subteam.design, name: "Design"), NewSubteam(id: 2, value: shared.Subteam.electrical, name: "Electrical"), NewSubteam(id: 3, value: shared.Subteam.software, name: "Software"), NewSubteam(id: 4, value: shared.Subteam.marketing, name: "Marketing")] as [NewSubteam]
    
    let subteams = ["None", "Build", "Design", "Electrical", "Software", "Marketing", "Executive"]
    
    var body: some View {
        ScrollView {
            VStack {
                Text("Create Account")
                    .font(.largeTitle)
                    .fontWeight(.bold)
                    .padding(.top, 80)
                
                if (errorMsg != "") {
                    Text("\(errorMsg)")
                        .fontWeight(.bold)
                        .foregroundColor(Color.red)
                        .padding(.bottom, 20)
                        .padding(.top, 1)
                }
                
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
                
                HStack {
                    TextField("Username", text: $username)
                        .padding(.horizontal)
                        .frame(height: 45.0)
                        .overlay(RoundedRectangle(cornerRadius: 5.0).strokeBorder(Color(UIColor.separator)))
                        .padding(.leading)
                        .autocapitalization(.none)
                        .disableAutocorrection(true)
                    
                    TextField("Phone", text: $phone)
                        .padding(.horizontal)
                        .frame(height: 45.0)
                        .overlay(RoundedRectangle(cornerRadius: 5.0).strokeBorder(Color(UIColor.separator)))
                        .padding(.trailing)
                        .autocapitalization(.none)
                        .disableAutocorrection(true)
                        .keyboardType(.phonePad)
                        .focused($numpadFocused)
                }
                
                TextField("Email", text: $email)
                    .padding(.horizontal)
                    .frame(height: 45.0)
                    .overlay(RoundedRectangle(cornerRadius: 5.0).strokeBorder(Color(UIColor.separator)))
                    .padding(.horizontal)
                    .autocapitalization(.none)
                    .disableAutocorrection(true)
                    .keyboardType(.emailAddress)
                
                SecureField("Password", text: $password)
                    .padding(.horizontal)
                    .frame(height: 45.0)
                    .overlay(RoundedRectangle(cornerRadius: 5.0).strokeBorder(Color(UIColor.separator)))
                    .padding(.horizontal)
                    .textContentType(.newPassword)
                    
                SecureField("Confirm Password", text: $passwordConfirm)
                    .padding(.horizontal)
                    .frame(height: 45.0)
                    .overlay(RoundedRectangle(cornerRadius: 5.0).strokeBorder(Color(UIColor.separator)))
                    .padding(.horizontal)
                    .textContentType(.newPassword)
                
                HStack {
                    Text("Subteam: ")
                    Spacer()
                    Picker("Appearance", selection: $subteamString) {
                        ForEach(subteams, id: \.self) {subteam in
                            Text(subteam)
                        }
                    }
                    .pickerStyle(.menu)
                }
                .padding(.horizontal)
                .frame(height: 45.0)
                .overlay(RoundedRectangle(cornerRadius: 5.0).strokeBorder(Color(UIColor.separator)))
                .padding(.horizontal)
                
                TextField("Grade", text: $grade)
                    .padding(.horizontal)
                    .frame(height: 45.0)
                    .overlay(RoundedRectangle(cornerRadius: 5.0).strokeBorder(Color(UIColor.separator)))
                    .padding(.horizontal)
                    .autocapitalization(.none)
                    .disableAutocorrection(true)
                    .keyboardType(.phonePad)
                    .focused($numpadFocused)
                
                Button (action: {
                    Task {
                        var subteam: shared.Subteam
                        guard firstname != "" && lastname != "" && email != "" && username != "" && phone != "" && grade != "" && password != "" && passwordConfirm != "" else {
                            errorMsg = "Please fill in all fields"
                            return
                        }
                        if (password == passwordConfirm){
                            isBusy = true
                            
                            if (Int(grade) ?? 0 > 12 || Int(grade) ?? 0 < 9) {
                                errorMsg = "Enter a valid grade 9-12"
                                isBusy = false
                                return
                            }
                            
                            switch subteamString {
                            case "Build":
                                subteam = shared.Subteam.build
                                break
                            case "Design":
                                subteam = shared.Subteam.design
                                break
                            case "Electrical":
                                subteam = shared.Subteam.electrical
                                break
                            case "Software":
                                subteam = shared.Subteam.software
                                break
                            case "Marketing":
                                subteam = shared.Subteam.marketing
                                break
                            case "Executive":
                                subteam = shared.Subteam.executive
                                break
                            default:
                                subteam = shared.Subteam.none
                                break
                            }
                            
                            let response = try await env.dh.users.register(username: username, password: password, email: email, firstName: firstname, lastName: lastname, subteam: subteam, phone: phone, grade: Int32(grade) ?? 0) { err in
                                errorMsg = err.message
                                isBusy = false
                                return
                            }
                            
                            guard response != nil else {
                                errorMsg = "Unknown Error"
                                isBusy = false
                                return
                            }
                            
                            env.updateUser(newUser: response)
                            isBusy = false
                        } else {
                            errorMsg = "Passwords do not match"
                        }
                    }
                }) {
                    if (isBusy) {
                        AnyView(ProgressView())
                            .frame(height: 30.0)
                            .frame(maxWidth: .infinity)
                    } else {
                        Text("Create Account")
                            .frame(height: 30.0)
                            .frame(maxWidth: .infinity)
                    }
                }
                .padding(.horizontal)
                .padding(.top, 15)
                .buttonStyle(.borderedProminent)
                .tint(isBusy ? Color.secondary : Color.accentColor)
            }
            .submitLabel(.done)
            .onTapGesture {
                numpadFocused = false
            }
        }
    }
}

struct AccountCreationView_Previews: PreviewProvider {
    static var previews: some View {
        AccountCreationView()
    }
}
