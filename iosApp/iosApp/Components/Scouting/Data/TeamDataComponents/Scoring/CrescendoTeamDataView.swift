//
//  CrescendoTeamDataView.swift
//  E-Motion
//
//  Created by Jason Ballinger on 3/28/24.
//  Copyright © 2024 team2658. All rights reserved.
//

import SwiftUI
import shared

enum MatchViewGraph {
    case overall
    case teamProp
    case teamBreakdown
}

enum AutoViewGraph {
    case overall
    case breakdown
//    case attemptProp
}

enum TeleopViewGraph {
    case overall
    case breakdown
}

//enum EndgameViewGraph {
//    case overall
//    case breakdown
//}

struct CrescendoTeamDataView: View {
    @State private var matchViewGraphSelection: MatchViewGraph = .overall
    @State private var autoViewGraphSelection: AutoViewGraph = .overall
    @State private var teleopViewGraphSelection: TeleopViewGraph = .overall
//    @State private var endgameViewGraphSelection: EndgameViewGraph = .overall
    @State var data: [shared.Crescendo]
    
    private let graphFrameHeight: CGFloat = 250
    
    var body: some View {
        List {
            Section("Match") {
                switch matchViewGraphSelection {
                case .overall:
                    CrescendoMatchScoreView(data: $data)
                        .padding(.vertical)
                        .frame(minHeight: graphFrameHeight)
                case .teamProp:
                    CrescendoTeamPropView(data: $data)
                        .padding(.vertical)
                        .frame(minHeight: graphFrameHeight)
                case .teamBreakdown:
                    CrescendoTeamAutoTeleopView(data: $data)
                        .padding(.vertical)
                        .frame(minHeight: graphFrameHeight)
                }
                
                Picker("Select Match Graph", selection: $matchViewGraphSelection) {
                    Text("Overall").tag(MatchViewGraph.overall)
                    Text("Team Proportion").tag(MatchViewGraph.teamProp)
                    Text("Team Auto / Teleop").tag(MatchViewGraph.teamBreakdown)
                }
                .pickerStyle(.inline)
                .labelsHidden()
            }
            
            Section("Autonomous") {
                switch autoViewGraphSelection {
                case .overall:
                    CrescendoAutoOverallView(data: $data)
                        .padding(.vertical)
                        .frame(minHeight: graphFrameHeight)
                case .breakdown:
                    CrescendoAutoBreakdownView(data: $data)
                        .padding(.vertical)
                        .frame(minHeight: graphFrameHeight)
                }
                
                Picker("Select Autonomous Graph", selection: $autoViewGraphSelection) {
                    Text("Overall").tag(AutoViewGraph.overall)
                    Text("Autonomous Score Breakdown").tag(AutoViewGraph.breakdown)
                }
                .pickerStyle(.inline)
                .labelsHidden()
            }
            
            Section("Teleop") {
                switch teleopViewGraphSelection {
                case .overall:
                    CrescendoTeleopOverallView(data: $data)
                        .padding(.vertical)
                        .frame(minHeight: graphFrameHeight)
                case .breakdown:
                    CrescendoTeleopBreakdownView(data: $data)
                        .padding(.vertical)
                        .frame(minHeight: graphFrameHeight)
                }
                
                Picker("Select Teleop Graph", selection: $teleopViewGraphSelection) {
                    Text("Overall").tag(TeleopViewGraph.overall)
                    Text("Teleop Score Breakdown").tag(TeleopViewGraph.breakdown)
                }
                .pickerStyle(.inline)
                .labelsHidden()
            }
            
        }
        .navigationTitle("Scoring")
        .navigationBarTitleDisplayMode(.inline)
    }
}

#Preview {
    CrescendoTeamDataView(data: [HelpfulVars().testmatchwin])
}
