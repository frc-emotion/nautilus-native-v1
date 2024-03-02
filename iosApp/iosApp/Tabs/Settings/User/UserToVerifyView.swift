//
//  UserToVerifyView.swift
//  E-Motion
//
//  Created by Jason Ballinger on 3/1/24.
//  Copyright © 2024 team2658. All rights reserved.
//

import SwiftUI
import shared

struct UserToVerifyView: View {
    @State var user: shared.User
    @EnvironmentObject var env: EnvironmentModel
    @State private var showingVerifyOptions = false
    @State private var showingDeleteConfirmation = false
    
    var body: some View {
        ScrollView {
            ProfileView(user: user)
                .padding()
            HStack {
                Button(action: {
                    showingVerifyOptions = true
                }, label: {
                    Text("Verify User")
                        .frame(height: 30)
                        .frame(maxWidth: .infinity)
                })
                .padding(.leading)
                .padding(.bottom)
                .buttonStyle(.borderedProminent)
                .tint(.green)
                .disabled(env.user!.accountType == AccountType.lead && (env.user!.subteam != user.subteam))
                
                Button(role: .destructive, action: {
                    showingDeleteConfirmation = true
                }, label: {
                    Image(systemName: "trash.fill")
                        .frame(height: 30)
                        .frame(maxWidth: 50)
                })
                .padding(.trailing)
                .padding(.bottom)
                .buttonStyle(.borderedProminent)
                .disabled(env.user!.accountType == AccountType.lead && (env.user!.subteam != user.subteam))
            }
            
            if (env.user!.accountType == AccountType.lead && (env.user!.subteam != user.subteam)) {
                Text("You cannot verify this user because they are not a member of your subteam")
                    .padding(.bottom)
                    .fontWeight(.bold)
                    .padding(.horizontal)
                    .foregroundStyle(.red)
            }
            
            Divider()
            VStack {
                HStack {
                    Text("User Information")
                        .font(.title2)
                        .fontWeight(.bold)
                        .padding(.bottom, 5)
                    Spacer()
                }
                HStack {
                    Text("Name:")
                        .fontWeight(.bold)
                    Text("\(user.firstname) \(user.lastname)")
                    Spacer()
                }
                HStack {
                    Text("Username:")
                        .fontWeight(.bold)
                    Text(user.username)
                    Spacer()
                }
                HStack {
                    Text("Email:")
                        .fontWeight(.bold)
                    Text(user.email)
                    Spacer()
                }
                HStack {
                    Text("Phone:")
                        .fontWeight(.bold)
                    Text(user.phone)
                    Spacer()
                }
                HStack {
                    Text("Subteam:")
                        .fontWeight(.bold)
                    Text(user.subteam?.description().capitalized ?? "Null")
                    Spacer()
                }
                HStack {
                    Text("Grade:")
                        .fontWeight(.bold)
                    Text(user.grade?.description ?? "Null")
                    Spacer()
                }
            }
            .padding()
        }
        .confirmationDialog("Verify User", isPresented: $showingVerifyOptions) {
            Button("Verify") {
                // TODO: Verify logic
            }
            if (env.user!.accountType == AccountType.admin) {
                Button("Verify as Lead") {}
                Button("Verify as Admin") {}
                if (env.user!.accountType == AccountType.superuser) {
                    Button("Verify as Superuser") {}
                }
            }
        }
        .confirmationDialog("Delete User", isPresented: $showingDeleteConfirmation) {
            Button("Delete", role: .destructive) {
                // TODO: Delete user logic
            }
        }
    }
}

#Preview {
    UserToVerifyView(user: HelpfulVars().testuser).environmentObject({ () -> EnvironmentModel in
        let env = EnvironmentModel()
        env.user = HelpfulVars().testuser
        return env
    }() )
}
