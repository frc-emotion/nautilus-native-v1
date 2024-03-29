//
//  CrescendoMatchScoreView.swift
//  E-Motion
//
//  Created by Jason Ballinger on 3/23/24.
//  Copyright © 2024 team2658. All rights reserved.
//

import SwiftUI
import Charts
import shared

struct CrescendoMatchScorePreviewView: View {
    var data: [shared.Crescendo]
    var body: some View {
        VStack {
            Chart {
                ForEach(data, id: \.self) { item in
                    BarMark(x: .value("Match Number", String(item.matchNumber)), y: .value("Score", item.finalScore))
                        .annotation {
                            Text("\(item.finalScore)").font(.caption)
                        }
                }
            }
            .chartYAxis(.hidden)
        }
    }
}

#Preview {
    CrescendoMatchScorePreviewView(data: ([HelpfulVars().testmatchwin, HelpfulVars().testmatchtie]))
}
