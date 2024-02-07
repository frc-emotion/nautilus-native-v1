import SwiftUI
import shared
import KeychainSwift
import Network

@main
struct iOSApp: App {
    @StateObject var envModel = EnvironmentModel()

    var body: some Scene {
        WindowGroup {
            NavigationView {
                ApplicationSwitcher()
            }
            .navigationViewStyle(.stack)
            .environmentObject(envModel)
        }
    }
}

struct ApplicationSwitcher: View {
    @EnvironmentObject var env: EnvironmentModel
    var body: some View {
        
        if (env.user != nil) {
            //        if (true) {
            TabView {
                //                Unnecessary tabs are hidden right now
                //
                //                HomeView()
                //                    .tabItem {
                //                        Label("Home", systemImage: "house.fill")
                //                    }
                //
                //                ScoutingView()
                //                    .navigationTitle("Scouting")
                //                    .tabItem {
                //                        Label("Scouting", systemImage: "chart.bar.doc.horizontal.fill")
                //                    }
                if (env.user!.accountType != shared.AccountType.unverified) {
                    AttendanceView()
                        .tabItem {
                            Label("Attendance", systemImage: "calendar")
                        }
                        .environmentObject(env)
                    
//                    DirectoryView(dh: $dh)
//                        .tabItem {
//                            Label("People", systemImage: "person.2.fill")
//                        }
                }
//                SettingsView(dh: $dh)
//                    .navigationTitle("Settings")
//                    .tabItem {
//                        Label("Settings", systemImage: "gear")
//                    }
            }
        } else {
            AuthenticationView()
        }
    }
}
