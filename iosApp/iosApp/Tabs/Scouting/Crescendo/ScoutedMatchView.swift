//
//  ScoutedMatchView.swift
//  E-Motion
//
//  Created by Jason Ballinger on 3/2/24.
//  Copyright © 2024 team2658. All rights reserved.
//

import SwiftUI
import shared

struct ScoutedMatchView: View {
    @State var match: Int32
    @State var data: [shared.Crescendo]
    @EnvironmentObject var env: EnvironmentModel
//    @Binding var selection: Int32?
    
    var filteredMatches: [shared.Crescendo] {
        var toReturn: [shared.Crescendo] = []
        for item in data {
            if (item.matchNumber == match) {
                toReturn.append(item)
            }
        }
        return toReturn
    }
    
    // winning alliance
    var winningAlliance: [shared.Crescendo] {
        var winningScore: Int32 = 0
        var toReturn: [shared.Crescendo] = []
        
        for item in filteredMatches {
            if (item.finalScore > winningScore) {
                winningScore = item.finalScore
            }
        }
        
        for item in filteredMatches {
            if (item.finalScore == winningScore) {
                toReturn.append(item)
            }
        }
        
        return toReturn
    }
    
    // losing alliance
    var losingAlliance: [shared.Crescendo] {
        var toReturn: [shared.Crescendo] = []
        for item in filteredMatches {
            if (!winningAlliance.contains(item)) {
                toReturn.append(item)
            }
        }
        return toReturn
    }
    
    var body: some View {
        List {
            Section("Score") {
                AllianceScoreboardView(alliance: winningAlliance)
                    .fontWeight(.bold)
                AllianceScoreboardView(alliance: losingAlliance)
            }
            
            // winning alliance stats
            Section("Winning Alliance") {
                AllianceDataView(alliance: winningAlliance)
            }
            // losing alliance stats
            Section("Losing Alliance") {
                AllianceDataView(alliance: losingAlliance)
            }
        }
        .navigationTitle("Match \(match)")
        .navigationBarTitleDisplayMode(.large)
//        .onAppear {
//            
//        }
//        .onDisappear() {
//            selection = nil
//        }
    }
}

#Preview {
    ScoutedMatchView(match: 0, data: []).environmentObject({ () -> EnvironmentModel in
        let env = EnvironmentModel()
        env.user = HelpfulVars().testuser
        return env
    }() )
}
