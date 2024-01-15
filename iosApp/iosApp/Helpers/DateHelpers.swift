//
//  DateHelpers.swift
//  E-Motion
//
//  Created by Jason Ballinger on 1/14/24.
//  Copyright © 2024 team2658. All rights reserved.
//

import Foundation

class DateHelpers {
    func formatUnixDateString(timestamp: Int64) -> String {
        let dateFormatter = DateFormatter()
        dateFormatter.dateStyle = .short
        dateFormatter.timeStyle = .short
        dateFormatter.locale = Locale.current
    //    dateFormatter.dateFormat = "M/dd H:mm"
        
        let date = Date(timeIntervalSince1970: TimeInterval(timestamp / 1000))
        return dateFormatter.string(from: date)
    }
}
