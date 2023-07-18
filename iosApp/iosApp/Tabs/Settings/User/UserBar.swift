//
//  UserBar.swift
//  iosApp
//
//  Created by Jason Ballinger on 7/11/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI

struct UserBar: View {
    var body: some View {
        HStack(alignment: .center, spacing: 15) {
            Image("profile-default")
                .resizable()
                .frame(width: 60, height: 60)
                .clipShape(Circle())
            VStack(alignment: .leading, spacing: 2.0) {
                Text("Jason Ballinger")
                    .font(.title)
                Text("Software Team Lead")
                    .font(.subheadline)
                    .foregroundColor(Color.gray)
            }
        }
    }
}

struct UserBar_Previews: PreviewProvider {
    static var previews: some View {
        UserBar()
    }
}
