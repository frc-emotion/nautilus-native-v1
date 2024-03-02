import SwiftUI
import shared

struct SettingsView: View {
    @EnvironmentObject var env: EnvironmentModel
    @State private var showingErrorDialog = false
    @State private var errorMsg = ""
    @State private var attendancePeriodSelection: String = ""
    
    var body: some View {
        NavigationStack {
            List {
                Section {
                    NavigationLink {
                        // temporary workaround to prevent crashing, too time crunched to figure out the right way to do this
                        LoggedInUserView(user: env.user ?? Constants().emptyUser)
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
                .navigationTitle("Settings")
                .navigationBarTitleDisplayMode(.large)
                if (env.user != nil) {
                    Section {
                        Picker("Attendance Period", selection: $attendancePeriodSelection) {
                            ForEach(Array(env.user!.attendanceKeys), id: \.self) {
                                Text($0)
                            }
                        }
                        .pickerStyle(.navigationLink)
                        .disabled(Array(env.user!.attendance.keys).isEmpty)
                    }
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
            .refreshable {
                Task {
                    let user = try await env.dh.users.refreshLoggedIn(onError: { err in
                        errorMsg = err.message
                        showingErrorDialog = true
                    })
                    env.updateUser(newUser: user)
                }
            }
            .alert(isPresented: $showingErrorDialog) {
                Alert(title: Text("Error"), message: Text(errorMsg))
            }
            .onAppear() {
                attendancePeriodSelection = env.selectedAttendancePeriod
            }
            .onChange(of: attendancePeriodSelection) { newSelection in
                env.updateSelectedAttendancePeriod(newPeriod: newSelection)
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
