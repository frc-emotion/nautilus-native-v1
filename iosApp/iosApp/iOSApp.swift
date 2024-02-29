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
                if (env.user!.accountType != shared.AccountType.unverified) {
                    AttendanceView()
                        .tabItem {
                            Label("Attendance", systemImage: "calendar")
                        }
                        .environmentObject(env)
                    
                    if (env.user!.permissions.generalScouting) {
                        CrescendoScoutingFormView()
                            .navigationTitle("Scouting")
                            .tabItem {
                                Label("Scouting", systemImage: "chart.bar.doc.horizontal.fill")
                            }
                    }
                    
                    DirectoryView()
                        .tabItem {
                            Label("People", systemImage: "person.2.fill")
                        }
                        .environmentObject(env)
                }
                SettingsView()
                    .navigationTitle("Settings")
                    .tabItem {
                        Label("Settings", systemImage: "gear")
                    }
                    .environmentObject(env)
            }
        } else {
            AuthenticationView()
        }
    }
}
