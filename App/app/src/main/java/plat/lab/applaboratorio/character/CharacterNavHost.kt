package plat.lab.applaboratorio.character

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.navigation
import kotlinx.serialization.Serializable
import plat.lab.applaboratorio.character.ui.details.characterDetailScreen
import plat.lab.applaboratorio.character.ui.details.navigateToCharacterDetail
import plat.lab.applaboratorio.character.ui.list.CharacterListDestination
import plat.lab.applaboratorio.character.ui.list.charactersScreen
import plat.lab.applaboratorio.character.ui.list.navigateToCharacters

@Serializable
data object CharacterNestNav

fun NavController.navigateToCharacterNest(navOptions: NavOptions? = null) {
    navigate(CharacterNestNav, navOptions)
}

fun NavGraphBuilder.characterNestNav(navController: NavController) {
    navigation<CharacterNestNav>(startDestination = CharacterListDestination){
        charactersScreen(onCharacterClick = { id -> navController.navigateToCharacterDetail(id) })
        characterDetailScreen(onBackArrow = { navController.navigateToCharacters() })
    }
}