package org.nautilusapp.nautilus.android.screens.scouting.standscoutingforms.crescendo

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import org.nautilusapp.nautilus.DataHandler
import org.nautilusapp.nautilus.DataResult
import org.nautilusapp.nautilus.Error
import org.nautilusapp.nautilus.Result
import org.nautilusapp.nautilus.android.PreviewTheme
import org.nautilusapp.nautilus.android.screens.scouting.standscoutingforms.BaseScoutingForm
import org.nautilusapp.nautilus.android.screens.scouting.standscoutingforms.RPInfo
import org.nautilusapp.nautilus.android.screens.scouting.standscoutingforms.crescendo.auto.CrescendoAutoInput
import org.nautilusapp.nautilus.android.screens.scouting.standscoutingforms.crescendo.auto.CrescendoAutoState
import org.nautilusapp.nautilus.android.screens.scouting.standscoutingforms.crescendo.endgame.CrescendoEndgame
import org.nautilusapp.nautilus.android.screens.scouting.standscoutingforms.crescendo.endgame.CrescendoEndgameInput
import org.nautilusapp.nautilus.android.screens.scouting.standscoutingforms.crescendo.teleop.CrescendoTeleopInput
import org.nautilusapp.nautilus.android.screens.scouting.standscoutingforms.crescendo.teleop.CrescendoTeleopState
import org.nautilusapp.nautilus.android.ui.composables.containers.Screen
import org.nautilusapp.nautilus.android.ui.theme.ColorTheme
import org.nautilusapp.nautilus.scouting.scoutingdata.CrescendoSubmission
import org.nautilusapp.nautilus.scouting.tooltips.CrescendoTooltips

@Composable
fun CrescendoForm(
    dh: DataHandler,
    snack: SnackbarHostState? = null
) {
    val vm = viewModel<CrescendoVM>()
    LaunchedEffect(true) {
        vm.comps = dh.seasons.getComps(2024) {
            vm.comps = it
        }
    }
    Screen(onRefresh = {
        dh.seasons.sync().also {
            if (it is Result.Success) {
                it.data.find { s -> s.year == 2024 }?.let { c ->
                    vm.comps = c.competitions
                }
            }
        }
    }, snack = snack) {
        CrescendoFormUI(
            auto = vm.auto,
            teleop = vm.teleop,
            endgame = vm.endgame,
            comps = vm.comps,
            onSubmit = dh.crescendo::upload
        )
    }
}

@Composable
fun CrescendoFormUI(
    auto: CrescendoAutoState,
    teleop: CrescendoTeleopState,
    endgame: CrescendoEndgame,
    comps: List<String>,
    onSubmit: suspend (CrescendoSubmission) -> DataResult<*>
) {
    BaseScoutingForm(
        competitions = comps,
//        rpInfo = Pair("Melody", "Ensemble"),
        rpInfo = Pair(
            RPInfo("Melody", CrescendoTooltips.melodyRP),
            RPInfo("Ensemble", CrescendoTooltips.ensembleRP)
        ),
        onFormSubmit = {
            val error = Result.Error(Error("Invalid Form Input", 400))
            val a = auto.data ?: return@BaseScoutingForm error
            val t = teleop.data ?: return@BaseScoutingForm error
            val e = endgame.data ?: return@BaseScoutingForm error
            onSubmit(CrescendoSubmission.from(it, a, t, e))
        },
        contentInputsOkay = auto.isValid && teleop.isValid && endgame.isValid,
        clearContentInputs = {
            auto.clear()
            teleop.clear()
            endgame.clear()
        }) {
        CrescendoAutoInput(state = auto)
        CrescendoTeleopInput(state = teleop)
        Spacer(modifier = Modifier.size(8.dp))
        CrescendoEndgameInput(state = endgame)
    }
}

@Preview
@Composable
fun CrescendoFormPreview() {
    val auto = remember {
        CrescendoAutoState()
    }
    val tel = remember {
        CrescendoTeleopState()
    }
    val end = remember {
        CrescendoEndgame()
    }
    PreviewTheme(preference = ColorTheme.NAUTILUS_DARK) {
        Screen {
            CrescendoFormUI(
                auto = auto,
                teleop = tel,
                endgame = end,
                comps = listOf("balls", "fart"),
                onSubmit = { Result.Success(Unit) }
            )
        }
    }
}