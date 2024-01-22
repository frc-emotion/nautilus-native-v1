//
//  ForgotPasswordView.swift
//  Σ-Motion
//
//  Created by Jason Ballinger on 7/18/23.
//  Copyright © 2023 team2658. All rights reserved.
//

import SwiftUI

struct ForgotPasswordView: View {
    
    @State var email = ""
    
    var body: some View {
        NavigationStack {
            VStack {
                Text("Forgot Password")
                    .font(.largeTitle)
                    .fontWeight(.bold)
                
                TextField("Email", text: $email)
                    .padding(.horizontal)
                    .frame(height: 45.0)
                    .overlay(RoundedRectangle(cornerRadius: 5.0).strokeBorder(Color(UIColor.separator)))
                    .padding(.horizontal)
                    .autocapitalization(.none)
                    .disableAutocorrection(true)
                
                Button (action: {
                    Task {
                        
                    }
                }) {
                    Text("Request Reset")
                        .frame(height: 30.0)
                        .frame(maxWidth: .infinity)
                }
                .padding(.horizontal)
                .buttonStyle(.borderedProminent)
                .padding(.top, 5)
            }
        }
    }
}

struct ForgotPasswordView_Previews: PreviewProvider {
    static var previews: some View {
        ForgotPasswordView()
    }
}
