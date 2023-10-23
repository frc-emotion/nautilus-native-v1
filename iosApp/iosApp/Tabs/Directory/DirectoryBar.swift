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
    var user: shared.User
    
    var body: some View {
        HStack {
            Text("\(user.firstName) \(user.lastName)")
        }
    }
}

struct DirectoryBar_Previews: PreviewProvider {
    static var previews: some View {
        DirectoryBar(user: HelpfulVars().testuser)
    }
}
