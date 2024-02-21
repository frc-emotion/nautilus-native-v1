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
    @Published var testuser = shared.TokenUser(_id: "0", firstname: "test", lastname: "user", username: "testuser1", email: "testuser1@example.com", subteam: shared.Subteam.none, roles: [""], accountType: shared.AccountType.superuser, accountUpdateVersion: 0, attendance: ["" : shared.UserAttendance(totalHoursLogged: 10, logs: [shared.MeetingLog(meetingId: "0", verifiedBy: "0")])], grade: 10, permissions: shared.UserPermissions(generalScouting: true, pitScouting: true, viewMeetings: true, viewScoutingData: true, blogPosts: true, deleteMeetings: true, makeAnnouncements: true, makeMeetings: true), phone: "1234567890", token: "0")
    @Published var meeting = shared.Meeting(_id: "0", startTime: 0, endTime: 3600, type: "", description: "", value: 2, createdBy: "", attendancePeriod: "", isArchived: false, username: "")
}
