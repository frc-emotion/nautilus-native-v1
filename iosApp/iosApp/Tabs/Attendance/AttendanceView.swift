//
//  AttendanceView.swift
//  Σ-Motion
//
//  Created by Jason Ballinger on 9/6/23.
//  Copyright © 2023 team2658. All rights reserved.
//

import SwiftUI
import shared
import CoreNFC
import UIKit
import SwiftyJSON

func getUTCDate(date: Int) {
    let dateFormatter = DateFormatter()
    dateFormatter.timeZone = TimeZone(abbreviation: "UTC")
}

struct AttendanceView: View {
    //    let helpers = AttendanceHelpers()
    @State private var data: String = ""
    @State private var verifiedBy: String = ""
    @State private var errorMsg: String = ""
    @State private var meetingsPopoverDisplayed = false
    @State private var historyPopoverDisplayed = false
    @EnvironmentObject var env: EnvironmentModel

    var body: some View {
//        let att = env.user?.attendance.values.reversed().first
        let att = env.user?.attendance[env.selectedAttendancePeriod]
        let hours = att?.totalHoursLogged ?? 0
        let progress = Double(hours) / 36
        let test: String? = env.user?.attendance.keys.first
        let attObj = env.user?.attendance
        let _: Int32 = if(test == nil || attObj == nil ) { 0 } else { attObj![test!]?.totalHoursLogged ?? 0 }
        
        NavigationStack {
            VStack {
                CircularProgressView(progress: progress, defaultColor: Color.green, progressColor: Color.green, innerText: "\(Int(hours))")
                    .frame(width: 150, height: 150)
                
                Text("Total Hours Required: 36")
                    .fontWeight(.bold)
                    .padding(.vertical, 25)
                
                
                Divider()
                    .padding(.bottom, 15)
                if (UIDevice.current.systemName == "iOS") {
                    if (errorMsg != "") {
                        Text(errorMsg)
                            .fontWeight(.bold)
                            .foregroundColor(Color.red)
                            .padding(.bottom)
                    }
                    
                    NFCButtonView(data: $data, verifiedBy: $verifiedBy)
                        .frame(width: 160, height: 50, alignment: .center)
                } else {
                    Text("Please use an iPhone to log attendance.")
                        .fontWeight(.bold)
                        .foregroundColor(Color.red)
                        .padding(.bottom)
                }
            }
            
            .onChange(of: data) { newData in
                if (newData != "") {
                    env.dh.attendance.attend(meetingId: newData, time: Int64((NSDate().timeIntervalSince1970) * 1000), verifiedBy: verifiedBy) { err in
                        errorMsg = err.description()
                    } completionHandler: { updatedUser, err in
                        guard updatedUser != nil else {
                            if (err != nil) {
                                errorMsg = err!.localizedDescription
                            } else {
                                errorMsg = "Unknown Error Occured"
                            }
                            return
                        }
                        errorMsg = ""
                        Task { env.updateUser(newUser: updatedUser) }
                    }
                }
            }
            .navigationBarTitleDisplayMode(.inline)
            .navigationTitle("Attendance")
            .toolbar {
                // view previous attendance
                //                ToolbarItem(placement: .topBarLeading) {
                //                    Button {
                //                        historyPopoverDisplayed = true
                //                    } label: {
                //                        Image(systemName: "clock.arrow.circlepath")
                //                    }
                //                }
//                ToolbarItem(placement: .topBarTrailing) {
//                    Button {
//                        // refresh
//                    } label: {
//                        Image(systemName: "arrow.clockwise")
//                            .rotationEffect(.degrees(30.00))
//                    }
//                }
                // leads: view meetings
                ToolbarItem(placement: .topBarTrailing) {
                    if (env.user != nil) {
                        if (env.user!.accountType == shared.AccountType.lead || env.user!.accountType == shared.AccountType.admin || env.user!.accountType == shared.AccountType.superuser) {
                            NavigationLink {
                                MeetingsListView()
                                    .environmentObject(env)
                            } label: {
                                Image(systemName: "calendar")
                            }
                        }
                    }
                }
            }
            .popover(isPresented: $historyPopoverDisplayed) {
                AttendedMeetingsView(user: env.user!, isPresented: $historyPopoverDisplayed)
            }
        }
    }
}

struct AttendanceView_Previews: PreviewProvider {
    static var previews: some View {
        AttendanceView().environmentObject({ () -> EnvironmentModel in
            let env = EnvironmentModel()
            env.user = HelpfulVars().testuser
            return env
        }() )
    }
}
