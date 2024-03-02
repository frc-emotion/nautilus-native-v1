//
//  MeetingBar.swift
//  E-Motion
//
//  Created by Jason Ballinger on 1/7/24.
//  Copyright © 2024 team2658. All rights reserved.
//

import SwiftUI
import shared

struct MeetingBar: View {
    @State var meeting: shared.Meeting
    var body: some View {
        VStack {
            HStack {
                Text(meeting.description_)
                    .fontWeight(.bold)
                Spacer()
            }
            HStack {
                Text("\(DateHelpers().formatUnixDateString(timestamp: meeting.startTime)) to \(DateHelpers().formatUnixDateString(timestamp: meeting.endTime))")
                Spacer()
            }
        }
    }
}

#Preview {
    MeetingBar(meeting: shared.Meeting(_id: "0", startTime: 0, endTime: 3600, type: "general", description: "sample meeting", value: 2, createdBy: "0", attendancePeriod: "0", isArchived: false, username: "testuser1"))
}
