package increasedStackSize.packets;

import increasedStackSize.IncreasedStackSize;
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

public class PacketReadServerSettings extends Packet {
    Map<String, Integer> newSettings = new HashMap<>();

    // Required for the registry
    public PacketReadServerSettings(byte[] data) {
        super(data);
        PacketReader reader = new PacketReader(this);
        if (!reader.getNextBoolean())
            newSettings = StackSizeSetting.mapFromString(reader.getNextString());
    }

    public PacketReadServerSettings(boolean fromClient) {
        PacketWriter writer = new PacketWriter(this);
        writer.putNextBoolean(fromClient);
        if (!fromClient)
            writer.putNextString(IncreasedStackSize.settings.toString());
    }

    @Override
    public void processClient(NetworkPacket packet, Client client) {
        IncreasedStackSize.settings.setMultipliers(newSettings);
    }

    @Override
    public void processServer(NetworkPacket packet, Server server, ServerClient client) {
        if (client.checkHasRequestedSelf())
            client.sendPacket(new PacketReadServerSettings(false));
    }
}
