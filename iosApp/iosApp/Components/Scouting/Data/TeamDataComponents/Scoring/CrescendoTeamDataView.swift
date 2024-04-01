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
    case teamPropAutoTeleop
}

//enum AutoViewGraph {
//    case overall
//    case attemptProp
//}
//
//enum TeleopViewGraph {
//    case overall
//    case ampSpeakerProp
//}

struct CrescendoTeamDataView: View {
    @State private var matchViewGraphSelection: MatchViewGraph = .overall
//    @State private var autoViewGraphSelection: AutoViewGraph = .overall
//    @State private var teleopViewGraphSelection: TeleopViewGraph = .overall
    @State var data: [shared.Crescendo]
    
    private let graphFrameHeight: CGFloat = 300
    
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
                case .teamPropAutoTeleop:
                    HomeView()
                }
                
                Picker("Select Match Graph", selection: $matchViewGraphSelection) {
                    Text("Overall").tag(MatchViewGraph.overall)
                    Text("Team Proportion").tag(MatchViewGraph.teamProp)
                    Text("Team Auto / Teleop").tag(MatchViewGraph.teamPropAutoTeleop)
                }
                .pickerStyle(.inline)
                .labelsHidden()
            }
//            
//            Section("Autonomous") {
//                switch autoViewGraphSelection {
//                case .overall:
//                    HomeView()
//                case .attemptProp:
//                    HomeView()
//                }
//                
//                Picker("Select Autonomous Graph", selection: $autoViewGraphSelection) {
//                    Text("Overall").tag(AutoViewGraph.overall)
//                    Text("Successful Shots Proportion").tag(AutoViewGraph.attemptProp)
//                }
//                .pickerStyle(.inline)
//                .labelsHidden()
//            }
//            
//            Section("Teleop") {
////                switch teleopViewGraphSelection {
////                case .overall:
////                    HomeView()
////                case .attemptProp:
////                    HomeView()
////                }
//                
//                Picker("Select Teleop Graph", selection: $teleopViewGraphSelection) {
//                    Text("Overall").tag(TeleopViewGraph.overall)
//                    Text("Amp / Speaker Proportion").tag(TeleopViewGraph.ampSpeakerProp)
//                }
//                .pickerStyle(.inline)
//                .labelsHidden()
//            }
            
        }
        .navigationTitle("Scoring")
        .navigationBarTitleDisplayMode(.inline)
    }
}

#Preview {
    CrescendoTeamDataView(data: [HelpfulVars().testmatchwin])
}
