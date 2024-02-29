//
//  ScoutingTextInputView.swift
//  E-Motion
//
//  Created by Jason Ballinger on 2/28/24.
//  Copyright © 2024 team2658. All rights reserved.
//

import SwiftUI

struct ScoutingNumberInputView: View {
    @State var fieldName: String
    @Binding var data: Int?
    @Environment(\.colorScheme) var colorScheme
    
    var body: some View {
        HStack {
            Text("\(fieldName): ")
            TextField("Enter \(fieldName)", value: $data, format: .number)
                .keyboardType(.numberPad)
                .multilineTextAlignment(.trailing)
        }
        .padding(.vertical, 12.5)
        .padding(.horizontal)
        .background(colorScheme == .dark ? Color.init(UIColor.systemGray5) : Color.white)
        .clipShape(RoundedRectangle(cornerRadius: 5))
        .padding(.horizontal)
    }
}

#Preview {
    ScoutingNumberInputView(fieldName: "Test", data: .constant(0))
}
