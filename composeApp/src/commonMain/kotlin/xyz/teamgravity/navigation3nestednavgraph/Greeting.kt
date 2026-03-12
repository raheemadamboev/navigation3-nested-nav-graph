package xyz.teamgravity.navigation3nestednavgraph

class Greeting {
    private val platform = getPlatform()

    fun greet(): String {
        return "Hello, ${platform.name}!"
    }
}