//
//  DirectoryBar.swift
//  iosApp
//
//  Created by Jason Ballinger on 7/11/23.
//  Copyright © 2023 team2658. All rights reserved.
//

import SwiftUI
import shared

struct DirectoryBar: View {
    var user: shared.TokenUser
    
    var body: some View {
        if (user.accountType.value >= 2) {
            HStack {
                Text("\(user.firstname) \(user.lastname)")
                    .fontWeight(.bold)
            }
        } else {
            HStack {
                Text("\(user.firstname) \(user.lastname)")
            }
        }
        
    }
}

struct DirectoryBar_Previews: PreviewProvider {
    static var previews: some View {
        DirectoryBar(user: HelpfulVars().testuser)
    }
}
