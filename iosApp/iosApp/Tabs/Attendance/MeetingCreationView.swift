//
//  MeetingCreationView.swift
//  E-Motion
//
//  Created by Jason Ballinger on 1/7/24.
//  Copyright © 2024 team2658. All rights reserved.
//

import SwiftUI

struct MeetingCreationView: View {
    enum MeetingType: String, CaseIterable, Identifiable {
        case general, leads, kickoff, competition, other
        var id: Self { self }
    }
    
    @State private var startDate = Date.now
    @State private var endDate = Date.now + 7200
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
                }
                
                Section(header: Text("Meeting Time")) {
                    DatePicker("Start Time", selection: $startDate, displayedComponents: [.date, .hourAndMinute])
                        .datePickerStyle(.compact)
                    
                    DatePicker("End Time", selection: $endDate, displayedComponents: [.date, .hourAndMinute])
                        .datePickerStyle(.compact)
                    
                    Text("Meeting Hours")
                }
                Button {
                    
                } label: {
                    Text("Create Meeting")
                        .frame(height: 30.0)
                        .frame(maxWidth: .infinity)
                }
                .navigationTitle("Create a Meeting")
            }
            
        }
    }
}

#Preview {
    MeetingCreationView()
}
