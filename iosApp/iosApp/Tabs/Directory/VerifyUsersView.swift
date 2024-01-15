//
//  VerifyUsersView.swift
//  E-Motion
//
//  Created by Jason Ballinger on 1/10/24.
//  Copyright © 2024 team2658. All rights reserved.
//

import SwiftUI
import shared

struct VerifyUsersView: View {
    @State var users: [shared.User]?
    @Binding var presented: Bool
    var body: some View {
        NavigationView {
            List {
                
            }
            .navigationTitle("Verify Users")
            .navigationBarTitleDisplayMode(.inline)
            .toolbar {
                ToolbarItem(placement: .topBarTrailing) {
                    Button {
                        presented = false
                    } label: {
                        Text("Done")
                    }
                }
            }
        }
    }
}

#Preview {
    VerifyUsersView(presented: .constant(true))
}
