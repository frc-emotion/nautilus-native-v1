//
//  AlliancePhaseProportionView.swift
//  E-Motion
//
//  Created by Jason Ballinger on 4/4/24.
//  Copyright © 2024 team2658. All rights reserved.
//

import SwiftUI
import shared
import Charts

struct AlliancePhaseProportionView: View {
    @Binding var alliance: [shared.Crescendo]
    
    var allianceScorePhases: [(id: UUID, phase: String, score: Int32)] {
        var autoScore: Int32 = 0
        var teleopScore: Int32 = 0
        var endgameScore: Int32 = 0
        
        for item in alliance {
            autoScore += item.autoScore
            teleopScore += item.teleopScore
            endgameScore += item.endgameScore
        }
        
        return [(UUID(), "Autonomous", autoScore), (UUID(), "Teleop", teleopScore), (UUID(), "Endgame", endgameScore)]
    }
    
    var body: some View {
        Chart(allianceScorePhases, id: \.id) {
            BarMark(x: .value("Score", $0.score))
                .foregroundStyle(by: .value("Phase", $0.phase))
        }
        .chartForegroundStyleScale([
            "Autonomous": .red, "Teleop": .blue, "Endgame": .green
        ])
    }
}

#Preview {
    AlliancePhaseProportionView(alliance: .constant([]))
}
