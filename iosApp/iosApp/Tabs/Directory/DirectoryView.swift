//
//  DirectoryView.swift
//  iosApp
//
//  Created by Jason Ballinger on 7/11/23.
//  Copyright © 2023 team2658. All rights reserved.
//

import SwiftUI
import shared

struct DirectoryView: View {
    var body: some View {
        NavigationView {
            //            List(users) { user in
            //                NavigationLink {
            //                    UserView(user: user)
            //                } label: {
            //                    DirectoryBar(user: user)
            //                }
        }
        .navigationTitle("People")
    }
}

struct DirectoryView_Previews: PreviewProvider {
    static var previews: some View {
        DirectoryView()
    }
}
