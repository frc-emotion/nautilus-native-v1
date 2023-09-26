//
//  AttendanceView.swift
//  Σ-Motion
//
//  Created by Jason Ballinger on 9/6/23.
//  Copyright © 2023 team2658. All rights reserved.
//

import SwiftUI
import SwiftNFC
import shared

struct AttendanceView: View {
    
    @ObservedObject var NFCR = NFCReader()
    @ObservedObject var NFCW = NFCWriter()
    
    func read() {
        NFCR.read()
    }
    
    func write(message: String) {
        NFCW.msg = message
        NFCW.write()
    }
    
    var body: some View {
        VStack {
            CircularProgressView(progress: 0.35, defaultColor: Color.green, progressColor: Color.green, innerText: "37")
                .frame(width: 150, height: 150)
            
            Divider()
                .padding(.vertical, 25)
            
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
