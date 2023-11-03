import SwiftUI
import shared

@main
struct iOSApp: App {
    @StateObject var userStateViewModel = UserStateViewModel()
    
    var body: some Scene {
        WindowGroup {
            NavigationView {
                ApplicationSwitcher()
            }
            .navigationViewStyle(.stack)
            .environmentObject(userStateViewModel)
        }
    }
}

struct ApplicationSwitcher: View {
    let defaults = UserDefaults.standard
    @EnvironmentObject var vm: UserStateViewModel
    
    var body: some View {
        if (vm.isLoggedIn) {
            let DaUser = shared.User.Companion().fromJSON(json: defaults.string(forKey: "User"))!
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
                
                AttendanceView(user: DaUser)
                    .tabItem {
                        Label("Attendance", systemImage: "calendar")
                    }
                
                //                DirectoryView()
                //                    .tabItem {
                //                        Label("People", systemImage: "person.2.fill")
                //                    }
                //
                //                AdminView()
                //                    .tabItem {
                //                        Label("Admin", systemImage: "person.badge.key.fill")
                //                    }
                //
                SettingsView(user: DaUser)
                    .navigationTitle("Settings")
                    .tabItem {
                        Label("Settings", systemImage: "gear")
                    }
            }
        } else {
            LoginView()
        }
    }
}
