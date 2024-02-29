//
//  BooleanSegmentedPickerView.swift
//  E-Motion
//
//  Created by Jason Ballinger on 2/28/24.
//  Copyright © 2024 team2658. All rights reserved.
//

import SwiftUI

struct BooleanSegmentedPickerView: View {
    @State var fieldName: String
    @Binding var data: Bool?
    @Environment(\.colorScheme) var colorScheme
    
    var body: some View {
        HStack {
            Text(fieldName)
            Spacer()
            Picker("", selection: $data) {
                Text("Yes").tag(true as Bool?)
                Text("No").tag(false as Bool?)
            }
            .pickerStyle(.segmented)
            .frame(maxWidth: 150)
        }
        .padding(.horizontal)
        .padding(.vertical, 10)
        .background(colorScheme == .dark ? Color.init(UIColor.systemGray5) : Color.white)
        .clipShape(RoundedRectangle(cornerRadius: 5))
        .padding(.horizontal)
    }
}

#Preview {
    BooleanSegmentedPickerView(fieldName: "Test", data: .constant(false))
}
