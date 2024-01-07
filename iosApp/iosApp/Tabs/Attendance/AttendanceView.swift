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
    @State var data: String = ""
    @State var errorMsg: String = ""
    
    // AppStorage user updates automatically and stores User to the @State when it updates
    @AppStorage("User") var storedUser: String!
    @State var user: shared.User
    @State private var meetingsPopoverDisplayed = false
    @State private var historyPopoverDisplayed = false
    let defaults = UserDefaults.standard
    let client = shared.EmotionClient()

    var body: some View {
        let hours: Int32 = if user.attendance.isEmpty {0} else {user.attendance[0].totalHoursLogged}
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
                        let response = try await client.attendMeeting(user: User.Companion().fromJSON(json: defaults.string(forKey: "User")), meetingId: newData, tapTime:  Int64((NSDate().timeIntervalSince1970) * 1000), failureCallback: { (errorMsgIn) -> () in
                            
                            if let errorMsgIn = errorMsgIn.data(using: .utf8, allowLossyConversion: false) {
                                Task {
                                    let json = try JSON(data: errorMsgIn)
                                    errorMsg = "Error: " + json["message"].stringValue
                                }
                            }
                        })
                        if let response {
                            defaults.set(response.toJSON(), forKey: "User")
                            errorMsg = ""
                        }
                        data = ""
                    }
                }
            }
            .onChange(of: storedUser) { newUser in
                user = shared.User.Companion().fromJSON(json: newUser)!
            }
            .navigationBarTitleDisplayMode(.inline)
            .navigationTitle("Attendance")

            .toolbar {
                // view previous attendance
                ToolbarItem(placement: .topBarLeading) {
                    Button {
                        historyPopoverDisplayed = true
                    } label: {
                        Image(systemName: "clock.arrow.circlepath")
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
            
            .popover(isPresented: $meetingsPopoverDisplayed) {
                MeetingsListView(user: user)
            }
            
            .popover(isPresented: $historyPopoverDisplayed) {
                // do nothing
            }
        }
    }
}

struct AttendanceView_Previews: PreviewProvider {
    static var previews: some View {
        AttendanceView(user: HelpfulVars().testuser)
    }
}
