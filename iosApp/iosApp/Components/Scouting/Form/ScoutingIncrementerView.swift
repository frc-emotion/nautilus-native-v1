//
//  ScoutingIncrementerView.swift
//  E-Motion
//
//  Created by Jason Ballinger on 2/27/24.
//  Copyright © 2024 team2658. All rights reserved.
//

import SwiftUI

struct ScoutingIncrementerView: View {
    @Environment(\.colorScheme) var colorScheme
    @State var fieldName: String
    @Binding var data: Int?
    
    var body: some View {
        VStack {
            HStack {
                Text(fieldName)
                    .font(.title3)
                Spacer()
                Button(action: {
                    data = nil
                }, label: {
                    Text("Reset")
                })
            }
            .padding(.horizontal, 5)
            HStack {
                // text field
                TextField("...", value: $data, format: .number)
                    .keyboardType(.numberPad)
                    .font(Font.system(size: 40))
                Spacer()
                HStack(spacing: 10) {
                    Button(action: {
                        if (data != nil) {
                            guard data! > 0 else {
                                return
                            }
                            data = data! - 1
                        }
                        else {
                            data = 0
                        }
                    }, label: {
                        MinusLabelView()
                            .frame(width: 50, height:50)
                    })
                    Button(action: {
                        if (data != nil) {
                            data = data! + 1
                        } else {
                            data = 1
                        }
                    }, label: {
                        PlusLabelView()
                            .frame(width: 50, height:50)
                    })
                }
            }
            .padding(.horizontal, 5)
        }
        .padding(.vertical, 20)
        .padding(.horizontal)
        .background(colorScheme == .dark ? Color.init(UIColor.systemGray5) : Color.white)
        .clipShape(RoundedRectangle(cornerRadius: 5))
        .padding(.horizontal)
    }
}

#Preview {
    ScoutingIncrementerView(fieldName: "Test", data: .constant(0))
        .frame(maxHeight: .infinity)
        .background(Color.yellow)
}
