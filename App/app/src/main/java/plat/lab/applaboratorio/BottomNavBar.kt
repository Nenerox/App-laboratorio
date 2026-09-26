package plat.lab.applaboratorio

import androidx.annotation.DrawableRes
import plat.lab.applaboratorio.character.CharacterNestNav

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
        route = "", //TODO agregar el route de locations
        label = "Locations",
        icon = R.drawable.location_logo
    ),
    BottomBarDestination(
        route = "", //TODO agregar el route de locations
        label = "Profile",
        icon = R.drawable.profile_logo
    )
)