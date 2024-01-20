import SwiftUI
import shared
import Network

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
        if (vm.user != nil && vm.isBusy == false) {
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
                    AttendanceView()
                        .tabItem {
                            Label("Attendance", systemImage: "calendar")
                        }
                        .environmentObject(vm)
                    
                    DirectoryView()
                        .tabItem {
                            Label("People", systemImage: "person.2.fill")
                        }
                        .environmentObject(vm)
                }
                //
                //
                //                AdminView()
                //                    .tabItem {
                //                        Label("Admin", systemImage: "person.badge.key.fill")
                //                    }
                //
                SettingsView()
                    .navigationTitle("Settings")
                    .tabItem {
                        Label("Settings", systemImage: "gear")
                    }
                    .environmentObject(vm)
            }
        } else {
            AuthenticationView()
        }
    }
}
