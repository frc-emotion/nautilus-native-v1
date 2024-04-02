//
//  CrescendoAutoBreakdownView.swift
//  E-Motion
//
//  Created by Jason Ballinger on 4/2/24.
//  Copyright © 2024 team2658. All rights reserved.
//

import SwiftUI
import shared
import Charts

struct CrescendoAutoBreakdownView: View {
    @Binding var data: [shared.Crescendo]
    
    var TeamMatchData: [(period: String, matchNumber: Int, score: Int)] {
        // leave, amp, speaker
        let leave = data.map { ("Leave", Int($0.matchNumber), Int($0.auto_.leave ? 2 : 0))}
        let amp = data.map { ("Amp", Int($0.matchNumber), Int($0.auto_.ampNotes * 2)) }
        let speaker = data.map { ("Speaker", Int($0.matchNumber), Int($0.auto_.speakerNotes * 5)) }
        return leave + amp + speaker
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
                "Leave": .green, "Amp": .red, "Speaker": .blue
            ])
        }
    }
}

#Preview {
    CrescendoAutoBreakdownView(data: .constant([HelpfulVars().testmatchwin, HelpfulVars().testmatchlose]))
}
