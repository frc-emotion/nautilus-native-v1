//
//  AllianceScoreProportionView.swift
//  E-Motion
//
//  Created by Jason Ballinger on 4/4/24.
//  Copyright © 2024 team2658. All rights reserved.
//

import SwiftUI
import shared
import Charts

struct AllianceScoreProportionView: View {
    @Binding var alliance: [shared.Crescendo]
    var body: some View {
        Chart(alliance, id: \.self) {
            BarMark(x: .value("Score", $0.teamScore))
                .foregroundStyle(by: .value("Team Number", String($0.teamNumber)))
        }
    }
}

#Preview {
    AllianceScoreProportionView(alliance: .constant([HelpfulVars().testmatchwin]))
}
