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
    var body: some View {
        // currently does not include users not associated with a team
        var subteams = [shared.Subteam.executive, shared.Subteam.build, shared.Subteam.design, shared.Subteam.electrical, shared.Subteam.software, shared.Subteam.marketing]
        NavigationView {
            if (users != nil) {
                List(subteams, id: \.self) { subteam in
                    Section(header: Text(subteam.name)) {
                        ForEach (users! , id: \.self) { user in
                            if (user.subteam == subteam) {
                                NavigationLink {
                                    UserView(user: user)
                                } label: {
                                    DirectoryBar(user: user)
                                }
                            }
                        }
                    }
                }
            } else {
                ProgressView()
            }
        }
        .navigationTitle("People")
        .onAppear() {
            Task {
                users = try await shared.EmotionClient().getUsers(user: user)
                print(users)
            }
        }
//        .navigationTitle("People")
    }
}

struct DirectoryView_Previews: PreviewProvider {
    static var previews: some View {
        DirectoryView(user: HelpfulVars().testuser)
    }
}
