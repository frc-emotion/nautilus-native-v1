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
                HomeView()
                    .tabItem {
                        Label("Home", systemImage: "house.fill")
                    }
                
                ScoutingView()
                    .tabItem {
                        Label("Scouting", systemImage: "chart.bar.doc.horizontal.fill")
                    }
                
                DirectoryView()
                    .tabItem {
                        Label("People", systemImage: "person.2.fill")
                    }
                
                AdminView()
                    .tabItem {
                        Label("Admin", systemImage: "person.badge.key.fill")
                    }
                
                SettingsView()
                    .tabItem {
                        Label("Settings", systemImage: "gear")
                    }
            }
        } else {
            LoginView()
        }
    }
}
