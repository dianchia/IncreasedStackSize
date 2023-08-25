package increasedStackSize.commands;

import increasedStackSize.IncreasedStackSize;
import necesse.engine.commands.CommandLog;
import necesse.engine.commands.ModularChatCommand;
import necesse.engine.commands.PermissionLevel;
import necesse.engine.localization.Localization;
import necesse.engine.network.client.Client;
import necesse.engine.network.server.Server;
import necesse.engine.network.server.ServerClient;

public class GetStackSizeCommand extends ModularChatCommand {
    public GetStackSizeCommand() {
        super("getstacksizemultiplier", "Get stack size multiplier", PermissionLevel.USER, false);
    }

    @Override
    public void runModular(Client client, Server server, ServerClient serverClient, Object[] objects, String[] strings, CommandLog commandLog) {
        commandLog.add(Localization.translate("increasedstacksize", "newcommand"));
        commandLog.add(Localization.translate("increasedstacksize", "getstacksize", "stacksize", IncreasedStackSize.stackSizeMultiplier));
    }
}
