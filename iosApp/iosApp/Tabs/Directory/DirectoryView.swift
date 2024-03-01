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
    @State private var verifyUsersPopoverShown = false
    @State private var alertBoxShowing = false
    @State private var selectedUser: shared.PartialUser?
    @State private var searchText = ""
    //    @State private var alertMsg = ""
    @EnvironmentObject var env: EnvironmentModel
    let customSubteamOrder: [shared.Subteam] = [.executive, .build, .design, .electrical, .software, .marketing, .none]
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
    // MARK: - Filter Users
    var filteredUsers: [(String, [shared.PartialUser])]? {
        guard let subteamSortedUsers = subteamSortedUsers else { return nil }
        
        // Filter and sort subteams based on custom order
        let filteredSubteams = customSubteamOrder.compactMap { subteam -> (String, [shared.PartialUser])? in
            guard let users = subteamSortedUsers[subteam.description()] else { return nil }
            let filteredUsers = users.filter { user -> Bool in
                let fullName = (user.firstname + " " + user.lastname).lowercased()
                return searchText.isEmpty || fullName.contains(searchText.lowercased())
            }
            return filteredUsers.isEmpty ? nil : (subteam.description(), filteredUsers)
        }
        
        return filteredSubteams
    }
    // MARK: - Body
    var body: some View {
        NavigationSplitView {
            VStack {
                if users != nil, let subteamSortedUsers = subteamSortedUsers {
                    List (selection: $selectedUser) {
                        ForEach(filteredUsers ?? [], id: \.0) { subteamDescription, users in
                            if !users.isEmpty {
                                Section(header: Text(subteamDescription)) {
                                    ForEach(users, id: \.self) { user in
                                        DirectoryBar(user: user)
                                    }
                                }
                            }
                        }
                    }
                } else {
                    AnyView(ProgressView())
                }
            }
            .navigationTitle("People")
            .navigationBarTitleDisplayMode(.large)
            .toolbar {
                if (env.user!.accountType == AccountType.lead || env.user!.accountType == AccountType.admin || env.user!.accountType == AccountType.superuser) {
                    ToolbarItem(placement: .topBarTrailing) {
                        Button {
                            verifyUsersPopoverShown = true
                        } label: {
                            Image(systemName: "person.crop.circle.badge.questionmark.fill")
                        }
                    }
                }
            }
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
        .searchable(text: $searchText)
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
        .popover(isPresented: $verifyUsersPopoverShown) {
            VerifyUsersView(users: users, presented: $verifyUsersPopoverShown)
                .environmentObject(env)
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
