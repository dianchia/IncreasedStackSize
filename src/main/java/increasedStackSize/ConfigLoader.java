package increasedStackSize;

import necesse.engine.GlobalData;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Objects;

public class ConfigLoader {
    private final String filename;
    public Config config = new Config();

    public ConfigLoader(String filename) {
        this.filename = GlobalData.rootPath() + "/settings/increasedStackSize/" + filename;
        loadConfig();
    }

    private void loadConfig() {
        try {
            File file = new File(this.filename);
            if (!file.exists()) {
                createNewFile(file);
            }

            InputStreamReader isr = new InputStreamReader(Files.newInputStream(file.toPath()), StandardCharsets.UTF_8);
            BufferedReader br = new BufferedReader(isr);
            this.loadConfig(br);
            br.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void createNewFile(File file) throws IOException {
        if (!file.getParentFile().mkdirs()) throw new IOException("Error creating directory: " + file.getParentFile().toPath());
        if (!file.createNewFile()) throw new IOException("Error creating file: " + file.toPath());
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(Files.newOutputStream(file.toPath()), StandardCharsets.UTF_8))) {
            writer.write("multiplier=5");
        }
    }

    private void loadConfig(BufferedReader br) throws IOException{
        String line;
        while((line = br.readLine())!= null) {
            if (line.length() != 0) {
                String[] temp = line.split("=");
                if (Objects.equals(temp[0], "multiplier")) config = new Config(Integer.parseInt(temp[1]));
            }
        }
    }

}
