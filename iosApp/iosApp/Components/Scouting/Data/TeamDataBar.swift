//
//  TeamDataBar.swift
//  E-Motion
//
//  Created by Jason Ballinger on 3/1/24.
//  Copyright © 2024 team2658. All rights reserved.
//

import SwiftUI

struct TeamDataBar: View {
    var body: some View {
        VStack {
            HStack {
                Text("Team Name")
                    .fontWeight(.bold)
                Text("#1234")
                    .foregroundStyle(Color.secondary)
                Spacer()
            }
            HStack {
                Text("10-2-0")
                Text("(83.3%)")
                    .foregroundStyle(Color.secondary)
                Spacer()
                Text("27 RP")
            }
        }
    }
}

#Preview {
    TeamDataBar()
}
