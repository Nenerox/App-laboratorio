package plat.lab.applaboratorio

import androidx.annotation.DrawableRes
import plat.lab.applaboratorio.character.CharacterNestNav
import plat.lab.applaboratorio.locations.LocationNestNav
import plat.lab.applaboratorio.profile.ui.profile.ProfileDestination

data class BottomBarDestination(
    val route: Any,
    val label: String,
    @DrawableRes val icon: Int
)


val bottomBarDestinations = listOf(
    BottomBarDestination(
        route = CharacterNestNav,
        label = "Characters",
        icon = R.drawable.characters_logo
    ),
    BottomBarDestination(
        route = LocationNestNav,
        label = "Locations",
        icon = R.drawable.location_logo
    ),
    BottomBarDestination(
        route = ProfileDestination,
        label = "Profile",
        icon = R.drawable.profile_logo
    )
)