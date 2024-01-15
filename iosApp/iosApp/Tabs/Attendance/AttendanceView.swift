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
    @State private var errorMsg: String = ""
    @State var user: shared.User
    @State private var meetingsPopoverDisplayed = false
    @State private var historyPopoverDisplayed = false
    @EnvironmentObject var vm: UserStateViewModel

    var body: some View {
        let hours: Int32 = if user.attendance.isEmpty {0} else {user.attendance.last?.totalHoursLogged ?? 0}
        let progress = Double(hours) / 36
        
        NavigationView {
            VStack {
                CircularProgressView(progress: progress, defaultColor: Color.green, progressColor: Color.green, innerText: "\(Int(hours))")
                    .frame(width: 150, height: 150)
                
                Text("Total Hours Required: 36")
                    .fontWeight(.bold)
                    .padding(.vertical, 25)
                
                Divider()
                    .padding(.bottom, 15)
                
                if (errorMsg != "") {
                    Text(errorMsg)
                        .fontWeight(.bold)
                        .foregroundColor(Color.red)
                        .padding(.bottom)
                }
                
                NFCButtonView(data: $data)
                    .frame(width: 160, height: 50, alignment: .center)
            }
            
            .onChange(of: data) { newData in
                if newData != "" {
                    Task {
                        let response = try await shared.EmotionClient().attendMeeting(user: user, meetingId: newData, tapTime: Int64((NSDate().timeIntervalSince1970) * 1000), failureCallback: { (errorMsgIn) -> () in
                            
                            if let errorMsgIn = errorMsgIn.data(using: .utf8, allowLossyConversion: false) {
                                Task {
                                    let json = try JSON(data: errorMsgIn)
                                    errorMsg = "Error: " + json["message"].stringValue
                                }
                            }
                        })
                        if let response {
                            Task { await vm.setUser(userIn: response) }
                            errorMsg = ""
                        }
                        data = ""
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
                ToolbarItem(placement: .topBarTrailing) {
                    Button {
                        // refresh
                    } label: {
                        Image(systemName: "arrow.clockwise")
                            .rotationEffect(.degrees(30.00))
                    }
                }
                // leads: view meetings
                ToolbarItem(placement: .topBarTrailing) {
                    if (user.accountType == shared.AccountType.lead || user.accountType == shared.AccountType.admin || user.accountType == shared.AccountType.superuser) {
                        Button {
                            meetingsPopoverDisplayed = true
                        } label: {
                            Image(systemName: "calendar")
                        }
                    }
                }
            }
            
            .popover(isPresented: $meetingsPopoverDisplayed, content: {
                NavigationView {
                    MeetingsListView(user: user, isPresented: $meetingsPopoverDisplayed)
                }
            })
            
            .popover(isPresented: $historyPopoverDisplayed) {
                AttendedMeetingsView(user: user, isPresented: $historyPopoverDisplayed)
            }
        }
    }
}

struct AttendanceView_Previews: PreviewProvider {
    static var previews: some View {
        AttendanceView(user: HelpfulVars().testuser)
    }
}
