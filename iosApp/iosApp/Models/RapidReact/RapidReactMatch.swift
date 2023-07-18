//
//  RapidReactMatch.swift
//  Σ-Motion
//
//  Created by Jason Ballinger on 7/18/23.
//  Copyright © 2023 team2658. All rights reserved.
//

import Foundation

struct RapidReactMatch: Hashable, Codable {
    var id: String
    var competition: String
    var matchNumber: Int
    var teamNumber: Int
    var teamName: String
    var tarmac: Bool
    var autoLower: Int
    var autoUpper: Int
    var teleopLower: Int
    var teleopUpper: Int
    var cycleTime: String
    var mainShots: String
    var climbScore: Int
    var defensive: Bool
    var humanShot: Bool
    var rankingPoints: Array<String>
    var score: Int
    var won: Bool
    var comments: String
}
