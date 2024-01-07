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
        NavigationView {
            let subteams = [shared.Subteam.executive, shared.Subteam.build, shared.Subteam.design, shared.Subteam.electrical, shared.Subteam.software, shared.Subteam.marketing]
//            TODO: do it the proper way:
//            var subs = shared.Subteam.entries
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
                .navigationTitle("People")
            } else {
                ProgressView()
            }
        }
        .onAppear() {
            Task {
                let response = try await shared.EmotionClient().getUsers(user: user)
                if (response != nil) { users = response }
            }
        }
    }
}

struct DirectoryView_Previews: PreviewProvider {
    static var previews: some View {
        DirectoryView(user: HelpfulVars().testuser)
    }
}
