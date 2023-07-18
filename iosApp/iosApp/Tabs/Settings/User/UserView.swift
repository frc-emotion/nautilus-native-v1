//
//  UserView.swift
//  iosApp
//
//  Created by Jason Ballinger on 7/11/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI

struct UserView: View {
    var user: User
    
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
                        Text("Software Team Lead")
                        Spacer()
                        Text("Senior")
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
        UserView(user: users[0])
    }
}
