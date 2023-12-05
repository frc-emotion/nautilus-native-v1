import SwiftUI
import shared

struct SettingsView: View {
    let defaults = UserDefaults.standard
    @AppStorage("User") var storedUser: String!
    @State var user: shared.User
    @EnvironmentObject var vm: UserStateViewModel
    
    var body: some View {
        NavigationView {
            List {
                Section {
                    NavigationLink {
                        UserView(user: user)
                    } label: {
                        UserBar(user: user)
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
                            let response = try await shared.EmotionClient().getMe(user: user)
                            if let response {
                                defaults.set(response.toJSON(), forKey: "User")
                            }
                            
                        }
                    }) {
                        Text("Refresh User")
                            .fontWeight(.bold)
                            .foregroundColor(Color.primary)
                    }
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
            .onChange(of: storedUser) { newUser in
                user = shared.User.Companion().fromJSON(json: newUser) ?? shared.User.Companion().fromJSON(json: storedUser)!
            }
        }
    }
}

struct SettingsView_Previews: PreviewProvider {
    static var previews: some View {
        SettingsView(user: HelpfulVars().testuser)
    }
}
