//
//  AllianceDataView.swift
//  E-Motion
//
//  Created by Jason Ballinger on 4/4/24.
//  Copyright © 2024 team2658. All rights reserved.
//

import SwiftUI
import shared

struct AllianceDataView: View {
    @State var alliance: [shared.Crescendo]
    var body: some View {
        AllianceRPView(alliance: $alliance)
        
        AllianceScoreProportionView(alliance: $alliance)
            .frame(maxHeight: 60)
        
        AlliancePhaseProportionView(alliance: $alliance)
            .frame(maxHeight: 60)
    }
}

#Preview {
    AllianceDataView(alliance: [])
}
