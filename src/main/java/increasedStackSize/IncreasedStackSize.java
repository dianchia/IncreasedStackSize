package increasedStackSize;

import increasedStackSize.commands.ChangeStackSizeCommand;
import increasedStackSize.commands.GetStackSizeCommand;
import increasedStackSize.commands.StackSizeCommand;
import increasedStackSize.packets.PacketReadServerSettings;
import necesse.engine.GameLog;
import necesse.engine.commands.CommandsManager;
import necesse.engine.modLoader.ModSettings;
import necesse.engine.modLoader.annotations.ModEntry;
import necesse.engine.registries.PacketRegistry;

@ModEntry
public class IncreasedStackSize {

    public static final StackSizeSetting settings = new StackSizeSetting();

    public static ModSettings initSettings() {
        return settings;
    }

    public void preInit() {
    }

    public void init() {
        PacketRegistry.registerPacket(PacketReadServerSettings.class);
    }

    public void initResources() {
    }

    public void postInit() {
        GameLog.debug.println("[IncreasedStackSize] Current multipliers: " + settings.getMultipliers());
        CommandsManager.registerServerCommand(new ChangeStackSizeCommand());
        CommandsManager.registerServerCommand(new GetStackSizeCommand());
        CommandsManager.registerServerCommand(new StackSizeCommand());
    }
}
