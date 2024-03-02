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
//    case yourRanking
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
    @State private var sheetPresentationDetent: PresentationDetent = .large
    
    var body: some View {
        NavigationStack {
            Picker("Screen Selection", selection: $selectedScreen) {
                Text("Teams").tag(ScreenOptions.teams)
                Text("Matches").tag(ScreenOptions.matches)
                //                Text("Your Ranking").tag(ScreenOptions.yourRanking)
            }
            .pickerStyle(.segmented)
            .padding(.horizontal)
            .navigationTitle("Scouting")
            .navigationBarTitleDisplayMode(.inline)
            .padding(.top)
            .toolbar {
                ToolbarItem(placement: .principal) {
                    Button {
                        
                    } label: {
                        VStack {
                            Text("Scouting").font(.headline)
                            Text("Crescendo - Port Hueneme Regional").font(.subheadline)
                        }
                    }
                    .tint(.primary)
                }
                if (env.user!.permissions.generalScouting) {
                    ToolbarItem(placement: .topBarTrailing) {
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
                List {
                    TeamDataBar()
                    TeamDataBar()
                    TeamDataBar()
                }
            case .matches:
                List {
                    TeamDataBar()
                }
            }
            
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

