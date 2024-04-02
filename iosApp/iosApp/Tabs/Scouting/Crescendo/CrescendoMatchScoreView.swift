//
//  CrescendoMatchScoreView.swift
//  E-Motion
//
//  Created by Jason Ballinger on 3/28/24.
//  Copyright © 2024 team2658. All rights reserved.
//

import SwiftUI
import Charts
import shared

struct CrescendoMatchScoreView: View {
    @Binding var data: [shared.Crescendo]
    
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
        }
    }
}

#Preview {
    CrescendoMatchScorePreviewView(data: ([HelpfulVars().testmatchwin, HelpfulVars().testmatchlose]))
}
