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
    @EnvironmentObject var env: EnvironmentModel
    
    var body: some View {
        HStack(alignment: .center, spacing: 15) {
            Image("profile-default")
                .resizable()
                .frame(width: 60, height: 60)
                .clipShape(Circle())
            VStack(alignment: .leading, spacing: 2.0) {
                if (env.user != nil){
                    Text("\(env.user!.firstname) \(env.user!.lastname)")
                        .font(.title)
                    
                    if (env.user!.subteam != nil) {
                        Text((env.user!.accountType.value >= 2 ? "\(env.user!.subteam!.description().capitalized) Team Lead" : "\(env.user!.subteam!.description().capitalized) Team Member"))
                            .font(.subheadline)
                            .foregroundColor(Color.gray)
                    } else {
                        Text("No subteam")
                    }
                }
            }
        }
    }
}

struct UserBar_Previews: PreviewProvider {
    static var previews: some View {
        UserBar()
    }
}
