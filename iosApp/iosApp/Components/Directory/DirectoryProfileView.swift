//
//  DirectoryProfileView.swift
//  E-Motion
//
//  Created by Jason Ballinger on 3/21/24.
//  Copyright © 2024 team2658. All rights reserved.
//

import SwiftUI
import shared

struct DirectoryProfileView: View {
    @Binding var user: shared.PartialUser
    
    var body: some View {
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
    }
}

#Preview {
    DirectoryProfileView(user: .constant(HelpfulVars().testuser as shared.User as! shared.PartialUser))
}
