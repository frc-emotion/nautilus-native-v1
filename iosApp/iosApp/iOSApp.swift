import SwiftUI
import shared
import KeychainSwift
import Network
import BackgroundTasks

@main
struct iOSApp: App {
    @StateObject var envModel = EnvironmentModel()
    @Environment(\.scenePhase) private var phase
    
    func ScheduleAppRefreshTasks() {
        let request = BGAppRefreshTaskRequest(identifier: "apprefresh")
        request.earliestBeginDate = .now.addingTimeInterval(900)
        try? BGTaskScheduler.shared.submit(request)
    }

    var body: some Scene {
        WindowGroup {
            NavigationView {
                ApplicationSwitcher()
            }
            .navigationViewStyle(.stack)
            .environmentObject(envModel)
            .onChange(of: phase) { newPhase in
                switch newPhase {
                case .background: ScheduleAppRefreshTasks()
                default: break
                }
            }
        }
        .backgroundTask(.appRefresh("apprefresh")) {
            await envModel.dh.bgSync()
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
                    
                    if (env.user!.permissions.generalScouting || env.user!.permissions.viewScoutingData) {
                        ScoutingView()
                            .tabItem {
                                Label("Scouting", systemImage: "chart.bar.doc.horizontal.fill")
                            }
                            .environmentObject(env)
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
