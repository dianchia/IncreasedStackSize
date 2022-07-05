package increasedStackSize.patches;

import increasedStackSize.IncreasedStackSize;
import necesse.engine.modLoader.annotations.ModMethodPatch;
import necesse.engine.save.LoadData;
import necesse.engine.world.WorldSettings;
import net.bytebuddy.asm.Advice;

@ModMethodPatch(target = WorldSettings.class, name = "loadSaveScript", arguments = {LoadData.class})
public class WorldSettingsLoadSaveScriptPatch {
    @Advice.OnMethodExit
    static void onExit(@Advice.Argument(0) LoadData save) {
        if (save.hasLoadDataByName("stackSizeMultiplier")) {
            IncreasedStackSize.setStackSizeMultiplier(save.getInt("stackSizeMultiplier"), true);
        }
    }
}
