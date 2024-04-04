//
//  AllianceRPView.swift
//  E-Motion
//
//  Created by Jason Ballinger on 4/3/24.
//  Copyright © 2024 team2658. All rights reserved.
//

import SwiftUI
import shared

struct AllianceRPView: View {
    var alliance: [shared.Crescendo]
    
    var body: some View {
        HStack {
            VStack(spacing: 10) {
                Text("Result").fontWeight(.bold)
                if (alliance.first!.won) { Text("2").font(.title) }
                else if (alliance.first!.tied) { Text("1").font(.title) }
                else { Text("0").font(.title) }
            }
            .frame(minWidth: 0, maxWidth: .infinity)
            Divider()
            
            VStack(spacing: 10) {
                Text("Melody").fontWeight(.bold)
                if (alliance.first!.ranking.melody) { Text("1").font(.title) }
                else { Text("0").font(.title) }
            }
            .frame(minWidth: 0, maxWidth: .infinity)
            Divider()
            
            VStack(spacing: 10) {
                Text("Ensemble").fontWeight(.bold)
                if (alliance.first!.ranking.ensemble) { Text("1").font(.title) }
                else { Text("0").font(.title) }
            }
            .frame(minWidth: 0, maxWidth: .infinity)
//            Divider()
//            
//            VStack {
//                Text("Co-op").fontWeight(.bold)
//                if (alliance.first)
//            }
        }
        .frame(height: 75)
        .frame(maxWidth: .infinity)
    }
}

#Preview {
    AllianceRPView(alliance: [HelpfulVars().testmatchwin])
}
