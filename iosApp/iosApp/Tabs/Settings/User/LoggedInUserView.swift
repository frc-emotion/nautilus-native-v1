//
//  UserView.swift
//  iosApp
//
//  Created by Jason Ballinger on 7/11/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import shared

struct LoggedInUserView: View {
    // TODO: This needs to work for both Token & non-token users
    @State var user: shared.User
    
    
    var body: some View {
        ScrollView {
            ProfileView(user: user)
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

struct UserView_Previews: PreviewProvider {
    static var previews: some View {
        LoggedInUserView(user: HelpfulVars().testuser)
    }
}
