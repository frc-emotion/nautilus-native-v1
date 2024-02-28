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
    @EnvironmentObject var env: EnvironmentModel
    @State var meetings: [shared.Meeting]?
    @State private var showingAlert = false
    @State var alertMessage = ""
    @Binding var isPresented: Bool
    @State private var promptReload = false
    
    var body: some View {
        NavigationStack {
            List {
                NavigationLink {
                    MeetingCreationView(reloader: $promptReload)
                        .environmentObject(env)
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
                                MeetingView(user: env.user!, meeting: meeting)
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
                                                //                                                let _ = try await shared.EmotionClient().deleteMeeting(id: meeting._id, user: user)
                                                try await env.dh.attendance.delete(id: meeting._id)
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
                        promptReload = true
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
            promptReload = true
        }
        .alert(alertMessage, isPresented: $showingAlert) {
            Button("Ok", role: .cancel) {}
        }
        .onChange(of: promptReload) { _ in
            if (promptReload == true) {
                Task {
                    let _ = env.dh.attendance.getAll { newMeetings in
                        meetings = newMeetings
                        promptReload = false
                    }
                    promptReload = false
                }
            }
        }
    }
}

#Preview {
    MeetingsListView(isPresented: .constant(true)).environmentObject({ () -> EnvironmentModel in
        let env = EnvironmentModel()
        env.user = HelpfulVars().testuser
        return env
    }() )
}
