package com.example.hero_datasource.cache

import com.example.hero_datasource.network.EndPoints.BASE_URL
import com.example.hero_domain.Hero
import com.example.hero_domain.HeroRole
import com.example.hero_domain.getHeroAttackType
import com.example.hero_domain.getHeroAttrFromAbbreviation
import com.example.herodatasource.cache.Hero_Entity

fun Hero_Entity.toHero(): Hero {
    return Hero(
        id = id.toInt(),
        localizedName = localizedName,
        primaryAttribute = getHeroAttrFromAbbreviation(primaryAttribute),
        attackType = getHeroAttackType(attackType),
        roles = getRoles(),
        img = "$BASE_URL$img",
        icon = "$BASE_URL$icon",
        baseHealth = baseHealth.toFloat(),
        baseHealthRegen = baseHealthRegen?.toFloat()?: 0f,
        baseMana = baseMana.toFloat(),
        baseManaRegen = baseManaRegen?.toFloat()?: 0f,
        baseArmor = baseArmor.toFloat(),
        baseMoveRate = baseMoveRate.toFloat(),
        baseAttackMin = baseAttackMin.toInt(),
        baseAttackMax = baseAttackMax.toInt(),
        baseStr = baseStr.toInt(),
        baseAgi = baseAgi.toInt(),
        baseInt = baseInt.toInt(),
        strGain = strGain.toFloat(),
        agiGain = agiGain.toFloat(),
        intGain = intGain.toFloat(),
        attackRange = attackRange.toInt(),
        projectileSpeed = projectileSpeed.toInt(),
        attackRate = attackRate.toFloat(),
        moveSpeed = moveSpeed.toInt(),
        turnRate = turnRate?.toFloat()?: 0f,
        legs = legs.toInt(),
        turboPicks = turboPicks.toInt(),
        turboWins = turboWins.toInt(),
        proWins = proWins.toInt(),
        proPick = proPick.toInt(),
        firstPick = firstPick.toInt(),
        firstWin = firstWin.toInt(),
        secondPick = secondPick.toInt(),
        secondWin = secondWin.toInt(),
        thirdPick = thirdPick.toInt(),
        thirdWin = thirdWin.toInt(),
        fourthPick = fourthPick.toInt(),
        fourthWin = fourthWin.toInt(),
        fifthPick = fifthPick.toInt(),
        fifthWin = fifthWin.toInt(),
        sixthPick = sixthPick.toInt(),
        sixthWin = sixthWin.toInt(),
        seventhPick = seventhPick.toInt(),
        seventhWin = seventhWin.toInt(),
        eighthWin = eighthWin.toInt(),
        eighthPick = eighthPick.toInt(),
    )
}

private fun Hero_Entity.getRoles(): List<HeroRole> {
    val roles = mutableListOf<HeroRole>()
    if (roleCarry == 1L) roles.add(HeroRole.Carry)
    if (roleEscape == 1L) roles.add(HeroRole.Escape)
    if (roleNuker == 1L) roles.add(HeroRole.Nuker)
    if (roleInitiator == 1L) roles.add(HeroRole.Initiator)
    if (roleDurable == 1L) roles.add(HeroRole.Durable)
    if (roleDisabler == 1L) roles.add(HeroRole.Disabler)
    if (roleJungler == 1L) roles.add(HeroRole.Jungler)
    if (roleSupport == 1L) roles.add(HeroRole.Support)
    if (rolePusher == 1L) roles.add(HeroRole.Pusher)
    return roles
}
