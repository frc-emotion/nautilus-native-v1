//
//  ChargedUpFormView.swift
//  E-Motion
//
//  Created by Jason Ballinger on 10/14/23.
//  Copyright © 2023 team2658. All rights reserved.
//

import SwiftUI

struct ChargedUpFormView: View {
    var body: some View {
        ScrollView {
            VStack {
                Text("Match Info:")
                    .font(.title2)
                .fontWeight(.bold)
                HStack {
                    Text("Competition:")
                    Spacer()
                    
                }
            }
            
        }
    }
}

#Preview {
    ChargedUpFormView()
}
