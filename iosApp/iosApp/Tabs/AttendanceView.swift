//
//  AttendanceView.swift
//  Σ-Motion
//
//  Created by Jason Ballinger on 9/6/23.
//  Copyright © 2023 team2658. All rights reserved.
//

import SwiftUI
import SwiftNFC

struct AttendanceView: View {
    
    @ObservedObject var NFCR = NFCReader()
    @ObservedObject var NFCW = NFCWriter()
    
    func read() {
        NFCR.read()
    }
    
    func write() {
        NFCW.msg = NFCR.msg
        NFCW.write()
    }
    
    var body: some View {
        NavigationView {
           
            Button {
                read()
            } label: {
                Text("read")
            }

        }
    }
}

struct AttendanceView_Previews: PreviewProvider {
    static var previews: some View {
        AttendanceView()
    }
}
