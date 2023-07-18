//
//  ChargedUpScoring.swift
//  iosApp
//
//  Created by Jason Ballinger on 7/17/23.
//  Copyright © 2023 team2658. All rights reserved.
//

import Foundation

struct ChargedUpScoring: Hashable, Codable {
    var botScore: Int
    var botCones: Int
    var botCubes: Int
    var midScore: Int
    var midCones: Int
    var midCubes: Int
    var topScore: Int
    var topCones: Int
    var topCubes: Int
    var totalScore: Int
    var totalCones: Int
    var totalCubes: Int
    var coneRate: Int
    var cubeRate: Int
}
