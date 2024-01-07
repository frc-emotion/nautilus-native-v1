import SwiftUI
import shared

@main
struct iOSApp: App {
    @StateObject var userStateViewModel = UserStateViewModel()
    
    init() {
        
    }
    
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
        if (vm.user != nil) {
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
                if (vm.user!.accountType != shared.AccountType.unverified) {
                    AttendanceView(user: vm.user!)
                        .tabItem {
                            Label("Attendance", systemImage: "calendar")
                        }
                    
                    DirectoryView(user: vm.user!)
                        .tabItem {
                            Label("People", systemImage: "person.2.fill")
                        }
                }
                //
                //
                //                AdminView()
                //                    .tabItem {
                //                        Label("Admin", systemImage: "person.badge.key.fill")
                //                    }
                //
                SettingsView(user: vm.user!)
                    .navigationTitle("Settings")
                    .tabItem {
                        Label("Settings", systemImage: "gear")
                    }
            }
        } else {
            AuthenticationView()
        }
    }
}
