//
//  User.swift
//  iosApp
//
//  Created by Jason Ballinger on 7/11/23.
//  Copyright © 2023 team2658. All rights reserved.
//

import Foundation

struct User: Hashable, Equatable, Codable, Identifiable {
    var id: Int
    var firstname: String
    var lastname: String
    var username: String
    var email: String
    var password: String?
    var isAdmin: Bool
    var isVerified: Bool
    var token: String?
}

extension User {
    static let deviceUser = User(id: 1000, firstname: "Jason", lastname: "Deviceuser", username: "jasondeviceuser", email: "jasondeviceuser@gmail.com", isAdmin: true, isVerified: true, token: "abcdefghijklmnopqrstuvwxyz1234567890")
}
