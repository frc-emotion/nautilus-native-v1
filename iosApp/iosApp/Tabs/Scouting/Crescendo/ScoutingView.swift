//
//  ScoutingView.swift
//  iosApp
//
//  Created by Jason Ballinger on 7/9/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import shared

private enum ScreenOptions {
    case teams
    case matches
//    case picklist
}

struct ScoutingView: View {
    @EnvironmentObject var env: EnvironmentModel
    var body: some View {
        VStack {
            if (env.user!.permissions.viewScoutingData) {
                ScoutingViewDataPermission()
                    .environmentObject(env)
            } else if (env.user!.permissions.generalScouting) {
                CrescendoScoutingFormView()
                    .environmentObject(env)
            } else {
                Text("You do not have permission to scout.")
            }
        }
    }
}

private struct ScoutingViewDataPermission: View {
    @EnvironmentObject var env: EnvironmentModel
    @State private var selectedScreen: ScreenOptions = ScreenOptions.teams
    @State private var sheetIsPresented = false
    @State private var competitionSelectorPresented = false
    @State private var sheetPresentationDetent: PresentationDetent = .large
    @State private var competitions: [String]?
    @State private var selectedCompetition: String?
    @State private var scoutingData: [shared.Crescendo]?
    @State private var selectedTeam: Int32?
    @State private var selectedMatch: Int32?
    
    func calculateAvgRP(data: [shared.Crescendo]) -> Double {
        var rp: Int32 = 0
        for item in data {
            rp += item.rankingPoints
        }
        return Double(rp) / Double(data.count)
    }
    
    var filteredScoutingData: [shared.Crescendo]? {
        if scoutingData != nil {
            if (selectedCompetition != nil) {
                let filtered = scoutingData!.filter { match in
                    if match.competition == selectedCompetition {
                        return true
                    } else {
                        return false
                    }
                }
                return filtered
            } else {
                return nil
            }
        } else {
            return nil
        }
    }
    
    var groupDataByTeam: [Int32 : [shared.Crescendo]]? {
        if filteredScoutingData != nil {
            return Dictionary(grouping: filteredScoutingData!, by: {$0.teamNumber})
        } else {
            return nil
        }
    }
    
    var groupedDataByMatchNumber: [Int32 : [shared.Crescendo]]? {
        if filteredScoutingData != nil {
            return Dictionary(grouping: filteredScoutingData!, by: {$0.matchNumber})
        } else {
            return nil
        }
    }
    
    var body: some View {
        NavigationStack {
            Picker("Screen Selection", selection: $selectedScreen) {
                Text("Teams").tag(ScreenOptions.teams)
//                Text("Matches").tag(ScreenOptions.matches)
//                Text("Picklist").tag(ScreenOptions.picklist)
            }
            .pickerStyle(.segmented)
            .padding(.horizontal)
            .navigationTitle("Scouting")
            .navigationBarTitleDisplayMode(.inline)
            .padding(.top)
            .toolbar {
                ToolbarItem(placement: .principal) {
                    Menu {
                        VStack {
                            if (competitions != nil) {
                                ForEach (competitions!, id: \.self) { comp in
                                    Button {
                                        selectedCompetition = comp
                                    } label: {
                                        HStack {
                                            Text(comp.description)
                                                .lineLimit(1)
                                                .truncationMode(.middle)
                                            Spacer()
                                            if (selectedCompetition == comp) {
                                                Image(systemName: "checkmark")
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    } label: {
                        VStack {
                            Text("Scouting").font(.headline)
                            Text("Crescendo - \(selectedCompetition?.description ?? "No Competition Selected")").font(.subheadline)
                        }
                    }
                    .tint(.primary)
                }
                ToolbarItem(placement: .topBarTrailing) {
                    if (env.user!.permissions.generalScouting) {
                        Button {
                            sheetIsPresented = true
                            sheetPresentationDetent = .large
                        } label: {
                            Image(systemName: "plus")
                        }
                    }
                }
            }
            Divider()
                .sheet(isPresented: $sheetIsPresented, content: {
                    if #available(iOS 16.4, *) {
                        CrescendoScoutingFormView()
                            .environmentObject(env)
                            .presentationDetents([
                                .large,
                                .fraction(1/12)
                            ], selection: $sheetPresentationDetent)
                            .interactiveDismissDisabled()
                            .presentationBackgroundInteraction(
                                .enabled(upThrough: .fraction(1/12))
                            )
                    } else {
                        CrescendoScoutingFormView()
                            .environmentObject(env)
                            .presentationDetents([
                                .large,
                                .fraction(1/12)
                            ], selection: $sheetPresentationDetent)
                            .interactiveDismissDisabled()
                    }
                })
            
            switch selectedScreen{
            case .teams:
                VStack {
                    if (selectedCompetition != nil) {
                        if (filteredScoutingData != nil && groupDataByTeam != nil) {
                            List(selection: $selectedTeam) {
                                if let groupedData = groupDataByTeam?.sorted(by: { calculateAvgRP(data: $0.value) > calculateAvgRP(data: $1.value) }) {
                                    ForEach(groupedData, id: \.key) { keyValue in
                                        let (teamNumber, matches) = keyValue
                                        NavigationLink {
                                            ScoutedTeamView(team: teamNumber, data: filteredScoutingData!, selection: $selectedTeam)
                                        } label: {
                                            TeamDataBar(team: teamNumber, data: matches)
                                        }
                                    }
                                } else {
                                    Text("")
                                }
                            }
                        } else {
                            Spacer()
                            Text("No Scouting Data")
                                .fontWeight(.bold)
                                .foregroundStyle(Color.red)
                            Spacer()
                        }
                    } else {
                        Spacer()
                        Text("Please select a Competition")
                            .fontWeight(.bold)
                        Spacer()
                    }
                }
            case .matches:
                if (selectedCompetition != nil) {
                    if (filteredScoutingData != nil && groupedDataByMatchNumber != nil) {
                        List(selection: $selectedMatch) {
                            if let groupedData = groupedDataByMatchNumber?.sorted(by: { $0.key < $1.key }) {
                                ForEach(groupedData, id: \.key) { keyValue in
                                    let (matchNumber, data) = keyValue
                                    NavigationLink {
                                        ScoutedMatchView(match: matchNumber, data: data)
                                    } label: {
                                        Text("Match \(matchNumber)")
                                    }
                                }
                            }
                     
                        }
                    } else {
                        Spacer()
                        Text("No Scouting Data")
                            .fontWeight(.bold)
                        Spacer()
                    }
                }
                else {
                    Spacer()
                    Text("Please select a Competition")
                        .fontWeight(.bold)
                    Spacer()
                }
            }
        }
        .onAppear() {
            competitions = env.dh.seasons.getComps(year: 2024) { comps in
                competitions = comps
            }
            scoutingData = env.dh.crescendo.getAll(onCompleteSync_: { data in
                scoutingData = data
            })
            selectedCompetition = competitions?.last?.description
        }
    }
}

struct ScoutingView_Previews: PreviewProvider {
    static var previews: some View {
        ScoutingView().environmentObject({ () -> EnvironmentModel in
            let env = EnvironmentModel()
            env.user = HelpfulVars().testuser
            return env
        }() )
    }
}

