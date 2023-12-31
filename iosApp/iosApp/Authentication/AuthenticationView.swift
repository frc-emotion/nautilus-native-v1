//
//  AuthenticationView.swift
//  E-Motion
//
//  Created by Jason Ballinger on 12/30/23.
//  Copyright © 2023 team2658. All rights reserved.
//

import Foundation
import SwiftUI

struct AuthenticationView: View {
    var body: some View {
        NavigationView {
            VStack {
                VStack {
                    Image(.launch)
                    Text("E-Motion")
                        .font(.largeTitle)
                        .fontWeight(.bold)
                }
                .padding(.top, 40)
                
                Spacer()
                
                NavigationLink(destination: LoginView(), label: {
                    Text("Login")
                        .fontWeight(.bold)
                        .frame(height: 30.0)
                        .frame(maxWidth: .infinity)
                        .cornerRadius(50)
                })
                .buttonStyle(.borderedProminent)
                .padding(.horizontal)
                
                NavigationLink(destination: AccountCreationView(), label: {
                    Text("Register")
                        .fontWeight(.bold)
                        .frame(height: 30.0)
                        .frame(maxWidth: .infinity)
                        .cornerRadius(50)
                        
                })
                .tint(.gray)
                .buttonStyle(.borderedProminent)
                .padding(.horizontal)
            }
            .padding(.bottom, 10)
        }
        
    }
}

#Preview {
    AuthenticationView()
}
