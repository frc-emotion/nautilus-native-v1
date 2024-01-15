//
//  MeetingView.swift
//  E-Motion
//
//  Created by Jason Ballinger on 1/10/24.
//  Copyright © 2024 team2658. All rights reserved.
//

import SwiftUI
import shared

struct MeetingView: View {
    @State var user: shared.User
    @State var meeting: shared.Meeting
    @State var writer = NFCWriter()
    @State private var creatorName = ""
    
    var body: some View {
        VStack {
            VStack {
                HStack {
                    Text(meeting.description_)
                        .font(.largeTitle)
                        .fontWeight(.bold)
                    Spacer()
                }
                .padding(.bottom, 2.5)
                HStack {
                    Text(DateHelpers().formatUnixDateString(timestamp: meeting.startTime))
                    Spacer()
                    Text(DateHelpers().formatUnixDateString(timestamp: meeting.endTime))
                }
                .padding(.bottom, 1)
                HStack {
                    Text("\(meeting.value) Credits")
                    Spacer()
                    Text("Created by \(creatorName)")
                    
                }
            }
            
            Button {
                writer.scan(dataIn: meeting._id)
            } label: {
                Text("Create Meeting Tag")
                    .frame(height: 30.0)
                    .frame(maxWidth: .infinity)
            }
            .buttonStyle(.borderedProminent)
            .padding(.vertical, 7)
            
            
            Divider()
            
            ScrollView {
                // list users & attended status
            }
            
        }
        .padding(.horizontal)
        .padding(.top)
        .onAppear() {
            Task {
                guard let response = try await shared.EmotionClient().getUserById(id: meeting.createdBy, user: user) else {
                    creatorName = "Unknown"
                    return
                }
                creatorName = "\(response.firstName) \(response.lastName)"
            }
        }
    }
}

#Preview {
    MeetingView(user: HelpfulVars().testuser, meeting: HelpfulVars().meeting)
}
