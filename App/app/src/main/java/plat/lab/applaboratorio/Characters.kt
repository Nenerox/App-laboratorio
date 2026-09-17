package plat.lab.applaboratorio

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun CharacterPlate(modifier: Modifier = Modifier,
                   name: String,
                   status: String,
                   species: String,
                   link: String)
{
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(15.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = link,
            contentDescription = "Character Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(70.dp)
                .clip(CircleShape)
        )
        Column(modifier = modifier.padding(start = 5.dp)) {
            Text("$name",
                style = MaterialTheme.typography.titleLarge)
            Text("$species - $status",
                style = MaterialTheme.typography.bodyLarge)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterList(modifier: Modifier = Modifier,
                  onCharacterClick: (Int) -> Unit){
    val CharacterList: List<Character>
    CharacterList = CharacterDb().getAllCharacters()

    Column(modifier = modifier
        .fillMaxSize()){
        TopAppBar(title = { Text("Characters") },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                titleContentColor = MaterialTheme.colorScheme.primary,
        ))

        LazyColumn(modifier = modifier) {
            items(CharacterList.size) { it ->
                CharacterPlate(name = CharacterList[it].name,
                    status = CharacterList[it].status,
                    species = CharacterList[it].species,
                    link = CharacterList[it].image,
                    modifier = Modifier.clickable(enabled = true,
                        onClick = {onCharacterClick(CharacterList[it].id)})
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CharacterPreview() {
    CharacterList(onCharacterClick = {})
}