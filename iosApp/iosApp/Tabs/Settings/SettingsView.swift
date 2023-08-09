import SwiftUI
import shared

struct SettingsView: View {
    
    @EnvironmentObject var vm: UserStateViewModel
    
    var body: some View {
        NavigationView {
            List {
                Section {
                    NavigationLink {
                        UserView(user: users[1])
                    } label: {
                        UserBar()
                    }
//                    Not yet implemented
//                    NavigationLink {
//                        AccountView()
//                    } label: {
//                        Text("Account Settings")
//                    }
                }
                Section {
//                    Not yet implemented
//                    NavigationLink {
//                        BugReportView()
//                    } label: {
//                        Text("Report a Bug")
//                    }
                    Button (action: {
                        Task {
                            await vm.signOut()
                        }
                    }) {
                        Text("Log Out")
                            .fontWeight(.bold)
                            .foregroundColor(Color.red)
                    }
                }
            }
            .navigationTitle("Settings")
        }
    }
}

struct SettingsView_Previews: PreviewProvider {
    static var previews: some View {
        SettingsView()
    }
}
