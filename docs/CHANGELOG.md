# The Aether - Fabric - 1.21.1-1.5.1

Additions

- Added a new common server option `"Overworld-length Aether time cycle"` for changing the Aether's time from 3-times the length of the Overworld's day cycle to the same length as it.
- Added a new common server option `"Syncs time cycles"` for making the Aether's time sync with the Overworld's when it hits noon after eternal day has been banished.
- Added a new common client option `"Disables Aether's clouds"` for configuring whether clouds should render in the Aether dimension.
- Added a new tag `ores_in_ground/holystone` for Aether ores.
- Compatibility with Immersive Portals.
- Update de_at translation.
- Update en_ud translation.
- Update es_es translation.
- Update es_mx translation.
- Update fr_fr translation.
- Update it_it translation.
- Update ja_jp translation.
- Update ko_kr translation.
- Update lol_us translation.
- Update ms_my translation.
- Update pl_pl translation.
- Update ru_ru translation.
- Update sk_sk translation.
- Update sv_se translation.
- Update tok translation.
- Update uk_ua translation.
- Update zh_cn translation.

Fixes

- Fix Skyroot Swords and Pig Slayers not applying their custom mob drop modification abilities.
- Fix mount mid-air jumps being triggered when jumping from the ground.
- Fix dismount prevention when flying not working properly.
- Fix Sun Spirit and Fire Minion audio not playing.
- Fix the Aether Portal trigger sound not playing.
- Fix some discs playing in stereo mode.
- Fix Slider velocity being slower than it should be.
- Fix Aerwhales getting stuck on overhangs.
- Fix movement keys that cancel each other still disabling the Shield of Repulsion without moving.
- Fix TNT Presents having incorrect gravity.
- Fix TNT explosions not triggering other TNT blocks.
- Fix Dart Shooters not accepting Infinity in creative mode.
- Fix Aether materials not working with new armor trims.
- Fix the Skyroot Bed item model rendering as a Cyan Bed.
- Fix the Remedy effect overlay not displaying.
- Fix Skyroot Poison and Remedy Buckets not being in the `buckets` tag.
- Fix Skyroot Buckets not being in the `buckets/empty` tag.
- Fix a crash when trying to load a loot modifier asynchronously.
- Fix "UUID already exists" log-spam.

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
