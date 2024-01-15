//
//  AttendedMeetingsView.swift
//  E-Motion
//
//  Created by Jason Ballinger on 1/8/24.
//  Copyright © 2024 team2658. All rights reserved.
//

import SwiftUI
import shared

struct AttendedMeetingsView: View {
    @State var user: shared.User
    @Binding var isPresented: Bool
    var body: some View {
        NavigationView {
            Text(/*@START_MENU_TOKEN@*/"Hello, World!"/*@END_MENU_TOKEN@*/)
                .navigationTitle("History")
                .navigationBarTitleDisplayMode(.inline)
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

#Preview {
    AttendedMeetingsView(user: HelpfulVars().testuser, isPresented: .constant(true))
}
