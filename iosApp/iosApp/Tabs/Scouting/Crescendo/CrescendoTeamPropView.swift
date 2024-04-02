//
//  CrescendoTeamPropView.swift
//  E-Motion
//
//  Created by Jason Ballinger on 4/1/24.
//  Copyright © 2024 team2658. All rights reserved.
//

import SwiftUI
import shared
import Charts

struct CrescendoTeamPropView: View {
    @Binding var data: [shared.Crescendo]
    
    var TeamMatchData: [(period: String, matchNumber: Int, score: Int)] {
        let match = data.map { ("Match", Int($0.matchNumber), Int($0.finalScore)) }
        let team = data.map { ("Team", Int($0.matchNumber), Int($0.teamScore))}
        return match + team
    }
    
    var body: some View {
        VStack {
            Chart(TeamMatchData, id: \.matchNumber) { item in
                BarMark(x: .value("Match Number", String(item.matchNumber)), y: .value("Score", item.score), stacking: .unstacked)
//                    .annotation {
//                        Text("\(item.score)").font(.caption)
//                    }
                    .foregroundStyle(by: .value("Group", item.period))
                    
            }
            .chartForegroundStyleScale([
                "Match": .blue, "Team": .red
            ])
        }
    }
}

#Preview {
    CrescendoTeamPropView(data: .constant([HelpfulVars().testmatchwin, HelpfulVars().testmatchlose]))
}
