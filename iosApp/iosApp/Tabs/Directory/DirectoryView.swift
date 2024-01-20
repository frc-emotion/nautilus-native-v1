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
    @State var users: [shared.User]?
    @State private var popoverShown = false
    @State private var errorLoadingUsers = false
    @State private var alertBoxShowing = false
    @State private var selectedUser: shared.User?
    //    @State private var alertMsg = ""
    @EnvironmentObject var vm: UserStateViewModel
    // MARK: - Sort Users by Subteam
    //    var subteamSortedUsers: [shared.User]? {
    //        if (users != nil) {
    //            return users!.sorted { $0.subteam.description() < $1.subteam.description()}
    //        } else {
    //            return nil
    //        }
    //    }
    
    var subteamSortedUsers: [String: [shared.User]]? {
        if let users = users {
            let sortedUsers = users.sorted { $0.subteam.description() < $1.subteam.description() }
            let groupedBySubteam = Dictionary(grouping: sortedUsers, by: { $0.subteam.description() })
            return groupedBySubteam
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
            if (selectedUser != nil) {
                UserView(user: selectedUser!)
            } else {
                Text("Please select a User")
            }
        }
        .navigationSplitViewStyle(.balanced)
        .onAppear() {
            Task {
                guard let response = try await shared.EmotionClient().getUsers(user: vm.user!) else {
                    errorLoadingUsers = true
                    return
                }
                errorLoadingUsers = false
                users = response
            }
        }
    }
    
    private var mainListView: some View {
        //        if (!errorLoadingUsers && users != nil) {
        //            return List(subteamSortedUsers!, id: \.self, selection: $selectedUser) { (user: shared.User) in
        //                ForEach(shared.Subteam.entries, id: \.self) { (subteam: shared.Subteam) in
        //                    Section(header: Text(subteam.description())) {
        //                        let nextSort = subteamSortedUsers[subteam.description()]
        //                        ForEach(nextSort) { user in
        //                            DirectoryBar(user: user)
        //                        }
        //                    }
        //                }
        //            }
        //        }
        if !errorLoadingUsers, users != nil, let subteamSortedUsers = subteamSortedUsers {
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
            DirectoryView().environmentObject({ () -> UserStateViewModel in
                let vm = UserStateViewModel()
                vm.user = HelpfulVars().testuser
                return vm
            }() )
        }
    }
}
