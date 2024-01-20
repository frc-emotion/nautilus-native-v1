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
    @Published var testuser = shared.User(_id: "0", firstName: "Test", lastName: "User", username: "testuser", email: "testuser@example.com", phoneNumber: "1234567890", token: "test", subteam: shared.Subteam.executive, grade: 12, roles: [], accountType: shared.AccountType.superuser, accountUpdateVersion: 0, socials: [], parents: [], attendance: [UserAttendance(totalHoursLogged: 7, completedMarketingAssignment: false, logs: [])], children: [], spouse: nil, donationAmounts: nil, employer: nil, customRoleMessage: "Test Message")
    @Published var meeting = shared.Meeting(_id: "1", startTime: 0, endTime: 3600, type: "meeting", description: "sample meeting", value: 4, createdBy: "0")
}
