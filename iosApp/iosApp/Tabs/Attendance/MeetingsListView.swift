//
//  MeetingsListView.swift
//  E-Motion
//
//  Created by Jason Ballinger on 1/7/24.
//  Copyright © 2024 team2658. All rights reserved.
//

import SwiftUI
import shared

struct MeetingsListView: View {
    @State var meetings: [shared.Meeting]?
    @State var user: shared.User
    @State private var showingAlert = false
    @State var alertMessage = ""
    var body: some View {
        NavigationView {
            List {
                NavigationLink {
                    MeetingCreationView()
                } label: {
                    HStack {
                        Image(systemName: "plus")
                        Text("Create a New Meeting")
                    }
                    .foregroundStyle(.blue)
                    .font(.body.weight(.bold))
                    
                }
                
                Section(header: Text("Meetings")) {
                    if (meetings != nil) {
                        ForEach(meetings!, id: \.self) { meeting in
                            MeetingBar(meeting: meeting)
                        }
                    }
                }
            }
            .navigationTitle("Meetings")
            .navigationBarTitleDisplayMode(.inline)
        }
        .onAppear() {
            Task {
                let response = try await shared.EmotionClient().getMeetings(user: user)
                if (response != nil) {
                    meetings = response
                    print(meetings)
                } else {
                    alertMessage = "Unable to get list of meetings. Please try again later."
                    showingAlert = true
                }
            }
        }
        .alert(alertMessage, isPresented: $showingAlert) {
            Button("Ok", role: .cancel) {}
        }
    }
}

#Preview {
    MeetingsListView(user: HelpfulVars().testuser)
}
