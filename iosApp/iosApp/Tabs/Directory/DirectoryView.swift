//
//  DirectoryView.swift
//  iosApp
//
//  Created by Jason Ballinger on 7/11/23.
//  Copyright © 2023 team2658. All rights reserved.
//

import SwiftUI
import shared

struct DirectoryView: View {
    // MARK: - State Properties
    @State var users: [shared.UserWithoutToken]?
    @State private var popoverShown = false
    @State private var alertBoxShowing = false
    @State private var selectedUser: shared.PartialUser?
    //    @State private var alertMsg = ""
    @EnvironmentObject var env: EnvironmentModel
    // MARK: - Sort by Subteam
    var subteamSortedUsers: [String: [shared.PartialUser]]? {
        if let users = users {
            var sortedUsers = users.sorted { $0.lastname.lowercased() < $1.lastname.lowercased() }
            sortedUsers.sort { $0.accountType.value > $1.accountType.value }
            sortedUsers.sort { $0.subteam?.description() ?? "" < $1.subteam?.description() ?? "" }
            let converted: [PartialUser] = sortedUsers.map { user in
                return PartialUser(_id: user._id, firstname: user.firstname, lastname: user.lastname, username: user.username, email: user.email, subteam: user.subteam, roles: user.roles, accountType: user.accountType, grade: user.grade, phone: user.phone)
            }
            return Dictionary(grouping: converted, by: { $0.subteam?.description() ?? "" })
        } else {
            return nil
        }
    }
    // MARK: - Body
    var body: some View {
        NavigationSplitView {
            mainListView
                .navigationTitle("People")
        } detail: {
            // not changing automatically on iPad
            if (selectedUser != nil) {
                UserView(user: selectedUser!)
                    .navigationTitle("Profile")
                    .navigationBarTitleDisplayMode(.inline)
            } else {
                Text("Please select a User")
            }
        }
        .navigationSplitViewStyle(.balanced)
//        .onChange(of: selectedUser) { _ in
//            print("\(String(describing: selectedUser?.firstname)) \(String(describing: selectedUser?.lastname))")
//        }
        .onAppear() {
            users = env.dh.users.loadAll(onCompleteSync: { res in
                users = res
            })
        }
        .refreshable {
            users = env.dh.users.loadAll(onCompleteSync: { res in
                users = res
            })
        }
    }
    
    private var mainListView: some View {
        if users != nil, let subteamSortedUsers = subteamSortedUsers {
            return AnyView (
                List (selection: $selectedUser) {
                    ForEach(shared.Subteam.entries, id: \.self) { (subteam: shared.Subteam) in
                        if let nextSort = subteamSortedUsers[subteam.description()] {
                            Section(header: Text(subteam.description())) {
                                ForEach(nextSort, id: \.self) { user in
                                    DirectoryBar(user: user)
                                }
                            }
                        }
                    }
                }
            )
        } else {
            return AnyView(ProgressView())
        }
    }
    // MARK: - Previews
    struct DirectoryView_Previews: PreviewProvider {
        static var previews: some View {
            DirectoryView().environmentObject({ () -> EnvironmentModel in
                let env = EnvironmentModel()
                env.user = HelpfulVars().testuser
                return env
            }() )
        }
    }
}
