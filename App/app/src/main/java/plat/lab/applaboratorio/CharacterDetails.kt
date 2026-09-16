package plat.lab.applaboratorio

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterDetails(modifier: Modifier = Modifier,
                     id: Int,
                     onBackArrow: () -> Unit){
    Column(modifier = modifier
        .fillMaxSize()) {
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
        Text("Character Details id: $id")
    }
}

@Preview(showBackground = true)
@Composable
fun CharacterDetailsPreview(){
    CharacterDetails(id = 1, onBackArrow = {})
}