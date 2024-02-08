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
    @State private var promptReload = false
    
    var body: some View {
        NavigationStack {
            List {
                NavigationLink {
                    MeetingCreationView(user: user, reloader: $promptReload)
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
                        // list is reversed so most recent meetings are on top
                        ForEach(meetings!.reversed(), id: \.self) { meeting in
                            NavigationLink {
                                MeetingView(user: user, meeting: meeting)
                            } label: {
                                MeetingBar(meeting: meeting)
                                    .swipeActions(edge: .trailing) {
                                        //                                        Button {
                                        //                                            // archive meeting
                                        //                                        } label: {
                                        //                                            Label("Archive", systemImage: "archivebox.fill")
                                        //                                                .tint(.purple)
                                        //                                        }
                                        Button(role: .destructive) {
//                                            let response = try await
                                            Task {
                                                let _ = try await shared.EmotionClient().deleteMeeting(id: meeting._id, user: user)
                                                promptReload = true
                                            }
                                        } label: {
                                            Label("Delete", systemImage: "trash.fill")
                                        }
                                    }
                            }
                        }
//                        NavigationLink {
//                            // ArchivedMeetingsListView()
//                        } label: {
//                            Text("Archived")
//                                .font(.body.weight(.bold))
//                        }
                    }
                }
            }
            .navigationTitle("Meetings")
            .navigationBarTitleDisplayMode(.inline)
            .toolbar {
                ToolbarItem(placement: .topBarLeading) {
                    Button {
                        Task {
                            guard let response = try await shared.EmotionClient().getMeetings(user: user) else {alertMessage = "Unable to get list of meetings. Please try again later."
                                showingAlert = true
                                return
                            }
                            
                            meetings = response
                        }
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
        .onChange(of: promptReload) { _ in
            if (promptReload == true) {
                Task {
                    guard let response = try await shared.EmotionClient().getMeetings(user: user) else {alertMessage = "Unable to get list of meetings. Please try again later."
                        showingAlert = true
                        return
                    }
                    
                    meetings = response
                }
                promptReload = false
            }
        }
    }
}

#Preview {
    MeetingsListView(user: HelpfulVars().testuser, isPresented: .constant(true))
}
