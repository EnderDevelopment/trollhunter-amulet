# Minecraft Plugin: Amuleto del Cazatroles

Amuleto del Cazatroles is a Minecraft plugin for Paper servers that adds a craftable magical amulet with a right-click ability, visual particles, sound effects, temporary potion effects, cooldown handling, and an administrative command for receiving the item.

## Install

1. Build the plugin with Java 21 and Maven:
   ```bash
   mvn package
   ```
2. Copy the generated `target/plugin.jar` file to the server's `plugins` folder.
3. Start or restart a Paper Minecraft server running version `1.21.11`.
4. Confirm that the plugin loads successfully in the server console.

Requirements:

- Paper `1.21.11` or a compatible Paper-based server
- Java 21
- Maven 3.8 or newer for building from source

This project is a Java Edition Paper plugin. It does not implement a Bedrock Edition addon and is not compatible with Minecraft Bedrock `1.26.50`.

## Use

Craft the amulet in a crafting table using this shaped recipe:

```text
Gold Ingot   Sunflower    Gold Ingot
Diamond      Nether Star  Diamond
Gold Ingot   Diamond      Gold Ingot
```

The resulting item is the Amulet of Daylight.

1. Hold the amulet in your main hand.
2. Right-click the air or a block.
3. The amulet grants:
   - Glowing for 15 seconds
   - Night Vision for 15 seconds
   - End rod and totem particle effects
   - A beacon activation sound
4. Wait 15 seconds before using the ability again.

During the cooldown, the player receives an action-bar message showing the remaining time.

Operators with the `amulet.command` permission can use:

```text
/amuleto give
```

This gives the executing player an Amulet of Daylight. Running `/amuleto` without the `give` argument displays the available command and crafting information.

---

More Minecraft plugin projects by EnderDevelopment: https://github.com/EnderDevelopment

Project page: https://dash.enderdevelopment.com/community/6d49d0e8-eded-45d8-b21d-96c023456713?utm_source=github&utm_medium=readme&utm_campaign=auto_publish&utm_content=6d49d0e8-eded-45d8-b21d-96c023456713
