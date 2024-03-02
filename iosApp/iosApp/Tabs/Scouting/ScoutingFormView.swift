//
//  ScoutingFormView.swift
//  iosApp
//
//  Created by Jason Ballinger on 7/11/23.
//  Copyright © 2023 team2658. All rights reserved.
//

import SwiftUI
import shared

struct ScoutingFormView: View {
    @EnvironmentObject var env: EnvironmentModel
    var body: some View {
        CrescendoScoutingFormView()
            .environmentObject(env)
    }
}

struct ScoutingFormView_Previews: PreviewProvider {
    static var previews: some View {
        ScoutingFormView().environmentObject({ () -> EnvironmentModel in
            let env = EnvironmentModel()
            env.user = HelpfulVars().testuser
            return env
        }() )
    }
}
