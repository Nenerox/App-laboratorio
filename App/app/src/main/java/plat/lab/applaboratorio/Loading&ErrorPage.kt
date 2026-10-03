package plat.lab.applaboratorio

import android.text.Layout
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Composable
fun LoadingScreen(modifier: Modifier = Modifier,
                  onLoading: () -> Unit = {}){
    Column(
        modifier = modifier
            .fillMaxSize()
            .clickable(onClick = onLoading),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        CircularProgressIndicator(modifier = modifier.size(75.dp))
        Text("Cargando",
            style = MaterialTheme.typography.labelLarge)
    }
}

@Composable
fun ErrorScreen(modifier: Modifier = Modifier,Error: String,onRetry: () -> Unit){
    Column(modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Image(painter = painterResource(id = R.drawable.error),
            contentDescription = "Error",
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.error),
            modifier = modifier.size(100.dp)
        )
        Text(Error,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.error,
            textAlign = TextAlign.Center,
            modifier = modifier.padding(10.dp))
        FilledTonalButton(onClick = onRetry) {
            Text("Reintentar",
                style = MaterialTheme.typography.labelLarge)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ErrorPreview(){
    ErrorScreen(Error = "Error al obtener listado de personajes intenta de nuevo", onRetry = {})
}

@Preview(showBackground = true)
@Composable
fun LoadingPreview(){
    LoadingScreen()
}
