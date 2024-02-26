//
//  UserView.swift
//  iosApp
//
//  Created by Jason Ballinger on 7/11/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import shared

struct UserView: View {
    // TODO: This needs to work for both Token & non-token users
    @State var user: shared.User
    
    
    var body: some View {
        ScrollView {
            VStack {
                HStack(alignment: .top) {
                    Image("profile-default")
                        .resizable()
                        .clipShape(Circle())
                        .shadow(radius: 5)
                        .frame(width: 100.0, height: 100.0)
                    Spacer()
                    HStack {
                        // social icons, links?
                    }
                }
                VStack(alignment: .leading) {
                    Text("\(user.firstname) \(user.lastname)")
                        .font(.title)
                    
                    HStack {
                        if (user.subteam != nil) {
                            Text((user.accountType.value >= 2 ? "\(user.subteam!.description().capitalized) Team Lead" : "\(user.subteam!.description().capitalized) Team Member"))
                        } else {
                            Text("No subteam")
                        }
                        Spacer()
//                        switch (user.grade) {
//                        case 9: Text("Freshman")
//                        case 10: Text("Sophomore")
//                        case 11: Text("Junior")
//                        case 12: Text("Senior")
//                        default: Text("")
//                        }
                        Text("")
                    }
                    .font(.subheadline).foregroundColor(.secondary)
                }
            }
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
        UserView(user: HelpfulVars().testuser)
    }
}
