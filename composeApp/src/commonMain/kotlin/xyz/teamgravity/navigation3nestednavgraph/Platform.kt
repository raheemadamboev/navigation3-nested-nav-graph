package xyz.teamgravity.navigation3nestednavgraph

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform