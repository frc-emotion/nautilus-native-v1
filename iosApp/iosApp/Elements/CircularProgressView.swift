//
//  CircularProgressView.swift
//  E-Motion
//
//  Created by Jason Ballinger on 9/24/23.
//  Copyright © 2023 team2658. All rights reserved.
//

import SwiftUI

struct CircularProgressView: View {
    
    let progress: Double
    let defaultColor: Color
    let progressColor: Color
    
    var body: some View {
        ZStack {
            Circle()
                .stroke(
                    defaultColor.opacity(0.5),
                    lineWidth: 30
                )
            Circle()
                .trim(from: 0, to: progress)
                .stroke(
                    progressColor,
                    style: StrokeStyle(
                        lineWidth: 30,
                        lineCap: .round
                    )
                )
                .rotationEffect(.degrees(-90))
        }
    }
}

#Preview {
    CircularProgressView(progress: 0.33, defaultColor: Color.blue, progressColor: Color.blue)
}
