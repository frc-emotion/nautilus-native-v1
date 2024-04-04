//
//  TeamDataRankingPointsOverview.swift
//  E-Motion
//
//  Created by Jason Ballinger on 3/23/24.
//  Copyright © 2024 team2658. All rights reserved.
//

import SwiftUI
import shared

struct TeamDataRankingPointsOverview: View {
    var data: [shared.Crescendo]
    
    var totalRP: Int {
        var temp = 0
        for item in data {
            temp += Int(item.rankingPoints)
        }
        return temp
    }
    
    var matchRP: Int {
        var temp = 0
        for item in data {
            if (item.won) {
                temp += 2
            }
            if (item.tied) {
                temp += 1
            }
        }
        return temp
    }
    
    var melodyRP: Int {
        var temp = 0
        for item in data {
            if (item.ranking.melody) {
                temp += 1
            }
        }
        return temp
    }
    
    var ensembleRP: Int {
        var temp = 0
        for item in data {
            if (item.ranking.ensemble) {
                temp += 1
            }
        }
        return temp
    }
    
    var body: some View {
        VStack(spacing: 5) {
            HStack {
                // total (avg)
                VStack(spacing: 10) {
                    Text("Ranking Points").fontWeight(.bold)
                    Text(String(totalRP)).font(.title)
                    Text(String(format: "%.2f", Double(Double(totalRP) / Double(data.count))))
                        .foregroundStyle(Color.gray)
                }
                .frame(minWidth: 0, maxWidth: .infinity)
                Divider()
                VStack(spacing: 10) {
                    Text("Coopertition").fontWeight(.bold)
                    Text("N/A").font(.title)
                    Text("N/A").foregroundStyle(Color.gray)
                }
                .frame(minWidth: 0, maxWidth: .infinity)
            }
            Spacer()
            Divider()
            Spacer()
            HStack {
                // match result
                VStack(spacing: 10) {
                    Text("Result").fontWeight(.bold)
                    Text(String(matchRP)).font(.title)
                    Text(String(format: "%.2f", Double(Double(matchRP) / Double(data.count))))
                        .foregroundStyle(Color.gray)
                }
                .frame(minWidth: 0, maxWidth: .infinity)
                Divider()
                // melody
                VStack(spacing: 10) {
                    Text("Melody").fontWeight(.bold)
                    Text(String(melodyRP)).font(.title)
                    Text(String(format: "%.2f", Double(Double(melodyRP) / Double(data.count))))
                        .foregroundStyle(Color.gray)
                }
                .frame(minWidth: 0, maxWidth: .infinity)
                Divider()
                // harmony
                VStack(spacing: 10) {
                    Text("Ensemble").fontWeight(.bold)
                    Text(String(ensembleRP)).font(.title)
                    Text(String(format: "%.2f", Double(Double(ensembleRP) / Double(data.count))))
                        .foregroundStyle(Color.gray)
                }
                .frame(minWidth: 0, maxWidth: .infinity)
            }
        }
        .frame(maxHeight: 200)
    }
}

#Preview {
    TeamDataRankingPointsOverview(data: ([HelpfulVars().testmatchwin]))
}
