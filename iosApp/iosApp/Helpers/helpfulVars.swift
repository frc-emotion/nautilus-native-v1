//
//  helpfulVars.swift
//  E-Motion
//
//  Created by Jason Ballinger on 10/23/23.
//  Copyright © 2023 team2658. All rights reserved.
//

import Foundation
import shared

class HelpfulVars {
    @Published var testuser = shared.User(_id: "0", firstName: "Test", lastName: "User", username: "testuser", email: "testuser@example.com", phoneNumber: "1234567890", token: "test", subteam: shared.Subteam.executive, grade: 12, roles: [], accountType: shared.AccountType.superuser, accountUpdateVersion: 0, socials: [], parents: [], attendance: [], children: [], spouse: nil, donationAmounts: nil, employer: nil)
}
