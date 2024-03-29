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
    @Published var testuser = shared.TokenUser(_id: "0", firstname: "test", lastname: "user", username: "testuser1", email: "testuser1@example.com", subteam: shared.Subteam.none, roles: [""], accountType: shared.AccountType.superuser, accountUpdateVersion: 0, attendance: ["" : shared.UserAttendance(totalHoursLogged: 10, logs: [shared.MeetingLog(meetingId: "0", verifiedBy: "0")])], grade: 10, permissions: shared.UserPermissions(generalScouting: true, pitScouting: true, viewMeetings: true, viewScoutingData: true, blogPosts: true, deleteMeetings: true, makeAnnouncements: true, makeMeetings: true), phone: "1234567890", token: "640d5d586c4bc4abede30960")
    @Published var meeting = shared.Meeting(_id: "0", startTime: 0, endTime: 3600, type: "", description: "", value: 2, createdBy: "", attendancePeriod: "", isArchived: false, username: "")
    @Published var testmatchwin = shared.Crescendo(_id: "0", competition: "San Diego Qualifying", teamNumber: 2658, teamName: "E-Motion", matchNumber: 1, finalScore: 100, penaltyPointsEarned: 0, won: true, tied: false, comments: "meow", defensive: false, brokeDown: false, rankingPoints: 2, auto: CrescendoAuto(leave: true, ampNotes: 0, speakerNotes: 3), teleop: CrescendoTeleop(ampNotes: 2, speakerUnamped: 5, speakerAmped: 0), stage: CrescendoStage(state: CrescendoStageState.parked, harmony: 0, trapNotes: 0), ranking: CrescendoRankingPoints(melody: false, ensemble: false), createdBy: "0")
    @Published var testmatchlose = shared.Crescendo(_id: "0", competition: "San Diego Qualifying", teamNumber: 2658, teamName: "E-Motion", matchNumber: 2, finalScore: 100, penaltyPointsEarned: 0, won: false, tied: false, comments: "meow", defensive: false, brokeDown: false, rankingPoints: 0, auto: CrescendoAuto(leave: true, ampNotes: 0, speakerNotes: 3), teleop: CrescendoTeleop(ampNotes: 2, speakerUnamped: 5, speakerAmped: 0), stage: CrescendoStage(state: CrescendoStageState.parked, harmony: 0, trapNotes: 0), ranking: CrescendoRankingPoints(melody: false, ensemble: false), createdBy: "0")
    @Published var testmatchtie = shared.Crescendo(_id: "0", competition: "San Diego Qualifying", teamNumber: 2658, teamName: "E-Motion", matchNumber: 1, finalScore: 100, penaltyPointsEarned: 0, won: false, tied: true, comments: "meow", defensive: false, brokeDown: false, rankingPoints: 1, auto: CrescendoAuto(leave: true, ampNotes: 0, speakerNotes: 3), teleop: CrescendoTeleop(ampNotes: 2, speakerUnamped: 5, speakerAmped: 0), stage: CrescendoStage(state: CrescendoStageState.parked, harmony: 0, trapNotes: 0), ranking: CrescendoRankingPoints(melody: false, ensemble: false), createdBy: "0")

}
