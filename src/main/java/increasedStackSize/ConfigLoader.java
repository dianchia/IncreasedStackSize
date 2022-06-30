package increasedStackSize;

import necesse.engine.GlobalData;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Objects;

public class ConfigLoader {
    public static void loadConfig(String filename) {
        filename = GlobalData.rootPath() + "/settings/increasedStackSize/" + filename;
        try {
            File file = new File(filename);
            if (!file.exists()) {
                createNewFile(file);
            }

            InputStreamReader isr = new InputStreamReader(Files.newInputStream(file.toPath()), StandardCharsets.UTF_8);
            BufferedReader br = new BufferedReader(isr);
            loadConfig(br);
            br.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private static void createNewFile(File file) throws IOException {
        if (!file.getParentFile().mkdirs()) throw new IOException("Error creating directory: " + file.getParentFile().toPath());
        if (!file.createNewFile()) throw new IOException("Error creating file: " + file.toPath());
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(Files.newOutputStream(file.toPath()), StandardCharsets.UTF_8))) {
            writer.write("multiplier=5");
        }
    }

    private static void loadConfig(BufferedReader br) throws IOException{
        String line;
        while((line = br.readLine())!= null) {
            if (line.length() != 0) {
                String[] temp = line.split("=");
                if (Objects.equals(temp[0], "multiplier")) IncreasedStackSize.config.setMultiplier(Integer.parseInt(temp[1]));
            }
        }
    }

}
