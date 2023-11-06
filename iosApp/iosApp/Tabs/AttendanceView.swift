//
//  AttendanceView.swift
//  Σ-Motion
//
//  Created by Jason Ballinger on 9/6/23.
//  Copyright © 2023 team2658. All rights reserved.
//

// TODO: Simulator previews aren't working and returns a HumanReadableError, however works in production and on-device previews.

import SwiftUI
import shared
import CoreNFC
import UIKit

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
    let defaults = UserDefaults.standard
    let client = shared.EmotionClient()
    
//        let hours = user.attendance[0].totalHoursLogged

    var body: some View {
        let hours: Int32 = if user.attendance.isEmpty {0} else {user.attendance[0].totalHoursLogged}
        
        let progress = Double(hours) / 36
        
        VStack {
            Button {
                print(Int64((NSDate().timeIntervalSince1970) * 1000))
            } label: {
                Text("print current time")
            }
            
            Text("tag data: \(data)\n\n")
            
            CircularProgressView(progress: progress, defaultColor: Color.green, progressColor: Color.green, innerText: "\(Int(hours))")
                .frame(width: 150, height: 150)
            
            Divider()
                .padding(.vertical, 25)
            
            NFCButtonView(data: $data)
                .frame(width: 160, height: 50, alignment: .center)
        }
        
        .onChange(of: data) { newData in
            print("Data changed to \(newData)")
            print("attendance: \(User.Companion().fromJSON(json: defaults.string(forKey: "User"))!.attendance)")
            Task {
                let response = try await client.attendMeeting(user: User.Companion().fromJSON(json: defaults.string(forKey: "User")), meetingId: newData, tapTime:  Int64((NSDate().timeIntervalSince1970) * 1000), failureCallback: { (errorMsg) -> () in print(errorMsg)})
//                let newUser = try await client.getMe(user: User.Companion().fromJSON(json: defaults.string(forKey: "User")))
                defaults.set(response!.toJSON(), forKey: "User")
//                print(response)
            }
        }
        
        .onChange(of: storedUser) { newUser in
            user = shared.User.Companion().fromJSON(json: newUser)!
        }
    }
}

struct AttendanceView_Previews: PreviewProvider {
    static var previews: some View {
        AttendanceView(user: HelpfulVars().testuser)
    }
}
