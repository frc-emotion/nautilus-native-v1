//
//  Constants.swift
//  E-Motion
//
//  Created by Jason Ballinger on 2/26/24.
//  Copyright © 2024 team2658. All rights reserved.
//

import Foundation
import shared

class Constants {
    @Published var emptyUser = shared.TokenUser(_id: "", firstname: "", lastname: "", username: "", email: "", subteam: shared.Subteam.none, roles: [""], accountType: shared.AccountType.unverified, accountUpdateVersion: 0, attendance: ["" : shared.UserAttendance(totalHoursLogged: 0, logs: [shared.MeetingLog(meetingId: "", verifiedBy: "")])], grade: 0, permissions: shared.UserPermissions(generalScouting: false, pitScouting: false, viewMeetings: false, viewScoutingData: false, blogPosts: false, deleteMeetings: false, makeAnnouncements: false, makeMeetings: false), phone: "", token: "")
}
