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
    MeetingBar(meeting: Meeting(_id: "65711ee971a64fece9e56895", startTime: 1701910800000, endTime: 1701918000000, type: "meeting", description: "first general meeting", value: 2, createdBy: "640f5ba96c4bc4abede3096e"))
}
