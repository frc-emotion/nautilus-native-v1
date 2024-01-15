//
//  NFCWriter.swift
//  E-Motion
//
//  Created by Jason Ballinger on 1/7/24.
//  Copyright © 2024 team2658. All rights reserved.
//

import Foundation
import CoreNFC

class NFCWriter: NSObject, ObservableObject, NFCNDEFReaderSessionDelegate {
    var data = ""
    var session: NFCNDEFReaderSession?
    
    func scan(dataIn: String) {
        data = dataIn
        session = NFCNDEFReaderSession(delegate: self, queue: nil, invalidateAfterFirstRead: true)
        session?.alertMessage = "Hold your iPhone near the Attendance card."
        session?.begin()
    }
    
    func readerSession(_ session: NFCNDEFReaderSession, didInvalidateWithError error: Error) {
        
    }
    
    func readerSession(_ session: NFCNDEFReaderSession, didDetectNDEFs messages: [NFCNDEFMessage]) {
        
    }
    
    func readerSession(_ session: NFCNDEFReaderSession, didDetect tags: [NFCNDEFTag]) {
        let str: String = data
        if tags.count > 1 {
            let retryInterval = DispatchTimeInterval.milliseconds(500)
            session.alertMessage = "More than one tag was detected, please try again."
            DispatchQueue.global().asyncAfter(deadline: .now() + retryInterval, execute: {
                session.restartPolling()
            })
            return
        }
        let tag = tags.first!
        session.connect(to: tag, completionHandler: {(error: Error?) in
            if error != nil {
                session.alertMessage = "Unable to connect to tag"
                session.invalidate()
                return
            }
            tag.queryNDEFStatus(completionHandler: {(ndefstatus: NFCNDEFStatus, capacity: Int, error: Error?) in
                guard error == nil else {
                    session.alertMessage = "Unable to connect to tag"
                    session.invalidate()
                    return
                }
                
                switch ndefstatus {
                case .notSupported:
                    session.alertMessage = "Tag is not supported"
                    session.invalidate()
                case .readOnly:
                    session.alertMessage = "Tag is read only"
                    session.invalidate()
                case .readWrite:
                    tag.writeNDEF(.init(records: [NFCNDEFPayload(format: .media, type: "application/emotion".data(using: .utf8)!, identifier: Data(), payload: "\(str)".data(using: .utf8)!)]), completionHandler: {(error: Error?) in
                        if error != nil {
                            session.alertMessage = "Failed to write message to tag"
                        } else {
                            session.alertMessage = "Success!"
                        }
                        session.invalidate()
                    })
                @unknown default:
                    session.alertMessage = "Unknown tag type"
                    session.invalidate()
                }
            })
        })
    }
}
