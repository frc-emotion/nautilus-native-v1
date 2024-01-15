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
    @State var meeting: shared.Meeting
    var body: some View {
        Text(/*@START_MENU_TOKEN@*/"Hello, World!"/*@END_MENU_TOKEN@*/)
    }
}

#Preview {
    MeetingView(meeting: HelpfulVars().meeting)
}
