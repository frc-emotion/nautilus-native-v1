//
//  CrescendoScoutingFormView.swift
//  E-Motion
//
//  Created by Jason Ballinger on 2/27/24.
//  Copyright © 2024 team2658. All rights reserved.
//

import SwiftUI
import shared

extension shared.CrescendoStageState {
    var displayName: String {
        switch(self) {
        case .parked:
            return "Parked"
        case .onstage:
            return "Onstage"
        case .onstageSpotlit:
            return "Onstage (Spotlit)"
        default:
            return "Not Parked"
        }
    }
}

struct CrescendoScoutingFormView: View {
    @EnvironmentObject var env: EnvironmentModel
    @Environment(\.colorScheme) var colorScheme

    @State private var showingError = false
    @State private var errorMessage = ""
    
    @State var competitions: [String] = [""]
    
    @State var competition: String?
    @State var teamNumber: Int?
    @State var matchNumber: Int?
    @State var AutoAmpNotes: Int?
    @State var AutoSpeakerNotes: Int?
    @State var AutoLeftAllianceArea: Bool?
    @State var TeleopAmpNotes: Int?
    @State var TeleopSpeakerNotes: Int?
    @State var TeleopSpeakerNotesAmplified: Int?
    @State var EndgameParked: shared.CrescendoStageState?
    @State var EndgameHarmony: Int?
    @State var EndgameTrapNotes: Int?
    @State var RPMelody: Bool?
    @State var RPEnsemble: Bool?
    @State var RobotDefensive: Bool?
    @State var RobotBrokeDown: Bool?
    @State var PenaltyPointsEarned: Int?
    @State var FinalScore: Int?
    @State var FinalGameResultWin: Bool?
    @State var FinalGameResultTie: Bool?
    @State var FinalComments = ""
    @State private var isBusy = false
    
    func clearForm() {
        competition = nil
        teamNumber = nil
        matchNumber = nil
        AutoAmpNotes = nil
        AutoSpeakerNotes = nil
        AutoLeftAllianceArea = nil
        TeleopAmpNotes = nil
        TeleopSpeakerNotes = nil
        TeleopSpeakerNotesAmplified = nil
        EndgameParked = nil
        EndgameHarmony = nil    
        EndgameTrapNotes = nil
        RPMelody = nil
        RPEnsemble = nil
        RobotDefensive = nil
        RobotBrokeDown = nil
        PenaltyPointsEarned = nil
        FinalScore = nil
        FinalGameResultWin = nil
        FinalGameResultTie = nil
        FinalComments = ""
    }
    
    var body: some View {
        NavigationStack {
            ScrollView {
                VStack(spacing: 20) {
                    VStack (spacing: 10) {
                        HStack {
                            Text("Match Information")
                                .font(.title2)
                                .padding(.leading)
                            Spacer()
                        }
                        
                        HStack {
                            Text("Competition: ")
                            Spacer()
                            Picker("Competition", selection: $competition) {
                                Text("Select one").tag(nil as String?)
                                ForEach(competitions, id: \.self) { competition in
                                    Text(competition).tag(competition as String?)
                                }
                                if (false) { Text("").disabled(true) }
                            }
                            .padding(.trailing, -15)
                            .pickerStyle(.menu)
                            .tint(Color.accentColor)
                        }
                        .padding(.vertical, 10.0)
                        .padding(.horizontal)
                        .background(colorScheme == .dark ? Color.init(UIColor.systemGray5) : Color.white)
                        .clipShape(RoundedRectangle(cornerRadius: 5))
                        .padding(.horizontal)
                        
                        ScoutingNumberInputView(fieldName: "Team Number", data: $teamNumber)
                        ScoutingNumberInputView(fieldName: "Match Number", data: $matchNumber)
                    }
                    VStack(spacing: 10) {
                        HStack {
                            Text("Autonomous")
                                .font(.title2)
                                .padding(.leading)
                            Spacer()
                        }
                        ScoutingIncrementerView(fieldName: "Amp Notes", data: $AutoAmpNotes)
                        ScoutingIncrementerView(fieldName: "Speaker Notes", data: $AutoSpeakerNotes)
                        BooleanSegmentedPickerView(fieldName: "Left Alliance Area?", data: $AutoLeftAllianceArea)
                    }
                    VStack(spacing: 10) {
                        HStack {
                            Text("Teleop")
                                .font(.title2)
                                .padding(.leading)
                            Spacer()
                        }
                        ScoutingIncrementerView(fieldName: "Amp Notes", data: $TeleopAmpNotes)
                        ScoutingIncrementerView(fieldName: "Speaker Notes", data: $TeleopSpeakerNotes)
                        ScoutingIncrementerView(fieldName: "Speaker Notes (Amplified)", data: $TeleopSpeakerNotesAmplified)
                    }
                    VStack(spacing: 10) {
                        HStack {
                            Text("Endgame")
                                .font(.title2)
                                .padding(.leading)
                            Spacer()
                        }
                        HStack {
                            Text("Parked / Spotlit:")
                            Spacer()
                            Picker("", selection: $EndgameParked) {
                                Text("Select one").tag(nil as shared.CrescendoStageState?)
                                ForEach(shared.CrescendoStageState.entries, id: \.self) { item in
                                    Text(item.displayName).tag(item as shared.CrescendoStageState?)
                                }
                                if (false) { Text("").disabled(true) }
                            }
                            .padding(.trailing, -15)
                            .pickerStyle(.menu)
                            .tint(Color.accentColor)
                            

                        }
                        .padding(.vertical, 10.0)
                        .padding(.horizontal)
                        .background(colorScheme == .dark ? Color.init(UIColor.systemGray5) : Color.white)
                        .clipShape(RoundedRectangle(cornerRadius: 5))
                        .padding(.horizontal)
                        
                        HStack {
                            Text("Harmony:")
                            Spacer()
                            Picker("", selection: $EndgameHarmony) {
                                Text("Select one").tag(nil as Int?)
                                Text("None").tag(0 as Int?)
                                Text("2 Robot").tag(1 as Int?)
                                Text("3 Robot").tag(2 as Int?)
                                if (false) { Text("").disabled(true) }
                            }
                            .padding(.trailing, -15)
                            .pickerStyle(.menu)
                            .tint(Color.accentColor)
                        }
                        .padding(.vertical, 10.0)
                        .padding(.horizontal)
                        .background(colorScheme == .dark ? Color.init(UIColor.systemGray5) : Color.white)
                        .clipShape(RoundedRectangle(cornerRadius: 5))
                        .padding(.horizontal)
                        
                        // harmony
                        ScoutingIncrementerView(fieldName: "Trap Notes", data: $EndgameTrapNotes)
                    }
                    VStack(spacing: 10) {
                        HStack {
                            Text("Ranking Points")
                                .font(.title2)
                                .padding(.leading)
                            Spacer()
                        }
                        BooleanSegmentedPickerView(fieldName: "Melody", data: $RPMelody)
                        BooleanSegmentedPickerView(fieldName: "Ensemble", data: $RPEnsemble)
                    }
                    VStack(spacing: 10) {
                        HStack {
                            Text("Robot Info")
                                .font(.title2)
                                .padding(.leading)
                            Spacer()
                        }
                        BooleanSegmentedPickerView(fieldName: "Defense Bot?", data: $RobotDefensive)
                        BooleanSegmentedPickerView(fieldName: "Robot Broke Down?", data: $RobotBrokeDown)
                    }
                    VStack(spacing: 10) {
                        HStack {
                            Text("Results")
                                .font(.title2)
                                .padding(.leading)
                            Spacer()
                        }
                        ScoutingNumberInputView(fieldName: "Penalty Points", data: $PenaltyPointsEarned)
                        ScoutingNumberInputView(fieldName: "Final Score", data: $FinalScore)
                        // game result
                        BooleanSegmentedPickerView(fieldName: "Tied?", data: $FinalGameResultTie)
                        BooleanSegmentedPickerView(fieldName: "Won?", data: $FinalGameResultWin)
                        
                        VStack {
                            HStack {
                                Text("Comments:")
                                    .padding(.leading)
                                Spacer()
                            }
                            TextField("Enter Comments", text: $FinalComments, axis: .vertical)
                                .padding(.horizontal)
                        }
                        .padding(.vertical, 10.0)
                        .background(colorScheme == .dark ? Color.init(UIColor.systemGray5) : Color.white)
                        .clipShape(RoundedRectangle(cornerRadius: 5))
                        .padding(.horizontal)
                    }
                    
                    // submit button
                    VStack(spacing: 10) {
                        Button {
                            isBusy = true
                            var totalRP = 0
                            if (RPMelody!) { totalRP = totalRP + 1 }
                            if (RPEnsemble!) { totalRP = totalRP + 1 }
                            if (FinalGameResultTie!) { totalRP = totalRP + 1 }
                            if (FinalGameResultWin!) { totalRP = totalRP + 1 }
                            env.dh.crescendo.upload(data: shared.CrescendoSubmission(auto: shared.CrescendoAuto(leave: AutoLeftAllianceArea!, ampNotes: Int32(AutoAmpNotes!), speakerNotes: Int32(AutoSpeakerNotes!)), comments: FinalComments, competition: competition!, defensive: RobotDefensive!, matchNumber: Int32(matchNumber!), penaltyPointsEarned: Int32(PenaltyPointsEarned!), ranking: shared.CrescendoRankingPoints(melody: RPMelody!, ensemble: RPEnsemble!), rankingPoints: Int32(totalRP), score: Int32(FinalScore!), stage: shared.CrescendoStage(state: EndgameParked!, harmony: Int32(EndgameHarmony!), trapNotes: Int32(EndgameTrapNotes!)), teamNumber: Int32(teamNumber!), teleop: shared.CrescendoTeleop(ampNotes: Int32(TeleopAmpNotes!), speakerUnamped: Int32(TeleopSpeakerNotes!), speakerAmped: Int32(TeleopSpeakerNotesAmplified!)), tied: FinalGameResultTie!, won: FinalGameResultWin!, brokeDown: RobotBrokeDown!)) { err in
                                errorMessage = err.message
                                showingError = true
                                isBusy = false
                            } completionHandler: { match, err in
                                guard match != nil else {
                                    errorMessage = err?.localizedDescription ?? "Unknown Error Occured"
                                    showingError = true
                                    isBusy = false
                                    return
                                }
                                clearForm()
                                isBusy = false
                            }
                            isBusy = false
                        } label: {
                            if (isBusy) {
                                AnyView(ProgressView())
                                    .frame(height: 30.0)
                                    .frame(maxWidth: .infinity)
                                    .cornerRadius(50)
                                    .tint(Color.secondary)
                            } else {
                                Text("Submit")
                                    .fontWeight(.bold)
                                    .frame(height: 30.0)
                                    .frame(maxWidth: .infinity)
                                    .cornerRadius(50)
                            }
                        }
                        .buttonStyle(.borderedProminent)
                        .padding(.horizontal)
                        .disabled(!(competition != nil && teamNumber != nil && AutoAmpNotes != nil && AutoSpeakerNotes != nil && AutoLeftAllianceArea != nil && TeleopAmpNotes != nil && TeleopSpeakerNotes != nil && TeleopSpeakerNotesAmplified != nil && EndgameParked != nil && EndgameHarmony != nil && EndgameTrapNotes != nil && RPMelody != nil && RPEnsemble != nil && RobotDefensive != nil && RobotBrokeDown != nil && PenaltyPointsEarned != nil && FinalScore != nil && FinalGameResultTie != nil && FinalGameResultWin != nil))
                        
                        Button {
                            clearForm()
                        } label: {
                            Text("Clear Form")
                                .fontWeight(.bold)
                                .frame(height: 30.0)
                                .frame(maxWidth: .infinity)
                                .cornerRadius(50)
                        }
                        .buttonStyle(.borderedProminent)
                        .padding(.horizontal)
                        .tint(.red)
                        .disabled(!(competition != nil || teamNumber != nil || AutoAmpNotes != nil || AutoSpeakerNotes != nil || AutoLeftAllianceArea != nil || TeleopAmpNotes != nil || TeleopSpeakerNotes != nil || TeleopSpeakerNotesAmplified != nil || EndgameParked != nil || EndgameHarmony != nil || EndgameTrapNotes != nil || RPMelody != nil || RPEnsemble != nil || RobotDefensive != nil || RobotBrokeDown != nil || PenaltyPointsEarned != nil || FinalScore != nil || FinalGameResultTie != nil || FinalGameResultWin != nil))
                        
                    }
                }
                .padding(.bottom, 10)
                .navigationTitle("Crescendo Form")
                
            }
            .background(colorScheme == .dark ? Color.black : Color.init(UIColor.systemGray6))
        }
        .onAppear() {
            competitions = env.dh.seasons.getComps(year: 2024, onCompleteSync: { comps in
                competitions = comps
            })
        }
        .alert(errorMessage, isPresented: $showingError) {
            Button("Ok", role: .cancel) {}
        }
        .onChange(of: FinalGameResultTie) { new in
            if (new ?? false && FinalGameResultWin == true) { FinalGameResultWin = false }
        }
        .onChange(of: FinalGameResultWin) { new in
            if (new ?? false && FinalGameResultTie == true) { FinalGameResultTie = false }
        }
        .scrollDismissesKeyboard(.immediately)
    }
}

#Preview {
    CrescendoScoutingFormView().environmentObject({ () -> EnvironmentModel in
        let env = EnvironmentModel()
        env.user = HelpfulVars().testuser
        return env
    }() )
}
