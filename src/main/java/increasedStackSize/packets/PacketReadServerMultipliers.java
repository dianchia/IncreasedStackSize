package increasedStackSize.packets;

import increasedStackSize.IncreasedStackSize;
import increasedStackSize.SettingType;
import increasedStackSize.StackSizeSetting;
import necesse.engine.network.NetworkPacket;
import necesse.engine.network.Packet;
import necesse.engine.network.PacketReader;
import necesse.engine.network.PacketWriter;
import necesse.engine.network.client.Client;
import necesse.engine.network.server.Server;
import necesse.engine.network.server.ServerClient;

import java.util.HashMap;
import java.util.Map;

public class PacketReadServerMultipliers extends Packet {
    Map<String, Integer> newWorldMultipliers = new HashMap<>();
    Map<String, Integer> newGlobalMultipliers = new HashMap<>();

    // Required for the registry
    public PacketReadServerMultipliers(byte[] data) {
        super(data);
        PacketReader reader = new PacketReader(this);
        if (!reader.getNextBoolean()) {
            newWorldMultipliers = StackSizeSetting.mapFromString(reader.getNextString());
            newGlobalMultipliers = StackSizeSetting.mapFromString(reader.getNextString());
        }
    }

    public PacketReadServerMultipliers(boolean fromClient) {
        PacketWriter writer = new PacketWriter(this);
        writer.putNextBoolean(fromClient);
        if (!fromClient) {
            Map<String, Integer> worldMultipliers = IncreasedStackSize.settings.getMultipliers(SettingType.WORLD);
            Map<String, Integer> globalMultipliers = IncreasedStackSize.settings.getMultipliers(SettingType.GLOBAL);
            writer.putNextString(StackSizeSetting.mapToString(worldMultipliers));
            writer.putNextString(StackSizeSetting.mapToString(globalMultipliers));
        }
    }

    @Override
    public void processClient(NetworkPacket packet, Client client) {
        IncreasedStackSize.settings.setMultipliers(newWorldMultipliers, SettingType.WORLD);
        IncreasedStackSize.settings.setMultipliers(newGlobalMultipliers, SettingType.GLOBAL);
    }

    @Override
    public void processServer(NetworkPacket packet, Server server, ServerClient client) {
        if (client.checkHasRequestedSelf())
            client.sendPacket(new PacketReadServerMultipliers(false));
    }
}
