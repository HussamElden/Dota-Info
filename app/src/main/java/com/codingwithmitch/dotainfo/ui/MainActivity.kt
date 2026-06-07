package com.codingwithmitch.dotainfo.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.hilt.navigation.compose.hiltViewModel
import coil.ImageLoader
import com.codingwithmitch.dotainfo.R
import com.codingwithmitch.dotainfo.ui.theme.DotaInfoTheme
import com.example.core.DataState
import com.example.core.Logger
import com.example.core.UIComponent
import com.example.hero_interactors.HeroInteractors
import com.example.ui_herolist.ui.HeroList
import com.example.ui_herolist.ui.HeroListState
import com.example.ui_herolist.ui.HeroListViewModel
import com.squareup.sqldelight.android.AndroidSqliteDriver
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var imageLoader: ImageLoader
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            DotaInfoTheme {
                val viewModel: HeroListViewModel= hiltViewModel()
                HeroList(
                    state =viewModel.state.value,
                    imageLoader = imageLoader
                )
            }
        }
    }
}















