package increasedStackSize;

import necesse.engine.GlobalData;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Objects;

public class Compat {
    public static void backwardCompatLoad() {
        String filename = GlobalData.rootPath() + "settings/increasedStackSize/settings.cfg";
        File file = new File(filename);
        if (!file.exists()) return;
        try {

            InputStreamReader isr = new InputStreamReader(Files.newInputStream(file.toPath()), StandardCharsets.UTF_8);
            BufferedReader br = new BufferedReader(isr);

            String line;
            while ((line = br.readLine()) != null) {
                if (!line.isEmpty()) {
                    String[] temp = line.split("=");
                    if (Objects.equals(temp[0], "multiplier"))
                        IncreasedStackSize.setStackSizeMultiplier(Integer.parseInt(temp[1]), true);
                }
            }
            br.close();
            isr.close();
            boolean success = file.delete();
        } catch (IOException ignored) {
        }
    }
}
