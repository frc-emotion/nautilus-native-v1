//
//  UserView.swift
//  E-Motion
//
//  Created by Jason Ballinger on 3/1/24.
//  Copyright © 2024 team2658. All rights reserved.
//

import SwiftUI
import shared

struct UserView: View {
    @State var user: shared.User
    
    var body: some View {
        ScrollView {
            ProfileView(user: $user)
            .padding()
            Divider()
            VStack {
                Text("Nothing to see here")
            }
            .padding()
        }
        .navigationTitle("Profile")
        .navigationBarTitleDisplayMode(.inline)
    }
}

#Preview {
    UserView(user: HelpfulVars().testuser)
}
