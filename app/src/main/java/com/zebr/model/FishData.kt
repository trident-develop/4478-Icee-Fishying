package com.zebr.model

import com.zebr.R
import kotlin.random.Random

enum class FishType(val drawableRes: Int) {
    FISH_3(R.drawable.fish_3),
    FISH_6(R.drawable.fish_6),
    FISH_7(R.drawable.fish_7),
    JELLYFISH(R.drawable.jellyfish),
    OCTOPUS(R.drawable.octopus),
    CRAB(R.drawable.crab)
}

val goodFishTypes = listOf(FishType.FISH_3, FishType.FISH_6, FishType.FISH_7)
val badFishTypes = listOf(FishType.JELLYFISH, FishType.OCTOPUS, FishType.CRAB)

data class SwimmingFish(
    val id: Int,
    val fishType: FishType,
    val isGood: Boolean,
    var xFraction: Float,
    val yFraction: Float,
    val speed: Float,
    val goingRight: Boolean
)

data class LevelConfig(
    val level: Int,
    val targetScore: Int,
    val baseSpeed: Float,
    val spawnIntervalMs: Long,
    val maxFish: Int,
    val goodFish: FishType,
    val badFish: FishType
) {
    companion object {
        fun forLevel(level: Int): LevelConfig {
            val random = Random(level * 42)
            return LevelConfig(
                level = level,
                targetScore = 10 + (level - 1) * 3,
                baseSpeed = 0.25f + (level - 1) * 0.015f,
                spawnIntervalMs = (1200L - (level - 1) * 35L).coerceAtLeast(350L),
                maxFish = (4 + level / 4).coerceAtMost(10),
                goodFish = goodFishTypes[random.nextInt(goodFishTypes.size)],
                badFish = badFishTypes[random.nextInt(badFishTypes.size)]
            )
        }
    }
}
