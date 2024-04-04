//
//  AllianceScoreboardView.swift
//  E-Motion
//
//  Created by Jason Ballinger on 4/3/24.
//  Copyright © 2024 team2658. All rights reserved.
//

import SwiftUI
import shared

struct AllianceScoreboardView: View {
    var alliance: [shared.Crescendo]
    var body: some View {
        VStack {
            HStack(spacing: 20) {
                HStack(spacing: 10) {
                    ForEach(alliance, id: \.self) { item in
                        Text(String(item.teamNumber))
                            .frame(minWidth: 0, maxWidth: .infinity)
                        if (item != alliance.last) {
                            Divider()
                        }
                        
                    }
                }
                .frame(alignment: .leading)
                Divider()
                
                Text(String(alliance.first?.finalScore ?? -1))
                    .padding(.horizontal)
                    .frame(alignment: .trailing)
            }
            .frame(maxWidth: .infinity)
        }
        .frame(height: 40)
    }
}

#Preview {
    AllianceScoreboardView(alliance: [HelpfulVars().testmatchwin])
}
