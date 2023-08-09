//
//  ChargedUpMatch.swift
//  iosApp
//
//  Created by Jason Ballinger on 7/17/23.
//  Copyright © 2023 team2658. All rights reserved.
//

import Foundation

struct ChargedUpMatch: Hashable, Codable {
    var id: String
    var editHistory: Array<EditHistoryV1>
    var competition: String
    var matchNumber: Int
    var teamNumber: Int
    var teamName: String
    var RPEarned: Array<Bool>
    var totalRP: Int
    var autoPeriod: ChargedUpScoring
    var teleopPeriod: ChargedUpScoring
    var coneRate: Int
    var cubeRate: Int
    var linkScore: Int
    var autoDock: Bool
    var autoEngage: Bool
    var teleopDock: Bool
    var teleopEngage: Bool
    var parked: Bool
    var isDefensive: Bool
    var didBreak: Bool
    var penaltyCount: Int
    var score: Int
    var won: Bool
    var tied: Bool
    var comments: String
}

var ChargedUpMatches: [ChargedUpMatch] = load("ChargedUpMatches.json")
