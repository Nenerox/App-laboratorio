package plat.lab.applaboratorio

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterDetails(modifier: Modifier = Modifier,
                     id: Int,
                     onBackArrow: () -> Unit){
    Column(modifier = modifier
        .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally) {
        val character: Character
        character = CharacterDb().getCharacterById(id)

        TopAppBar(
            title = { Text("Character Detail") },
            navigationIcon = {
                IconButton(onClick = onBackArrow) {
                    Icon(
                        painter = painterResource(id = R.drawable.backarrow),
                        contentDescription = null
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                titleContentColor = MaterialTheme.colorScheme.primary,
            )
        )

        AsyncImage(
            model = character.image,
            contentDescription = "Character Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .padding(10.dp)
                .size(220.dp)
                .clip(CircleShape)
        )

        Text(character.name,
            style = MaterialTheme.typography.titleLarge)

        Row(modifier = modifier.fillMaxWidth()
            .padding(top = 20.dp, start = 50.dp, end = 50.dp),
            horizontalArrangement = Arrangement.SpaceBetween){
            Text("Species: ",
                style = MaterialTheme.typography.bodyLarge)
            Text("${character.species}",
                style = MaterialTheme.typography.bodyLarge)
        }
        Row(modifier = modifier.fillMaxWidth()
            .padding(top = 10.dp, start = 50.dp, end = 50.dp),
            horizontalArrangement = Arrangement.SpaceBetween){
            Text("Status: ",
                style = MaterialTheme.typography.bodyLarge)
            Text("${character.status}",
                style = MaterialTheme.typography.bodyLarge)
        }
        Row(modifier = modifier.fillMaxWidth()
            .padding(top = 10.dp, start = 50.dp, end = 50.dp),
            horizontalArrangement = Arrangement.SpaceBetween){
            Text("Gender: ",
                style = MaterialTheme.typography.bodyLarge)
            Text("${character.gender}",
                style = MaterialTheme.typography.bodyLarge)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CharacterDetailsPreview(){
    CharacterDetails(id = 2, onBackArrow = {})
}