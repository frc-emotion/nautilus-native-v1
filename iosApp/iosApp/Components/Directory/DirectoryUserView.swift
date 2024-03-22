//
//  DirectoryUserView.swift
//  E-Motion
//
//  Created by Jason Ballinger on 3/21/24.
//  Copyright © 2024 team2658. All rights reserved.
//

import SwiftUI
import shared

struct DirectoryUserView: View {
    @Binding var user: shared.PartialUser
    
    var body: some View {
        ScrollView {
            DirectoryProfileView(user: $user)
                .padding()
            Divider()
            VStack {
                Text("Nothing to see here")
            }
            .padding()
        }
        
    }
}

#Preview {
    DirectoryUserView(user: .constant(HelpfulVars().testuser as shared.User as! shared.PartialUser))
}
