//
//  RapidReactTeam.swift
//  Σ-Motion
//
//  Created by Jason Ballinger on 7/18/23.
//  Copyright © 2023 team2658. All rights reserved.
//

import Foundation

struct RapidReactTeam: Hashable, Codable, Identifiable {
    var id: Int // Team Number
    var name: String
    var matches: Array<RapidReactMatch>
}
