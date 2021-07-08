package features.definition

import java.time.OffsetDateTime

class DefinitionKts {
    val a = "123"
    fun methodA() {
        print("method a")
    }
}

fun main() {
    val definition = DefinitionKts()
    definition.methodA()
    print(definition.a)
    print(OffsetDateTime.now())
}
