package com.example.hero_interactors

import com.example.core.DataState
import com.example.core.ProgressBarState
import com.example.core.UIComponent
import com.example.hero_datasource.cache.HeroCache
import com.example.hero_datasource.network.HeroService
import com.example.hero_domain.Hero
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetHeros(
    private val service: HeroService,
    private val cache: HeroCache
) {
    fun execute(): Flow<DataState<List<Hero>>> = flow<DataState<List<Hero>>> {
        try {
            emit(DataState.Loading<List<Hero>>(progressBarState = ProgressBarState.Loading))
            val heros = try {
                service.getHeroes()
            }catch (e: Exception) {
                e.printStackTrace()
                emit(DataState.Response<List<Hero>>(
                    uiComponent = UIComponent.Dialog(
                        title = "Error",
                        description = e.message ?: "Unknown error"
                    )
                ))
                emptyList()
            }
            // cache the network data
            cache.insert(heros)

            // emit data from cache
            val cachedHeros = cache.selectAll()

            emit(DataState.Data(cachedHeros))
        }catch (e: Exception) {
           e.printStackTrace()
            emit(DataState.Response<List<Hero>>(
                uiComponent = UIComponent.Dialog(
                    title = "Error",
                    description = e.message ?: "Unknown error"
                )
            ))
        }
        finally {
            emit(DataState.Loading<List<Hero>>(progressBarState = ProgressBarState.Idle))
        }
    }
}