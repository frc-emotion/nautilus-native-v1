//
//  TeamDataWinrate.swift
//  E-Motion
//
//  Created by Jason Ballinger on 3/23/24.
//  Copyright © 2024 team2658. All rights reserved.
//

import SwiftUI
import Charts
import shared

struct TeamDataWinrate: View {
    var data: [shared.Crescendo]
    
    private var wins: Int {
        var wins = 0
        for item in data {
            if (item.won) {
                wins += 1
            }
        }
        return wins
    }
    
    private var winrate: Double {
        return Double((Double(wins) / Double(data.count)) * 100)
    }
    
    private var ties: Int {
        var ties = 0
        for item in data {
            if (item.tied) {
                ties += 1
            }
        }
        return ties
    }
    
    private var tierate: Double {
        return Double((Double(ties) / Double(data.count)) * 100)
    }
    
    private var losses: Int {
        var losses = 0
        for item in data {
            if (!(item.won || item.tied)) {
                losses += 1
            }
        }
        return losses
    }
    
    private var lossrate: Double {
        return Double((Double(losses) / Double(data.count)) * 100)
    }
    
    var body: some View {
        HStack {
            VStack {
                // win/tie/loss subheading
                HStack {
                    Text("Win / Loss / Tie").font(.subheadline)
                    Spacer()
                }
                Spacer()
                // win/tie/loss numbers
                HStack {
                    Text("\(wins) / \(losses) / \(ties)").font(.title).fontWeight(.bold)
                    Spacer()
                }
            }
            
            // w/t/l chart
            TeamWinrateDonut(winrate: winrate / 100, tierate: tierate / 100, lossrate: lossrate / 100)
        }
        .frame(maxHeight: 60)
    }
}

#Preview {
    TeamDataWinrate(data: ([HelpfulVars().testmatchwin, HelpfulVars().testmatchwin, HelpfulVars().testmatchwin, HelpfulVars().testmatchlose, HelpfulVars().testmatchtie]))
}
