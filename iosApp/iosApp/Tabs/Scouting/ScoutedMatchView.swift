//
//  ScoutedMatchView.swift
//  E-Motion
//
//  Created by Jason Ballinger on 3/2/24.
//  Copyright © 2024 team2658. All rights reserved.
//

import SwiftUI
import shared

struct ScoutedMatchView: View {
    @State var match: Int32
    @State var data: [shared.Crescendo]
    @Binding var selection: Int32?
    
    var body: some View {
        VStack {
            Text(/*@START_MENU_TOKEN@*/"Hello, World!"/*@END_MENU_TOKEN@*/)
        }
        .onDisappear() {
            selection = nil
        }
    }
}

#Preview {
    ScoutedMatchView(match: 0, data: [], selection: .constant(0))
}
