package com.example.pokewiki.presentation.pokemon_list

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.pokewiki.R
import com.example.pokewiki.ui.theme.BRFirmaItalic
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun PokemonListScreen(
    navController: NavController,
    viewModel: PokemonListViewModel = hiltViewModel()
) {
    val configuration = LocalConfiguration.current
    Surface(
        color = MaterialTheme.colorScheme.background,
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Spacer(modifier = Modifier.height(20.dp))
                Image(
                    painter = painterResource(id = R.drawable.ic_pokemon_banner),
                    contentDescription = "Pokemon",
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
            }

            items(getItemCount(viewModel,configuration)) { index ->
                if (index >= getItemCount(viewModel,configuration) - 1 && (!viewModel.endReached.value)) {
                    viewModel.loadPokemonPaginated()
                }
                PokeRow(
                    rowInd = index,
                    entries = viewModel.pokemonList.value,
                    navController = navController
                )
            }

            item {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    if (viewModel.isLoading.value) {
                        CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                    }
                    if (viewModel.loadError.value.isNotEmpty()) {
                        RetrySection(error = viewModel.loadError.value) {
                            viewModel.loadPokemonPaginated()
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun PokeEntry(
    entry: com.example.pokewiki.data.models.PokemonListEntry,
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: PokemonListViewModel = hiltViewModel()
) {
    val defaultDominantColor = MaterialTheme.colorScheme.surface
    var dominantColor by remember { mutableStateOf(defaultDominantColor) }

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .shadow(5.dp, RoundedCornerShape(10.dp))
            .clip(RoundedCornerShape(10.dp))
            .aspectRatio(1f)
            .background(
                Brush.verticalGradient(
                    listOf(
                        dominantColor,
                        defaultDominantColor
                    )
                )
            )
            .clickable {
                navController.navigate(
                    "pokemon_detail_screen/${dominantColor.toArgb()}/${entry.pokemonName}"
                )
            }
    ) {
        Column {
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .align(Alignment.CenterHorizontally)
            ) {
                val painter = rememberAsyncImagePainter(
                    ImageRequest.Builder(LocalContext.current).data(data = entry.imageUrl)
                        .apply {
                            crossfade(true)
                            listener(
                                onSuccess = { _, result ->
                                    viewModel.calculateDominantColor(result.drawable) { color ->
                                        dominantColor = color
                                    }
                                }
                            )
                        }.build()
                )
                if (painter.state is AsyncImagePainter.State.Loading) {
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier
                            .scale(0.5f)
                            .align(Alignment.Center)
                    )
                }
                Image(
                    painter = painter,
                    contentDescription = entry.pokemonName,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }
            Text(
                text = entry.pokemonName,
                fontFamily = BRFirmaItalic,
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun PokeRow(
    rowInd: Int,
    entries: List<com.example.pokewiki.data.models.PokemonListEntry>,
    navController: NavController
) {
    val configuration = LocalConfiguration.current
    val itemsPerRow = if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) 4 else 2

    Column {
        Row {
            for (i in 0 until itemsPerRow) {
                if (entries.size > rowInd * itemsPerRow + i) {
                    PokeEntry(
                        entry = entries[rowInd * itemsPerRow + i],
                        navController = navController,
                        modifier = Modifier.weight(1f)
                    )
                    if (i < itemsPerRow - 1) {
                        Spacer(modifier = Modifier.width(16.dp))
                    }
                } else {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun PokemonsList(
    navController: NavController,
    viewModel: PokemonListViewModel = hiltViewModel()
) {
    val configuration = LocalConfiguration.current
    LazyColumn(
        contentPadding = PaddingValues(16.dp)
    ) {

        items(getItemCount(viewModel,configuration)) { index ->
            if (index >= getItemCount(viewModel,configuration) - 1 && !viewModel.endReached.value) {
                viewModel.loadPokemonPaginated()
            }
            PokeRow(
                rowInd = index,
                entries = viewModel.pokemonList.value,
                navController = navController
            )
        }
    }
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        if (viewModel.isLoading.value) {
            CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
        }
        if (viewModel.loadError.value.isNotEmpty()) {
            RetrySection(error = viewModel.loadError.value) {
                viewModel.loadPokemonPaginated()
            }
        }
    }
}

@Composable
fun RetrySection(
    error: String,
    onRetry: () -> Unit
) {
    Column {
        Text(text = error, color = Color.Red, fontSize = 18.sp)
        Spacer(modifier = Modifier.height(10.dp))
        Button(
            onClick = { onRetry() },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(text = "Retry")
        }
    }
}


private fun getItemCount(viewModel: PokemonListViewModel,configuration: Configuration): Int {
    val itemsPerRow = if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) 4 else 2
    return if (viewModel.pokemonList.value.size % itemsPerRow == 0) {
        viewModel.pokemonList.value.size / itemsPerRow
    } else {
        viewModel.pokemonList.value.size / itemsPerRow + 1
    }
}
