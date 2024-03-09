//
//  VerifyUsersView.swift
//  E-Motion
//
//  Created by Jason Ballinger on 1/10/24.
//  Copyright © 2024 team2658. All rights reserved.
//

import SwiftUI
import shared

struct VerifyUsersView: View {
    @State var users: [shared.UserWithoutToken]?
    @Binding var presented: Bool
    @State private var selectedUser: shared.PartialUser?
    @EnvironmentObject var env: EnvironmentModel
    
    var usersConverted: [shared.PartialUser]? {
        if let users = users {
            let new = users.map { user in
                return PartialUser(_id: user._id, firstname: user.firstname, lastname: user.lastname, username: user.username, email: user.email, subteam: user.subteam, roles: user.roles, accountType: user.accountType, grade: user.grade, phone: user.phone)
            }
            return new.filter { user in
                if (user.accountType == AccountType.unverified) { return true }
                else { return false }
            }
        } else {
            return nil
        }
    }
    
    var body: some View {
        NavigationSplitView {
            VStack {
                if (usersConverted != nil) {
                    List (selection: $selectedUser) {
                        ForEach(usersConverted!, id: \.self) { user in
                            DirectoryBar(user: user)
                        }
                    }
                } else {
                    Text("No users to verify")
                }
            }
            .navigationTitle("Verify Users")
            .navigationBarTitleDisplayMode(.inline)
            .toolbar {
                ToolbarItem(placement: .topBarTrailing) {
                    Button {
                        presented = false
                    } label: {
                        Text("Done")
                    }
                }
            }
        } detail: {
            if selectedUser != nil {
                UserToVerifyView(user: selectedUser!)
                    .environmentObject(env)
            } else {
                Text("Select a user")
            }
        }
    }
}

#Preview {
    VerifyUsersView(presented: .constant(true)).environmentObject({ () -> EnvironmentModel in
        let env = EnvironmentModel()
        env.user = HelpfulVars().testuser
        return env
    }() )
}
