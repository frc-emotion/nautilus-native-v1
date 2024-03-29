//
//  ScoutedTeamMatchInfoBarView.swift
//  E-Motion
//
//  Created by Jason Ballinger on 3/27/24.
//  Copyright © 2024 team2658. All rights reserved.
//

import SwiftUI
import shared

struct ScoutedTeamMatchInfoBarView: View {
    @State var match: Int32
    @State var data: [shared.Crescendo]
    var body: some View {
        HStack {
            Text("Match \(match)")
            Spacer()
            if (data.count > 1) {
                Image(systemName: "exclamationmark.triangle.fill")
                    .foregroundStyle(Color.red)
            }
        }
    }
}

#Preview {
    ScoutedTeamMatchInfoBarView(match: 0, data: [])
}
