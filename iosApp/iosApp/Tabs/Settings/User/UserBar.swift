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
                Text("\(user.firstName) \(user.lastName)")
                    .font(.title)
                Text(user.email)
                    .font(.subheadline)
                    .foregroundColor(Color.gray)
            }
        }
    }
}

struct UserBar_Previews: PreviewProvider {
    static var previews: some View {
        UserBar(user: HelpfulVars().testuser)
    }
}
