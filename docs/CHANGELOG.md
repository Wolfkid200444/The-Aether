# The Aether - Fabric - 1.21.1-1.5.1-beta.5

Changes

- Update Cumulus to 2.0.0. This includes a rework to the menu registration and the movement of world preview system code from Aether to Cumulus. The Aether/Minecraft Theme button is also now replaced by Cumulus' Menu List button.
- Reimplement config screen.

Fixes

- Fix weapon abilities not working.
- FIx Valkyrie Hoe not working.
- Fix Altar and Freezer output not working correctly.
- Fix the eternal day check for sleeping in the Aether.
- Fix eternal day values not persisting correctly.
- Fix Valkyrie Lance being enchantable with any enchantment.
- Fix meat drops from Aether animals not being cooked when killed with Fire Aspect in one hit.
- Fix Ambrosium Torches on walls dropping vanilla Torches.
- Fix Moa jump stats not syncing properly.
- Fix a desync with the Aether tool debuff config in multiplayer.
- Fix an edge case with the Slider's movement math breaking down at high health numbers.
- Fix Shield of Repulsion deflection not working properly.
- Fix a potential edge case with the Shield of Repulsion overriding other mods projectile hit cancellation.
- Fix the optional Shield of Repulsion tooltip being incorrect.
- Fix incompatibility crash with MixinSquared.
- Fix incompatibility crash with Tips mod.
- Fix compatibility support for the Tips mod.
- Fix missing datafixer logspam.

# The Aether - Fabric - 1.21.1-1.5.1-beta.4

Fixes

- Fix eternal day not functioning correctly.
- Fix Silver Dungeons sometimes not generating with aerclouds.
- Fix an incorrect tooltip for Gravitite Armor.
- Fix first-person Shield of Repulsion rendering for players without slim arms.
- Fix projectiles getting stuck on top of the Slider.
- Fix glove modifiers being hardcoded to a specific slot.
- Fix cape textures not being correctly separated per-player.
- Fix Moa Skins not registering on the client.
- Fix effect overlay vignettes not rendering.
- Fix bosses using regular-styled boss bars.
- Fix entities not getting placed in structures.
- Fix a crash from incorrect class casting.
- Fix incorrect enchantment selections for Valkyrie Lance and Dart Shooters.
- Fix Valkyrie Lance having sweeping.
- Fix a crash with Tips by temporarily disabling some compatibility.

# The Aether - Fabric - 1.21.1-1.5.1-beta.3

Fixes

- Fix a null crash from the helper for moving accessories from Curios to the new system.
- Fix a null crash from Moa Skin loading.
- Fix Zephyr Snowballs having incorrect shooting trajectory.
- Fix the Sentry's hitbox being larger than it should be.
- Fix Valkyrie Queens not unlocking the full Silver Dungeon when defeated.
- Fix first-person glove rendering for players without slim arms.
- Fix incorrect lengths for some discs in their `jukebox_song` files.
- Fix missing mod logo images.
- Fix crash that would occur when stripping blocks with a axe
- Fix shields not blocking damage
- Missing Translucency for Aerogel Blocks
- Fix crash when encoding various values like Status Effects
- Fix issues where Accessories Keybind would close any screen even within text box
- Adjust key press hook fixing issues with Gravitite ability
- Use Porting libs method for processing entity info which fixes entity not spawning (Example: Fabric port of Farmers Delight)
- Adjust fog rendering code when porting lib is installed to correct fog rendering mostly

# The Aether - Fabric - 1.21.1-1.5.1-beta.2

Fixes

- Fix inability to drop items leading them to be deleted
- Fix inability to choose aether specific Datapacks
- Fix issues where Ice Accessories did not freeze liquids
- Attempt to fix possible crash with syncing DataMaps
- Fix for not creating attached data when tracking lightning

# The Aether - Fabric - 1.21.1-1.5.1-beta.1

- Port 1.21.1-1.5.1-beta.1 from NeoForge to Fabric.
