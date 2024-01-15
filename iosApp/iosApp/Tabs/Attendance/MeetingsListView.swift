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
    @Binding var isPresented: Bool
    var body: some View {
        NavigationView {
            List {
                NavigationLink {
                    MeetingCreationView()
                } label: {
                    HStack {
                        Image(systemName: "pswlus")
                        Text("Create a New Meeting")
                    }
                    .foregroundStyle(.blue)
                    .font(.body.weight(.bold))
                }
                
                Section(header: Text("Meetings")) {
                    if (meetings != nil) {
                        ForEach(meetings!, id: \.self) { meeting in
                            NavigationLink {
                                MeetingView(meeting: meeting)
                            } label: {
                                MeetingBar(meeting: meeting)
                            }
                        }
                        NavigationLink {
                            // ArchivedMeetingsListView()
                        } label: {
                            Text("Archived")
                                .font(.body.weight(.bold))
                        }
                    }
                }
            }
            .navigationTitle("Meetings")
            .navigationBarTitleDisplayMode(.inline)
            .toolbar {
                ToolbarItem(placement: .topBarLeading) {
                    Button {
                        
                    } label: {
                        Image(systemName: "arrow.clockwise")
                            .rotationEffect(.degrees(30.00))
                    }
                }
                ToolbarItem(placement: .topBarTrailing) {
                    Button {
                        isPresented = false
                    } label: {
                        Text("Done")
                    }
                }
            }
        }
        .onAppear() {
            Task {
                guard let response = try await shared.EmotionClient().getMeetings(user: user) else {alertMessage = "Unable to get list of meetings. Please try again later."
                    showingAlert = true
                    return
                }
                
                meetings = response
            }
        }
        .alert(alertMessage, isPresented: $showingAlert) {
            Button("Ok", role: .cancel) {}
        }
    }
}

#Preview {
    MeetingsListView(user: HelpfulVars().testuser, isPresented: .constant(true))
}
