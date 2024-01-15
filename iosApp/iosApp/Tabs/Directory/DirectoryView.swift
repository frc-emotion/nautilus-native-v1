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
    @State var user: shared.User
    @State var users: [shared.User]?
    @State private var popoverShown = false
    @State private var errorLoadingUsers = false
    @State private var alertBoxShowing = false
//    @State private var alertMsg = ""
    var body: some View {
        NavigationView {
            let subteams = [shared.Subteam.executive, shared.Subteam.build, shared.Subteam.design, shared.Subteam.electrical, shared.Subteam.software, shared.Subteam.marketing]
//            TODO: do it the proper way:
//            var subs = shared.Subteam.entries
            if (!errorLoadingUsers) {
                if (users != nil) {
                    List(subteams, id: \.self) { subteam in
                        Section(header: Text(subteam.name)) {
                            ForEach (users! , id: \.self) { user in
                                if (user.subteam == subteam && user.accountType != shared.AccountType.unverified) {
                                    NavigationLink {
                                        UserView(user: user)
                                    } label: {
                                        DirectoryBar(user: user)
                                    }
                                }
                            }
                        }
                    }
                    .navigationTitle("People")
                    .navigationBarTitleDisplayMode(.inline)
                    .refreshable {
                        Task {
                            guard let response = try await shared.EmotionClient().getUsers(user: user) else {
                                alertBoxShowing = true
                                return
                            }
                            users = response
                        }
                    }
                    .alert("Error refreshing users.\nPlease try again later.", isPresented: $alertBoxShowing) {
                        Button("Ok", role: .cancel) {}
                    }
//                    .toolbar {
//                        ToolbarItem(placement: .topBarTrailing) {
//                            Button {
//                                popoverShown = true
//                            } label: {
//                                Image(systemName: "person.crop.circle.badge.questionmark")
//                            }
//                        }
//                    }
                    .popover(isPresented: $popoverShown) {
                        VerifyUsersView(presented: $popoverShown)
                    }
                } else {
                    ProgressView()
                }
            }
            else {
                VStack {
                    Text("Error Loading Directory")
                        .padding(.bottom, 10)
                    Button {
                        
                    } label: {
                        Text("Retry")
                    }
                }
                
            }
        }
        .onAppear() {
            Task {
                guard let response = try await shared.EmotionClient().getUsers(user: user) else {
                    errorLoadingUsers = true
                    return
                }
                errorLoadingUsers = false
                users = response
            }
        }
    }
}

struct DirectoryView_Previews: PreviewProvider {
    static var previews: some View {
        DirectoryView(user: HelpfulVars().testuser)
    }
}
