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
    var verifiedBy = ""
    var session: NFCNDEFReaderSession?
    
    func scan(dataIn: String, verifiedByIn: String) {
        data = dataIn
        verifiedBy = verifiedByIn
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
        let verifiedBy: String = verifiedBy
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
                session.invalidate(errorMessage: "Unable to connect to tag")
                return
            }
            tag.queryNDEFStatus(completionHandler: {(ndefstatus: NFCNDEFStatus, capacity: Int, error: Error?) in
                guard error == nil else {
                    session.invalidate(errorMessage: "Unable to connect to tag")
                    return
                }
                
                switch ndefstatus {
                case .notSupported:
                    session.invalidate(errorMessage: "Tag is not supported")
                case .readOnly:
                    session.invalidate(errorMessage: "Tag is read only")
                case .readWrite:
                    tag.writeNDEF(.init(records: [NFCNDEFPayload(format: .media, type: "application/emotion".data(using: .utf8)!, identifier: Data(), payload: "\(str)".data(using: .utf8)!), NFCNDEFPayload(format: .media, type: "application/emotion".data(using: .utf8)!, identifier: Data(), payload: "\(verifiedBy)".data(using: .utf8)!)]), completionHandler: {(error: Error?) in
                        if error != nil {
                            session.invalidate(errorMessage: "Failed to write message to tag")
                        } else {
                            session.alertMessage = "Success!"
                        }
                        session.invalidate()
                    })
                @unknown default:
                    session.invalidate(errorMessage: "Unknown tag type")
                }
            })
        })
    }
}
