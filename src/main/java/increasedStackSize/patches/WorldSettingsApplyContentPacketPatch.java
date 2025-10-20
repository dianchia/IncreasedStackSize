package increasedStackSize.patches;

import increasedStackSize.IncreasedStackSize;
import necesse.engine.modLoader.annotations.ModMethodPatch;
import necesse.engine.network.PacketReader;
import necesse.engine.world.WorldSettings;
import net.bytebuddy.asm.Advice;

@ModMethodPatch(target = WorldSettings.class, name = "applyContentPacket", arguments = {PacketReader.class})
public class WorldSettingsApplyContentPacketPatch {
    @Advice.OnMethodExit
    static void onExit(@Advice.Argument(0) PacketReader reader) {
        int newStackSize = reader.getNextInt();
//        GameLog.debug.println("New stack size from packet = " + newStackSize);
        IncreasedStackSize.setStackSizeMultiplier(newStackSize, true);
    }
}
