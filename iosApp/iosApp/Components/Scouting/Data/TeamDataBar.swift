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
        var rankingPoints: Int32 {
            return data.reduce(0) { $0 + $1.rankingPoints }
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
        let winrate: Double = Double((wins / (wins + losses + ties))) * 100
        var formattedWinrate: String {
            if winrate.truncatingRemainder(dividingBy: 1) == 0 {
                return String(format: "%.0f", winrate)
            } else {
                return String(format: "%.2f", winrate)
            }
        }
        
        VStack {
            HStack {
                Text("\(data.first?.teamName ?? "UNKNOWN")")
                    .fontWeight(.bold)
                Text("#\(String(team))")
                    .foregroundStyle(Color.secondary)
                Spacer()
            }
            HStack {
                Text("\(String(wins))-\(String(losses))-\(String(ties))")
                Text("(\(formattedWinrate)%)")
                    .foregroundStyle(Color.secondary)
                Spacer()
                Text("\(rankingPoints) RP")
            }
        }
    }
}

#Preview {
    TeamDataBar(team: 2658, data: [])
}
