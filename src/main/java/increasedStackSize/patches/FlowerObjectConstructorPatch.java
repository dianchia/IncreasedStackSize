package increasedStackSize.patches;

import increasedStackSize.IncreasedStackSize;
import necesse.engine.modLoader.annotations.ModConstructorPatch;
import necesse.level.gameObject.furniture.FlowerObject;
import net.bytebuddy.asm.Advice;

import java.awt.*;

@ModConstructorPatch(target = FlowerObject.class, arguments = {String.class, int.class, int.class, String.class, Color.class})
public class FlowerObjectConstructorPatch {
    @Advice.OnMethodExit
    static void onExit(@Advice.FieldValue(value = "stackSize", readOnly = false)int stackSize) {
        stackSize = stackSize * IncreasedStackSize.config.getMultiplier();
    }
}
