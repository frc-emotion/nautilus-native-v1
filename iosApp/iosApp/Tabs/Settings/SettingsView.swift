import SwiftUI
import shared

struct SettingsView: View {
    @EnvironmentObject var env: EnvironmentModel
    
    var body: some View {
        NavigationStack {
            List {
                Section {
                    NavigationLink {
                        UserView(user: env.user!)
                    } label: {
                        if (env.user != nil) {
                            UserBar()
                                .environmentObject(env)
                        }
                    }
                       
//                    Not yet implemented
//                    NavigationLink {
//                        AccountView()
//                    } label: {
//                        Text("Account Settings")
//                    }
                }
//                Section {
//                    Not yet implemented
//                    NavigationLink {
//                        BugReportView()
//                    } label: {
//                        Text("Report a Bug")
//                    }
//                }
                Section {
                    Button (action: {
                        Task {
                            env.updateUser(newUser: nil)
                            env.dh.users.logout()
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
        SettingsView().environmentObject({ () -> EnvironmentModel in
            let env = EnvironmentModel()
            env.user = HelpfulVars().testuser
            return env
        }() )
    }
}
