package com.example.hero_datasource.network

import com.example.hero_domain.Hero
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.url

class HeroServiceImpl(
    private val httpClient: HttpClient,
): HeroService {

    override suspend fun getHeroes(): List<Hero> {
        return httpClient.get<List<HeroDto>> {
            url(EndPoints.HERO_STATS)
        }.map { it.toHero() }
    }
}