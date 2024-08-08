package com.example.pokewiki.presentation.pokemon_list

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.palette.graphics.Palette
import com.example.pokewiki.data.models.PokemonListEntry
import com.example.pokewiki.domain.repository.PokemonRepository
import com.example.pokewiki.utils.Constants.PAGE_SIZE
import com.example.pokewiki.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class PokemonListViewModel @Inject constructor(
    private val repository: PokemonRepository
):ViewModel() {

    private var currentPage=0

    var pokemonList= mutableStateOf<List<PokemonListEntry>>(listOf())
    var loadError= mutableStateOf("")
    var isLoading= mutableStateOf(false)
    var endReached= mutableStateOf(false)

    init {
        loadPokemonPaginated()
    }

    fun loadPokemonPaginated(){
        viewModelScope.launch {
            isLoading.value=true
            val result=repository.getPokemonList(PAGE_SIZE,currentPage* PAGE_SIZE)
            when(result){
                is Resource.Error -> {
                    loadError.value=result.message!!
                    isLoading.value=false
                }
                is Resource.Loading -> {

                }
                is Resource.Success -> {
                    endReached.value=currentPage* PAGE_SIZE>=result.data!!.count
                    val pokemonEntries=result.data.results.mapIndexed{index, entry ->

                        val url = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${entry.id}.png"
                        com.example.pokewiki.data.models.PokemonListEntry(
                            entry.name.capitalize(
                                Locale.ROOT
                            ), url, entry.id.toInt()
                        )
                    }
                    currentPage++
                    loadError.value=""
                    isLoading.value=false
                    pokemonList.value+=pokemonEntries
                }
            }
        }
    }

    fun calculateDominantColor(drawable:Drawable,onFinish:(Color)->Unit){
        val bmp=(drawable as BitmapDrawable).bitmap.copy(Bitmap.Config.ARGB_8888,true)
        Palette.from(bmp).generate {palette->
            palette?.dominantSwatch?.rgb?.let { color->
                onFinish(Color(color))
            }
        }
    }
}