//
//  EnvironmentModel.swift
//  E-Motion
//
//  Created by Jason Ballinger on 1/30/24.
//  Copyright © 2024 team2658. All rights reserved.
//

import Foundation
import Combine
import shared
import KeychainSwift

@MainActor
class EnvironmentModel: ObservableObject {
    @Published var dh: shared.DataHandler
    @Published var errorMessage: String?
    @Published var errorCode: Int?
    private var UserCancellable: AnyCancellable?
    private var AttendancePeriodCancellable: AnyCancellable?
//    private var ScoutingPeriodCancellable: AnyCancellable?
//    private var ScoutingCompetitionCancellable: AnyCancellable?
    
    @Published var user: shared.TokenUser?
    @Published var selectedAttendancePeriod = ""
    @Published var selectedScoutingPeriod = ""
    @Published var selectedScoutingCompetition = ""
    
    init() {
        dh = shared.DataHandler(routeBase: "https://api.team2658.org", databaseDriverFactory: IosDatabaseDriver()) {
            return KeychainSwift().get("userToken")
        } setToken: { newToken in
            if (newToken != nil) {
                KeychainSwift().set(newToken!, forKey: "userToken")
            } else {
                KeychainSwift().delete("userToken")
            }
        }
        
        user = dh.users.loadLoggedIn(onError: {e in
            self.errorMessage = e.message
            self.errorCode = e.code?.intValue
        })
        
        UserCancellable = $user.receive(on: DispatchQueue.main).sink { [weak self] newUser in
            self?.objectWillChange.send() // Trigger objectWillChange to notify SwiftUI
        }
        AttendancePeriodCancellable = $selectedAttendancePeriod.receive(on: DispatchQueue.main).sink { [weak self] _ in
            self?.objectWillChange.send()
        }
//        ScoutingPeriodCancellable = $selectedScoutingPeriod.receive(on: DispatchQueue.main).sink { [weak self] _ in
//            self?.objectWillChange.send()
//        }
//        ScoutingCompetitionCancellable = $selectedScoutingCompetition.receive(on: DispatchQueue.main).sink { [weak self] _ in
//            self?.objectWillChange.send()
//        }
        
        if (user != nil) {
            let attendanceKeys = Array(user!.attendance.keys)
            if attendanceKeys.first != nil {
                selectedAttendancePeriod = attendanceKeys.first!
            }
        }
    }

    func updateUser(newUser: TokenUser?) {
        user = newUser
        if (user != nil) {
            let attendanceKeys = Array(user!.attendance.keys)
            if attendanceKeys.first != nil {
                updateSelectedAttendancePeriod(newPeriod: attendanceKeys.first!)
            }
        }
    }
    
    func updateSelectedAttendancePeriod(newPeriod: String) {
        selectedAttendancePeriod = newPeriod
    }
    
//    func updateSelectedScoutingPeriod(newPeriod: String) {
//        selectedScoutingPeriod = newPeriod
//    }
//    
//    func updateSelectedScoutingCompetition(newCompetition: String) {
//        selectedScoutingCompetition = newCompetition
//    }
    
//    not using this right now, implement background syncing in the future
//    func refreshUser() async throws {
//        updateUser(newUser: try await dh.users.refreshLoggedIn(onError: {e in self.errorMessage = e.message; self.errorCode = e.code?.intValue}))
//    }
}
