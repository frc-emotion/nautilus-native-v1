//
//  MinusLabelView.swift
//  E-Motion
//
//  Created by Jason Ballinger on 2/27/24.
//  Copyright © 2024 team2658. All rights reserved.
//

import SwiftUI

struct MinusLabelView: View {
    var body: some View {
        ZStack {
            RoundedRectangle(cornerRadius: 5)
                .tint(Color.red)
            Image(systemName: "minus")
                .tint(Color.black)
        }
    }
}

#Preview {
    MinusLabelView()
}
