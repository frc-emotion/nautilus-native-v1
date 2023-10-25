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

struct NFCButton: UIViewRepresentable {
    @Binding var data : String
    
    func makeUIView(context: UIViewRepresentableContext<NFCButton>) -> UIButton {
        let button = UIButton()
        button.setTitle("Read NFC", for: .normal)
        button.backgroundColor = UIColor.blue
        button.addTarget(context.coordinator, action: #selector(context.coordinator.beginScan(_:)), for: .touchUpInside)
        return button
    }
    
    func updateUIView(_ uiView: UIButton, context: Context) {
        // Do nothing
    }
    
    func makeCoordinator() -> NFCButton.Coordinator {
        return Coordinator(data: $data)
    }
    
    class Coordinator: NSObject, NFCNDEFReaderSessionDelegate {
        var session: NFCNDEFReaderSession?
        @Binding var data : String // must match type above
        
        init(data: Binding<String>) {
            _data = data
        }
        
        @objc func beginScan(_ sender: Any) {
            guard NFCNDEFReaderSession.readingAvailable else {
                print("Error: Device does not support NFC.")
                return
            }
            
            session = NFCNDEFReaderSession(delegate: self, queue: .main, invalidateAfterFirstRead: true)
            session?.alertMessage = "Hold your iPhone near an NFC tag."
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
                record.typeNameFormat == .absoluteURI || record.typeNameFormat == .nfcWellKnown,
                let payload = String(data: record.payload, encoding: .utf8)
            else {
                return
            }
            
            print(payload)
            self.data = payload
        }
    }
}

struct AttendanceView: View {
    @State var data = ""
    
    var body: some View {
        let progress = 0.2
 
        VStack {
            Text(data)
            
            CircularProgressView(progress: progress, defaultColor: Color.green, progressColor: Color.green, innerText: "\(Int(progress * 35))")
                .frame(width: 150, height: 150)
            
//            Divider()
//                .padding(.vertical, 25)
            
            NFCButton(data: self.$data)
            
//            Button {
//                
//            } label: {
//                Text("read")
//            }
        }
    }
}

struct AttendanceView_Previews: PreviewProvider {
    static var previews: some View {
        AttendanceView()
    }
}
