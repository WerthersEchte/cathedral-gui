# A gui for a cathedral implementation in Java
[![License: Unlicense](https://img.shields.io/badge/License-Unlicense-blue.svg)](http://unlicense.org/)
![Build](https://github.com/WerthersEchte/cathedral-gui/actions/workflows/build.yml/badge.svg)
[![Test coverage](.github/badges/jacoco.svg)](https://github.com/WerthersEchte/cathedral-gui/actions/workflows/build.yml)
[![Code Quality](https://github.com/WerthersEchte/cathedral-gui/actions/workflows/codequality.yml/badge.svg)](https://github.com/WerthersEchte/cathedral-gui/actions/workflows/codequality.yml)
[![javadoc](https://javadoc.io/badge2/io.github.werthersechte/cathedral-gui/javadoc.svg)](https://javadoc.io/doc/io.github.werthersechte/cathedral-gui)

## About
This is a gui for the Java/Kotlin implementation(https://github.com/WerthersEchte/cathedral) of the board game cathedral (https://en.wikipedia.org/wiki/Cathedral_(board_game)). It intended use is for developing basic ki and ki adjacent programs.  
Uses https://github.com/WerthersEchte/cathedral-ai for ai implementations.

## How to get
Gradle:
- Kotlin
```
implementation("io.github.werthersechte:cathedral-gui:2.0.0")
```
- Groovy
```
implementation 'io.github.werthersechte:cathedral-gui:2.0.0'
```

## How to Use

The UI can be started with the command `CathedralGUI.start()`. To load an ai with the gui, use `CathedralGUI.start(YourAgent())`.

Example(Kotlin):
```
import de.fhkiel.ki.cathedral.ai.Agent
import de.fhkiel.ki.cathedral.game.Game
import de.fhkiel.ki.cathedral.game.Placement
import de.fhkiel.ki.cathedral.gui.CathedralGUI
import java.util.Optional

fun main() {
    CathedralGUI.start(PassingAgent())
}

// This agent always passes its turn
class PassingAgent: Agent{
    override fun name(): String {
        return "IPass"
    }

    override fun calculateTurn(
        game: Game,
        timeForTurn: Int,
        timeBonus: Int
    ): Optional<Placement> {
        return Optional.empty<Placement>()
    }

}
```

## Dependencies
- [Cathedral Ai](https://github.com/WerthersEchte/cathedral-ai)
