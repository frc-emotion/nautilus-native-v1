import SwiftUI
import shared

struct SettingsView: View {
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
                Text("THE SOFTWARE IS PROVIDED \"AS IS\", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.")
                    .font(.footnote)
            }
            .navigationTitle("Settings")
        }
    }
}

struct SettingsView_Previews: PreviewProvider {
    static var previews: some View {
        SettingsView(user: HelpfulVars().testuser)
    }
}
