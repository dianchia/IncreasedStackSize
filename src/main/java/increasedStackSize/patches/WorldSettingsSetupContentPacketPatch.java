package increasedStackSize.patches;

import increasedStackSize.IncreasedStackSize;
import necesse.engine.GameLog;
import necesse.engine.modLoader.annotations.ModMethodPatch;
import necesse.engine.network.PacketWriter;
import necesse.engine.world.WorldSettings;
import net.bytebuddy.asm.Advice;

@ModMethodPatch(target = WorldSettings.class, name = "setupContentPacket", arguments = {PacketWriter.class})
public class WorldSettingsSetupContentPacketPatch {
    @Advice.OnMethodExit
    static void onExit(@Advice.Argument(value = 0, readOnly = false) PacketWriter writer) {
        GameLog.debug.println("Adding stack size to packet. Stack size: " + IncreasedStackSize.stackSizeMultiplier);
        writer.putNextInt(IncreasedStackSize.stackSizeMultiplier);
    }
}
