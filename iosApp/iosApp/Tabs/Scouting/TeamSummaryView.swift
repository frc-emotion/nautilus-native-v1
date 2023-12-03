//
//  TeamSummaryView.swift
//  Σ-Motion
//
//  Created by Jason Ballinger on 7/26/23.
//  Copyright © 2023 team2658. All rights reserved.
//

import SwiftUI

struct TeamSummaryView: View {
    @State private var showComments = false
    
    var body: some View {
        NavigationView {
            VStack {
                HStack {
                    Text("E-Motion")
                        .font(.title2)
                        .fontWeight(.bold)
                        .multilineTextAlignment(.center)
                    Text("#2658")
                        .font(.title2)
                        .fontWeight(.bold)
                    Text("(100%)")
                        .foregroundColor(.secondary)
                }
                .lineLimit(1)
                .padding(.top, 5.0)
                HStack {
                    HStack {
                        Text("Avg score: ")
                        Text("140.00")
                    }
                    .padding(.trailing, 10.0)
                    HStack {
                        Text("RPs: ")
                        Text("3.75")
                    }
                    .padding(.leading, 10.0)
                    
                }
                .padding(.top, 0.1)
                HStack {
                    Spacer()
                    NavigationLink {
                        TeamCommentsView()
                    } label: {
                        Text("Comments")
                            .frame(width: 120.0, height: 22.5)
                    }
                    .buttonStyle(.borderedProminent)
                    Spacer()
                    NavigationLink {
                        TeamInfoView()
                    } label: {
                        Text("More Info")
                            .frame(width: 120.0, height: 22.5)
                    }
                    .buttonStyle(.borderedProminent)
                    Spacer()
                }
                .padding(.vertical, 5.0)
            }
            .padding()
            .overlay(
                RoundedRectangle(cornerRadius: 16)
                    .stroke(.secondary, lineWidth: 1)
            )
            .padding(.horizontal)
        .padding(.vertical, 4)
        }
    }
}

struct TeamSummaryView_Previews: PreviewProvider {
    static var previews: some View {
        TeamSummaryView()
    }
}
