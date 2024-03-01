//
//  MeetingCreationView.swift
//  E-Motion
//
//  Created by Jason Ballinger on 1/7/24.
//  Copyright © 2024 team2658. All rights reserved.
//

import SwiftUI
import shared

func roundedDate() -> Date {
    let calendar = Calendar.current
    let minutes = calendar.component(.minute, from: Date())
    let nearestHour = -(minutes % 60)
    if let roundedDate = calendar.date(byAdding: .minute, value: nearestHour, to: Date()) {
                return roundedDate
            } else {
                return Date()
            }
}

struct MeetingCreationView: View {
    enum MeetingType: String, CaseIterable, Identifiable {
        case general, leads, kickoff, workshop, competition, outreach, mentor, other
        var id: Self { self }
    }
    
    @Binding var reloader: Bool
    @Binding var isPresented: Bool
    @Environment(\.presentationMode) var presentationMode
    @EnvironmentObject var env: EnvironmentModel
    
    @State private var showError = false
    @State private var errorMsg = ""
    
    // form fields
    @State private var startDate = roundedDate()
    @State private var endDate = roundedDate() + 7200
    @State private var meetingValue = 2
    @State private var meetingName = ""
    @State private var meetingType: MeetingType = .general
    
    var body: some View {
        NavigationView {
            Form {
                Section(header: Text("Meeting Details")) {
                    HStack {
                        Text("Meeting Name ")
                        TextField("Name", text: $meetingName)
                            .tag("meetingNameTextField")
                        //                            .submitLabel(.done)
                    }
                    
                    Picker("Meeting Type", selection: $meetingType) {
                        ForEach(MeetingType.allCases) { type in
                            Text(type.rawValue.capitalized)
                        }
                    }
                    .pickerStyle(.menu)
                    
                    if (meetingType == MeetingType.other) {
                        TextField("Describe Meeting Type", text: $meetingName)
                            .tag("otherMeetingTypeTextField")
                    }
                }
                
                Section(header: Text("Meeting Time")) {
                    DatePicker("Start Time", selection: $startDate, displayedComponents: [.date, .hourAndMinute])
                        .datePickerStyle(.compact)
                    
                    DatePicker("End Time", selection: $endDate, displayedComponents: [.date, .hourAndMinute])
                        .datePickerStyle(.compact)
                    
                    HStack {
                        Text("Meeting Hours: \(meetingValue)")
                        Spacer()
                        Stepper("", value: $meetingValue, in: 0...100, step: 1)
                    }
                }
                Button {
                    guard (meetingName != "") else {
                        errorMsg = "Please fill in all fields"
                        showError = true
                        return
                    }
                    // TODO: change attendance period hardcoding, used dh.seasons.getAttendancePeriod() and a dropdown
                    // TODO: Automatically navigate backwards when new meeting successfully created
                    env.dh.attendance.create(startTime: Int64(startDate.timeIntervalSince1970), endTime: Int64(endDate.timeIntervalSince1970), type: meetingType.rawValue, description: meetingName, value: Int32(meetingValue), attendancePeriod: "2024spring") { err in
                        errorMsg = err.description()
                        showError = true
                    } completionHandler: { newMeeting, err in
                        guard newMeeting != nil else {
                            if (err != nil) {
                                errorMsg = err!.localizedDescription
                            } else {
                                errorMsg = "Unknown Error Occured"
                            }
                            return
                        }
                        reloader = true
                        isPresented = false
                    }
                } label: {
                    Text("Create Meeting")
                        .frame(height: 30.0)
                        .frame(maxWidth: .infinity)
                }
                .navigationTitle("Create a Meeting")
                .navigationBarTitleDisplayMode(.inline)
//                .navigationBarHidden(true)
                .onChange(of: startDate) { newDate in
                    if (startDate.timeIntervalSince1970 > endDate.timeIntervalSince1970) {
                        endDate = startDate
                    }
                    meetingValue = Int(Double(endDate.timeIntervalSince1970) - Double(startDate.timeIntervalSince1970)) / 3600
                }
                .onChange(of: endDate) { newDate in
                    if (endDate.timeIntervalSince1970 < startDate.timeIntervalSince1970) {
                        endDate = startDate
                    }
                    //                    meetingValue = Int((endDate - startDate) / 3600)
                    meetingValue = Int(Double(endDate.timeIntervalSince1970) - Double(startDate.timeIntervalSince1970)) / 3600
                }
                .alert(isPresented: $showError) {
                    Alert(
                        title: Text("Error"),
                        message: Text(errorMsg),
                        dismissButton: .default(Text("Ok"))
                    )
                }
                .toolbar {
                    ToolbarItem(placement: .topBarTrailing) {
                        Button {
                            isPresented = false
                        } label: {
                            Text("Done")
                        }
                    }
                }
            }
        }
    }
}

#Preview {
    MeetingCreationView(reloader: .constant(false), isPresented: .constant(true))
}
