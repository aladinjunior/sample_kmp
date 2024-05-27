import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import composedemo.composeapp.generated.resources.Res
import composedemo.composeapp.generated.resources.compose_multiplatform
import composedemo.composeapp.generated.resources.fr
import composedemo.composeapp.generated.resources.id
import composedemo.composeapp.generated.resources.jp
import composedemo.composeapp.generated.resources.mx
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalResourceApi::class)
data class Country(val name: String, val zone: TimeZone, val image: DrawableResource)


fun currentTimeAt(location: String, zone: TimeZone): String {
    fun LocalTime.formatted() = "$hour:$minute:$second"


        val time = Clock.System.now()
        val localTime = time.toLocalDateTime(zone).time
        return "The time in $location is ${localTime.formatted()}"


}

@OptIn(ExperimentalResourceApi::class)
fun countries() = listOf(
    Country("Japan", TimeZone.of("Asia/Tokyo"), Res.drawable.jp),
    Country("France", TimeZone.of("Europe/Paris"), Res.drawable.fr),
    Country("Mexico", TimeZone.of("America/Mexico_City"), Res.drawable.mx),
    Country("Indonesia", TimeZone.of("Asia/Jakarta"), Res.drawable.id),
    Country("Egypt", TimeZone.of("Africa/Cairo"), Res.drawable.mx)
)


@OptIn(ExperimentalResourceApi::class)
@Composable
fun App(countries: List<Country> = countries()) {
    MaterialTheme {
        var timeAtLocation by remember { mutableStateOf("No Location Selected") }
        var showCountries by remember { mutableStateOf(false) }
        Column(modifier = Modifier.padding(20.dp)){
            Text(
                text = timeAtLocation,
                style = TextStyle(fontSize = 24.sp),
                modifier = Modifier.fillMaxWidth().align(Alignment.CenterHorizontally),
                textAlign = TextAlign.Center,
            )
            Row(modifier = Modifier.padding(top = 10.dp, start = 20.dp)) {
                DropdownMenu(
                    expanded = showCountries,
                    onDismissRequest =  { showCountries = false }
                ) {
                    countries.forEach { (name, zone, image) ->
                        DropdownMenuItem(
                            onClick = {
                                timeAtLocation = currentTimeAt(name, zone)
                                showCountries = false
                            }
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Image(
                                    painterResource(image),
                                    modifier = Modifier.size(50.dp).padding(end = 10.dp),
                                    contentDescription = "$name flag"
                                    )
                            }
                            Text(name)
                        }
                    }
                }

            }
            Button(modifier = Modifier.padding(top = 10.dp, start = 20.dp),
                onClick = { showCountries = !showCountries}) {
                Text("Select Location")
            }
        }
    }


}






