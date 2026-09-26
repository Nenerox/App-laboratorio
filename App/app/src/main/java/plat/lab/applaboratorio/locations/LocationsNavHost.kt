package plat.lab.applaboratorio.locations

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.navigation
import kotlinx.serialization.Serializable
import plat.lab.applaboratorio.locations.ui.details.locationDetailScreen
import plat.lab.applaboratorio.locations.ui.details.navigateToLocationDetail
import plat.lab.applaboratorio.locations.ui.list.LocationListDestination
import plat.lab.applaboratorio.locations.ui.list.locationsScreen

@Serializable
data object LocationNestNav

fun NavController.navigateToLocationsNest(navOptions: NavOptions? = null) {
    navigate(LocationNestNav, navOptions)
}

fun NavGraphBuilder.locationsNestNav(navController: NavController) {
    navigation<LocationNestNav>(startDestination = LocationListDestination){
        locationsScreen(onLocationClick = { id -> navController.navigateToLocationDetail(id) })
        locationDetailScreen(onBackArrow = { navController.navigateUp() })
    }
}