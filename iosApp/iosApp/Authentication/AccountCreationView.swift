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
    
    @EnvironmentObject var vm: UserStateViewModel
    @State var firstname = ""
    @State var lastname = ""
    @State var email = ""
    @State var username = ""
    @State var phone = ""
    @State var grade = 0
    @State var password = ""
    @State var passwordConfirm = ""
//    @State var subteam = shared.Subteam.none
    @State var subteamString = "None"
    
//    let realSubteams = [NewSubteam(id: 0, value: shared.Subteam.build, name: "Build"), NewSubteam(id: 1, value: shared.Subteam.design, name: "Design"), NewSubteam(id: 2, value: shared.Subteam.electrical, name: "Electrical"), NewSubteam(id: 3, value: shared.Subteam.software, name: "Software"), NewSubteam(id: 4, value: shared.Subteam.marketing, name: "Marketing")] as [NewSubteam]
    
    let subteams = ["None", "Build", "Design", "Electrical", "Software", "Marketing", "Executive"]
    
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
                
                
                Button (action: {
                    Task {
                        var subteam: shared.Subteam
                        
                        if (password == passwordConfirm){
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
                            
                            // result of call is unused because UserStateViewModel will automatically navigate away once the user account is created.
                            await vm.createAccount(firstname: firstname, lastname: lastname, username: username, email: email, password: password, subteam: subteam, phone: phone, grade: Int32(grade))
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
