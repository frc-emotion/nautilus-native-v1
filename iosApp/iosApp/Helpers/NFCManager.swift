//import CoreNFC
//
//class NFCManager: NSObject, NFCNDEFReaderSessionDelegate {
//    var nfcSession: NFCNDEFReaderSession?
//
//    func writeMessageToTag(data: Data, mimeType: String) {
//        guard NFCNDEFReaderSession.readingAvailable else {
//            // NFC reading is not available on this device
//            return
//        }
//
//        let payload = NFCNDEFPayload.init(format: .unknown, type: mimeType.data(using: .utf8)!, identifier: nil, payload: data)
//        payload.typeNameFormat = .media
//
//        let message = NFCNDEFMessage(records: [payload])
//
//        nfcSession = NFCNDEFReaderSession(delegate: self, queue: nil, invalidateAfterFirstRead: false)
//        nfcSession?.alertMessage = "Hold your iPhone near the NFC tag"
//
//        // Start the NFC session
//        nfcSession?.begin()
//    }
//
//    // Delegate method called when the NFC session is started successfully
//    func readerSession(_ session: NFCNDEFReaderSession, didDetect tags: [NFCNDEFTag]) {
//        guard let tag = tags.first else {
//            // No tag detected
//            return
//        }
//
//        session.connect(to: tag) { (error: Error?) in
//            if error != nil {
//                
//                return
//            }
//
//            // Writing data to the tag
//            tag.writeNDEF(message) { error in
//                if error != nil {
//                    print("There was an error.")
//                } else {
//                    print("We have written your data successfully!")
//                }
//                session.invalidate()
//            }
//        }
//    }
//
//    // Delegate method called when the NFC session encounters an error
//    func readerSession(_ session: NFCNDEFReaderSession, didInvalidateWithError error: Error) {
//        // Handle session invalidation or error
//    }
//}
//
//// Usage:
//let nfcManager = NFCManager()
//let yourData = "Your data here"
//let yourMimeType = "application/emotion"
//let dataToWrite = yourData.data(using: .utf8)!
//
//nfcManager.writeMessageToTag(data: dataToWrite, mimeType: yourMimeType)
