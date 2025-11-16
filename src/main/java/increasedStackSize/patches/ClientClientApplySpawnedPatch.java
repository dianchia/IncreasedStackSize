package increasedStackSize.patches;

import increasedStackSize.packets.PacketReadServerSettings;
import necesse.engine.modLoader.annotations.ModMethodPatch;
import necesse.engine.network.client.Client;
import necesse.engine.network.client.ClientClient;
import net.bytebuddy.asm.Advice;

@ModMethodPatch(target = ClientClient.class, name = "applySpawned", arguments = {int.class})
public class ClientClientApplySpawnedPatch {
    @Advice.OnMethodExit
    static void onExit(@Advice.FieldValue(value = "client") Client client) {
        if (client.getPlayer() != null)
            client.network.sendPacket(new PacketReadServerSettings(true));
    }
}
