//
//  ScoutedTeamView.swift
//  E-Motion
//
//  Created by Jason Ballinger on 3/2/24.
//  Copyright © 2024 team2658. All rights reserved.
//

import SwiftUI
import shared

struct ScoutedTeamView: View {
    @State var team: Int32
    @State var data: [shared.Crescendo]
    @State var filteredData: [shared.Crescendo] = []
    @Binding var selection: Int32?
    
    var body: some View {
        // filter data to team
        
        var filteredData: [shared.Crescendo] {
            return data.filter { $0.teamNumber == team }
        }
        
        var matches: Dictionary<Int32, [shared.Crescendo]> {
            return Dictionary(grouping: filteredData, by: \.matchNumber)
        }
        
        VStack {
            List {
                Section("Ranking") {
                    VStack {
                        TeamDataWinrate(data: filteredData)
                            .padding(.vertical)
                        Divider()
                        TeamDataRankingPointsOverview(data: filteredData)
                            .padding(.vertical)
                    }
                }
                Section("Scoring") {
                    NavigationLink {
                        CrescendoTeamDataView(data: filteredData)
                    } label: {
                        CrescendoMatchScorePreviewView(data: filteredData)
                            .padding(.vertical)
                    }
                }
                Section("Matches") {
                    
                    ForEach(matches.sorted(by: {$0.key < $1.key}), id: \.key) { match in
                        
                        NavigationLink {
                            ScoutedTeamMatchInfoView(match: match.key, data: match.value)
                        } label: {
                            ScoutedTeamMatchInfoBarView(match: match.key, data: match.value)
                        }
                    }
                }
            }
        }
        .navigationTitle("\(String(filteredData.first?.teamName ?? ""))")
        .navigationBarTitleDisplayMode(.large)
//        .padding(.horizontal)
//        .onDisappear() {
//            selection = nil
//        }
    }
}

#Preview {
    ScoutedTeamView(team: 2658, data: [HelpfulVars().testmatchwin, HelpfulVars().testmatchwin, HelpfulVars().testmatchlose], selection: .constant(0)).environmentObject({ () -> EnvironmentModel in
        let env = EnvironmentModel()
        env.user = HelpfulVars().testuser
        return env
    }() )
}

