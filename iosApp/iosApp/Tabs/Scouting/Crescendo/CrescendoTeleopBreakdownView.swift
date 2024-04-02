//
//  CrescendoTeleopBreakdownView.swift
//  E-Motion
//
//  Created by Jason Ballinger on 4/2/24.
//  Copyright © 2024 team2658. All rights reserved.
//

import SwiftUI
import shared
import Charts

struct CrescendoTeleopBreakdownView: View {
    @Binding var data: [shared.Crescendo]
    
    var TeamMatchData: [(period: String, matchNumber: Int, score: Int)] {
        let amp = data.map { ("Amp", Int($0.matchNumber), Int($0.teleop.ampNotes)) }
        let speaker = data.map { ("Speaker", Int($0.matchNumber), Int($0.teleop.speakerUnamped * 2)) }
        let speakerAmped = data.map { ("Speaker (Amped)", Int($0.matchNumber), Int($0.teleop.speakerAmped * 5)) }
        return amp + speaker + speakerAmped
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
                "Amp": .red, "Speaker": .blue, "Speaker (Amped)": .green
            ])
        }
    }
}

#Preview {
    CrescendoTeleopBreakdownView(data: .constant([HelpfulVars().testmatchwin, HelpfulVars().testmatchlose]))
}
