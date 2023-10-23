import SwiftUI

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
    
    @EnvironmentObject var vm: UserStateViewModel
    
    var body: some View {
        if (vm.isLoggedIn) {
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
                
                AttendanceView()
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
                SettingsView(user: vm.userOut!)
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
