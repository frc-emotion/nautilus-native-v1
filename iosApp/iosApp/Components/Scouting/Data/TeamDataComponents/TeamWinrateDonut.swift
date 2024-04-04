//
//  TeamWinrateDonut.swift
//  E-Motion
//
//  Created by Jason Ballinger on 3/23/24.
//  Copyright © 2024 team2658. All rights reserved.
//

import SwiftUI

// 0 to 1
struct TeamWinrateDonut: View {
    @State var winrate: Double
    @State var tierate: Double
    @State var lossrate: Double
    let lineWidth: CGFloat = 10
    var body: some View {
        ZStack {
            ZStack {
                Circle()
                    .trim(from: 0, to: winrate)
                    .stroke(
                        Color.green.opacity(1),
                        lineWidth: self.lineWidth
                    )
                    .rotationEffect(.degrees(-90))
                Circle()
                    .trim(from: winrate, to: winrate + lossrate)
                    .stroke(
                        Color.red.opacity(1),
                        lineWidth: self.lineWidth
                    )
                    .rotationEffect(.degrees(-90))
                Circle()
                    .trim(from: winrate + lossrate, to: 1)
                    .stroke(
                        Color.yellow.opacity(1),
                        lineWidth: self.lineWidth
                    )
                    .rotationEffect(.degrees(-90))
            }
//            Text(String(winrate * 100) + "%")
//                .font(.custom(
//                    "SFProRounded",
//                    size: 65,
//                    relativeTo: .title))
//                .fontWeight(.heavy)
        }
    }
}

#Preview {
    TeamWinrateDonut(winrate: 0.67, tierate: 0.1, lossrate: 0.23)
}
