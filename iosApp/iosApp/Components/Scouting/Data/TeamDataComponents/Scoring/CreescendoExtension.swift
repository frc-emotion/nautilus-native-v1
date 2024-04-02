//
//  CreescendoExtension.swift
//  E-Motion
//
//  Created by Jason Ballinger on 4/2/24.
//  Copyright © 2024 team2658. All rights reserved.
//

import Foundation
import shared

extension shared.Crescendo {
    var autoScore: Int32 {
        var totalScore: Int32 = 0
        if self.auto_.leave { totalScore += 2 }
        totalScore += self.auto_.ampNotes * 2
        totalScore += self.auto_.speakerNotes * 5
        return totalScore
    }
    
    var teleopScore: Int32 {
        var totalScore: Int32 = 0
        totalScore += self.teleop.ampNotes
        totalScore += self.teleop.speakerUnamped * 2
        totalScore += self.teleop.speakerAmped * 5
        return totalScore
    }
    
    var endgameScore: Int32 {
        var totalScore: Int32 = 0
        totalScore += self.stage.trapNotes * 5
        totalScore += self.stage.harmony * 2
        switch self.stage.state {
        case .parked:
            totalScore += 1
        case .onstage:
            totalScore += 3
        case .onstageSpotlit:
            totalScore += 4
        default:
            totalScore += 0
        }
        
        return totalScore
    }
    
    var teamScore: Int32 {
        return autoScore + teleopScore + endgameScore
    }
}
