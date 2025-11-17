# Mod Information

Mod version: 2.0.0

Game version: 1.0.2

# Increased Stack Size

Feeling that your inventory is always full of stone when mining?
Wish to increase the stack size of items?
This is the perfect mod for you!

## Increased Stack Size 2.0 Released :exclamation: :exclamation:
In November 2025, a whole new version of Increased Stack Size is released!
Let us welcome the Increased Stack Size 2.0! :tada: :tada:

Well, what's new in this version you might ask? 
Roll the drum please... :drum:

**PER-CLASS STACK SIZE MULTIPLIER!!!**

Now, you can set separate multiplier for each class!
Want your stone to have a multiplier of 10 but not your pickaxe? I got you cover!
Just use the command `/stacksize set StonePlaceableItem 1` and `/stacksize set PickaxeToolItem 1`.
The settings are world base, so you have to set it for each world... right?
**Nope**, a global settings can be configured by including a `settings/IncreasedStackSize.cfg` in your game directory.
You can refer to [this section](#version-2x) for a detailed explanation on how to set up this configuration file.

But WAIT, how do I know the class name? I only know the in-game display name :confused:
Fear not, the mod author (me) has provided a way to search for the class name via the display name.
Just visit [here](https://dianchia.github.io/IncreasedStackSize) and you are good to go~

## Setting the multiplier

### Version 2.x
Two commands are provided, the `/stacksize set <classname> <multiplier>` and `/stacksize get [classname]`.
`/stacksize set` command requires the `classname` and `multiplier` arguments,
whereas `/stacksize get` will return all multipliers set if no arguments are provided.
Optionally, a `IncreasedStackSize.cfg` file can be used for setting a global multiplier across all saved world.
If you're on steam, just right-clicking on your game, then `Manage > Browse local files`, create a `settings` directory
if not exists, and create a `IncreasedStackSize.cfg` in the directory.
The format for the file is `classname=multiplier`. For example, `PickaxeToolItem=1`.
If you want to set a multiplier for all items in the game, use `Item=<multiplier>`,
or `/stacksize set Item <multiplier>` command in the game.

### Version 1.x
Two commands are provided, `/stacksize set <multiplier>` and `/stacksize get`.

### Version 0.x
To set the multiplier, run the command "/setstacksizemultiplier" in the game.

**Note that you can only run this command if you have permission higher or equals to "OWNER"**\
Please only give this permission to someone you trust.

## Multiplayer

This mod needs to be downloaded for both host and client if playing in multiplayer.

## Important Notes

- If you decrease the multiplier, **DO NOT** sort your inventory or any chest as you will lose the item that has stack
  size more than current maximum stack size.
  Instead, drop the item on the ground and pick it up again.

_Disclaimer: The mod author is not responsible to any item lost due to incorrect usage of this mod_

## Known Bugs

- A stack of items can be enchanted with a single scroll.

## GitHub

Bugs can be reported to [here](https://github.com/dianchia/IncreasedStackSize/issues)

## Collection

[Here](https://steamcommunity.com/sharedfiles/filedetails/?id=2830016047) are a few mod I wrote

## Support me
The mods are created without any profit motive, but if you like the mod, consider [buy me a coffee?](https://buymeacoffee.com/dianrui520y)