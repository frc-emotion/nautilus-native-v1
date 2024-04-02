//
//  CrescendoTeamAutoTeleopView.swift
//  E-Motion
//
//  Created by Jason Ballinger on 4/2/24.
//  Copyright © 2024 team2658. All rights reserved.
//

import SwiftUI
import shared
import Charts

struct CrescendoTeamAutoTeleopView: View {
    @Binding var data: [shared.Crescendo]
    
    var TeamMatchData: [(period: String, matchNumber: Int, score: Int)] {
        let auto = data.map { ("Autonomous", Int($0.matchNumber), Int($0.autoScore)) }
        let teleop = data.map { ("Teleop", Int($0.matchNumber), Int($0.teleopScore)) }
        let endgame = data.map { ("Endgame", Int($0.matchNumber), Int($0.endgameScore)) }
        return auto + teleop + endgame
    }
    
    var body: some View {
        VStack {
            Chart(TeamMatchData, id: \.matchNumber) { item in
                BarMark(x: .value("Match Number", String(item.matchNumber)), y: .value("Score", item.score), stacking: .standard)
//                    .annotation {
//                        Text("\(item.score)").font(.caption)
//                    }
                    .foregroundStyle(by: .value("Group", item.period))
                    
            }
            .chartForegroundStyleScale([
                "Autonomous": .red, "Teleop": .blue, "Endgame": .green
            ])
        }
    }
}

#Preview {
    CrescendoTeamAutoTeleopView(data: .constant([HelpfulVars().testmatchwin, HelpfulVars().testmatchlose]))
}
