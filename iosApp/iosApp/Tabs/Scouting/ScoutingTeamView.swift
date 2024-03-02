//
//  ScoutedTeamView.swift
//  E-Motion
//
//  Created by Jason Ballinger on 3/2/24.
//  Copyright © 2024 team2658. All rights reserved.
//

import SwiftUI
import shared

struct ScoutedTeamView: View {
    @State var team: Int32
    @State var data: [shared.Crescendo]
    
    var body: some View {
        Text(/*@START_MENU_TOKEN@*/"Hello, World!"/*@END_MENU_TOKEN@*/)
    }
}

#Preview {
    ScoutedTeamView(team: 2658, data: [])
}
