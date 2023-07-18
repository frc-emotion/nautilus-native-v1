//
//  DirectoryBar.swift
//  iosApp
//
//  Created by Jason Ballinger on 7/11/23.
//  Copyright © 2023 team2658. All rights reserved.
//

import SwiftUI

struct DirectoryBar: View {
    var user: User
    
    var body: some View {
        HStack {
            Text("\(user.firstname) \(user.lastname)")
        }
    }
}

struct DirectoryBar_Previews: PreviewProvider {
    static var previews: some View {
        DirectoryBar(user: users[0])
    }
}
