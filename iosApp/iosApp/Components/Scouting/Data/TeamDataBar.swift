//
//  TeamDataBar.swift
//  E-Motion
//
//  Created by Jason Ballinger on 3/1/24.
//  Copyright © 2024 team2658. All rights reserved.
//

import SwiftUI
import shared

struct TeamDataBar: View {
    @State var team: Int32
    @State var data: [shared.Crescendo]
    
    var body: some View {
        var avgRP: Double {
            var totalRP: Int32 {
                return data.reduce(0) { $0 + $1.rankingPoints }
            }
            return Double(totalRP) / Double(data.count)
        }
        var wins: Int {
            var wins = 0
            for item in data {
                if (item.won) {
                    wins += 1
                }
            }
            return wins
        }
        var ties: Int {
            var ties = 0
            for item in data {
                if (item.tied) {
                    ties += 1
                }
            }
            return ties
        }
        var losses: Int {
            var losses = 0
            for item in data {
                if (!(item.won || item.tied)) {
                    losses += 1
                }
            }
            return losses
        }
        let winrate: Double = Double((Double(wins) / Double(data.count) * 100))
        var formattedWinrate: String {
            if winrate.truncatingRemainder(dividingBy: 1) == 0 {
                return String(format: "%.0f", winrate)
            } else {
                return String(format: "%.2f", winrate)
            }
        }
        
        let matches = Dictionary(grouping: data, by: \.matchNumber)
        var duplicates: Bool {
            for item in matches {
                if (item.value.count > 1) {
                    return true
                }
            }
            return false
        }
        
        VStack {
            HStack {
                Text("\(data.first?.teamName ?? "UNKNOWN")")
                    .fontWeight(.bold)
                    .lineLimit(1)
                    .truncationMode(.tail)
                Text("#\(String(team))")
                    .foregroundStyle(Color.secondary)
                    .lineLimit(1)
                Spacer()
                if (duplicates) {
                    Image(systemName: "exclamationmark.triangle.fill")
                        .foregroundStyle(Color.red)
                }
            }
            HStack {
                Text("\(String(wins))-\(String(losses))-\(String(ties))")
                Text("(\(formattedWinrate)%)")
                    .foregroundStyle(Color.secondary)
                Spacer()
                Text("\(String(format: "%.2f", avgRP)) Avg. RP")
            }
        }
    }
}

#Preview {
    TeamDataBar(team: 2658, data: [HelpfulVars().testmatchwin, HelpfulVars().testmatchwin, HelpfulVars().testmatchwin, HelpfulVars().testmatchlose, HelpfulVars().testmatchtie])
}
