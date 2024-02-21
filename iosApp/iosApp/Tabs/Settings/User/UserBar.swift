//
//  UserBar.swift
//  iosApp
//
//  Created by Jason Ballinger on 7/11/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import shared

struct UserBar: View {
    var user: shared.User
    
    var body: some View {
        HStack(alignment: .center, spacing: 15) {
            Image("profile-default")
                .resizable()
                .frame(width: 60, height: 60)
                .clipShape(Circle())
            VStack(alignment: .leading, spacing: 2.0) {
                Text("\(user.firstname) \(user.lastname)")
                    .font(.title)
                
                if (user.subteam != nil) {
                    Text((user.accountType.value >= 2 ? "\(user.subteam!.description().capitalized) Team Lead" : "\(user.subteam!.description().capitalized) Team Member"))
                        .font(.subheadline)
                        .foregroundColor(Color.gray)
                } else {
                    Text("No subteam")
                }
            }
        }
    }
}

struct UserBar_Previews: PreviewProvider {
    static var previews: some View {
        UserBar(user: HelpfulVars().testuser)
    }
}
