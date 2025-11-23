package increasedStackSize.patches;

import increasedStackSize.IncreasedStackSize;
import necesse.engine.modLoader.annotations.ModMethodPatch;
import necesse.engine.save.SaveData;
import necesse.engine.world.WorldSettings;
import net.bytebuddy.asm.Advice;

@ModMethodPatch(target = WorldSettings.class, name = "getSaveScript", arguments = {})
public class WorldSettingsGetSaveScriptPatch {
    @Advice.OnMethodExit
    static void onExit(@Advice.Return(readOnly = false) SaveData save) {
        IncreasedStackSize.settings.addWorldSaveData(save);
    }
}
