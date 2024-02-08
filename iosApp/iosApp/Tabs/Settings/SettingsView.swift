import SwiftUI
import shared

struct SettingsView: View {
    @EnvironmentObject var vm: UserStateViewModel
    
    var body: some View {
        NavigationStack {
            List {
                Section {
                    NavigationLink {
                        UserView(user: vm.user!)
                    } label: {
                        UserBar(user: vm.user!)
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
        SettingsView().environmentObject({ () -> UserStateViewModel in
            let vm = UserStateViewModel()
            vm.user = HelpfulVars().testuser
            return vm
        }() )
    }
}
