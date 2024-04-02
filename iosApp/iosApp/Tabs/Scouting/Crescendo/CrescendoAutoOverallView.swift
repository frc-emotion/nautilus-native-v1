//
//  CrescendoAutoOverallView.swift
//  E-Motion
//
//  Created by Jason Ballinger on 4/2/24.
//  Copyright © 2024 team2658. All rights reserved.
//

import SwiftUI
import shared
import Charts

struct CrescendoAutoOverallView: View {
    @Binding var data: [shared.Crescendo]
    
    var TeamMatchData: [(period: String, matchNumber: Int, score: Int)] {
        return data.map { ("Autonomous", Int($0.matchNumber), Int($0.autoScore)) }
    }
    
    var body: some View {
        VStack {
            Chart(TeamMatchData, id: \.matchNumber) { item in
                BarMark(x: .value("Match Number", String(item.matchNumber)), y: .value("Score", item.score), stacking: .standard)
                    .annotation {
                        Text("\(item.score)").font(.caption)
                    }
                    
            }
        }
    }
}

#Preview {
    CrescendoAutoOverallView(data: .constant([HelpfulVars().testmatchwin, HelpfulVars().testmatchlose]))
}
