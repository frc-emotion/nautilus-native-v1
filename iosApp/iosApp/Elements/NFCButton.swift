//
//  NFCButton.swift
//  E-Motion
//
//  Created by Jason Ballinger on 10/25/23.
//  Copyright © 2023 team2658. All rights reserved.
//

import SwiftUI
import CoreNFC

struct NFCButton: UIViewRepresentable {
    @Binding var data : String
    @Binding var verifiedBy : String
    @State var title: String
    
    func makeUIView(context: UIViewRepresentableContext<NFCButton>) -> UIButton {
        let button = UIButton()
        button.configuration?.buttonSize = .small
        button.setTitle(title, for: .normal)
        button.backgroundColor = UIColor.blue
        button.addTarget(context.coordinator, action: #selector(context.coordinator.beginScan(_:)), for: .touchUpInside)
        return button
    }
    
    func updateUIView(_ uiView: UIButton, context: Context) {
        // Do nothing
    }
    
    func makeCoordinator() -> NFCButton.Coordinator {
        return Coordinator(data: $data, verifiedBy: $verifiedBy)
    }
    
    class Coordinator: NSObject, NFCNDEFReaderSessionDelegate {
        var session: NFCNDEFReaderSession?
        @Binding var data : String // must match type above
        @Binding var verifiedBy : String
        
        init(data: Binding<String>, verifiedBy: Binding<String>) {
            _data = data
            _verifiedBy = verifiedBy
        }
        
        @objc func beginScan(_ sender: Any) {
            guard NFCNDEFReaderSession.readingAvailable else {
                print("Error: Device does not support NFC.")
                return
            }
            
            session = NFCNDEFReaderSession(delegate: self, queue: .main, invalidateAfterFirstRead: true)
            session?.alertMessage = "Hold your iPhone near the Attendance card."
            session?.begin()
        }
        
        func readerSession(_ session: NFCNDEFReaderSession, didInvalidateWithError error: Error) {
            if let readerError = error as? NFCReaderError {
                if (readerError.code != .readerSessionInvalidationErrorFirstNDEFTagRead) && (readerError.code != .readerSessionInvalidationErrorUserCanceled) {
                    print("Error")
                }
            }
            
            self.session = nil
        }
        
        func readerSession(_ session: NFCNDEFReaderSession, didDetectNDEFs messages: [NFCNDEFMessage]) {
            guard
                let nfcMess = messages.first,
                let record = nfcMess.records.first,
                record.typeNameFormat == .media,
                let payload = String(data: record.payload, encoding: .utf8)
            else {
                return
            }
            let record2 = nfcMess.records[1]
            record2.typeNameFormat = .media
            let payload2 = String(data: record2.payload, encoding: .utf8)
            if (payload2 != nil) { self.verifiedBy = payload2! }
            self.data = payload
        }
    }
}

struct NFCButtonView: View {
    @Binding var data: String
    @Binding var verifiedBy: String
    var body: some View {
        NFCButton(data: self.$data, verifiedBy: self.$verifiedBy, title: "Log Attendance")
    }
}
