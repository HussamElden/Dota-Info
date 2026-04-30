package com.codingwithmitch.dotainfo.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import coil.ImageLoader
import com.codingwithmitch.dotainfo.R
import com.codingwithmitch.dotainfo.ui.theme.DotaInfoTheme
import com.example.core.DataState
import com.example.core.Logger
import com.example.core.UIComponent
import com.example.hero_interactors.HeroInteractors
import com.example.ui_herolist.ui.HeroList
import com.example.ui_herolist.ui.HeroListState
import com.squareup.sqldelight.android.AndroidSqliteDriver
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val state: MutableState<HeroListState> = mutableStateOf(HeroListState())
    private lateinit var imageLoader: ImageLoader
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        imageLoader= ImageLoader.Builder(this)
            .error(R.drawable.error_image)
            .placeholder(R.drawable.white_background)
            .availableMemoryPercentage(0.25)
            .crossfade(true)
            .build()
        val getHeros= HeroInteractors.build(
            sqlDriver = AndroidSqliteDriver(
                schema = HeroInteractors.schema,
                context = this,
                name = HeroInteractors.dbName
            )
        ).getHeros
        val logger= Logger("MainActivity")
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
        }.launchIn(CoroutineScope(IO))
        setContent {
            DotaInfoTheme {
                HeroList(
                    state =state.value,
                    imageLoader = imageLoader
                )
            }
        }
    }
}















