import SwiftUI
import shared

struct SettingsView: View {
    @EnvironmentObject var env: EnvironmentModel
    @State private var showingErrorDialog = false
    @State private var errorMsg = ""
    
    var body: some View {
        NavigationStack {
            List {
                Section {
                    NavigationLink {
                        // temporary workaround to prevent crashing, too time crunched to figure out the right way to do this
                        UserView(user: env.user ?? Constants().emptyUser)
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
            .refreshable {
                Task {
                    try await env.dh.users.refreshLoggedIn(onError: { err in
                        errorMsg = err.message
                        showingErrorDialog = true
                    })
                }
            }
            .alert(isPresented: $showingErrorDialog) {
                Alert(title: Text("Error"), message: Text(errorMsg))
            }
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
