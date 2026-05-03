package com.example.ui_herolist.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.DataState
import com.example.core.Logger
import com.example.core.UIComponent
import com.example.hero_interactors.GetHeros
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class HeroListViewModel
@Inject
constructor(
    private val getHeros: GetHeros
): ViewModel() {
     val state: MutableState<HeroListState> = mutableStateOf(HeroListState())
    val logger= Logger("MainActivity")

    init {
        getHeros()
    }

    private fun getHeros(){
        getHeros.execute().onEach {datastate->
            when(datastate){
                is DataState.Response -> {
                    when(datastate.uiComponent){
                        is UIComponent.Dialog -> {
                            logger.log("Dialog: ${(datastate.uiComponent as UIComponent.Dialog).title} - ${(datastate.uiComponent as UIComponent.Dialog).description}")
                        }
                        is UIComponent.None -> {
                            logger.log("UIComponent.None")
                        }
                    }
                }
                is DataState.Data -> {
                    datastate.data?.let { data->
                        state.value=state.value.copy(heroList = data)
                        logger.log("Data: ${data.size} heros")
                    }
                }
                is DataState.Loading -> {
                    logger.log("Loading: ${datastate.progressBarState}")
                    state.value = state.value.copy(progressBarState = datastate.progressBarState)
                }
            }
        }.launchIn(viewModelScope)
    }
}