package xyz.jxmm.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import xyz.jxmm.Fps_on_Minecraft;
import xyz.jxmm.utils.FileWriterMethod;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class Main {
    static Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
    static File cfgFolder = Fps_on_Minecraft.getPlugin().getDataFolder();
    static File cfg = new File(cfgFolder, "config.json");


    public static JsonObject main() throws IOException {
        JsonObject json = new JsonObject();
        //如果cfg不存在，则创建文件
        if (!cfgFolder.exists()) {
            cfgFolder.mkdirs();
            cfg.createNewFile();
            json = example();
        } else {
            try {
                json = gson.fromJson(new InputStreamReader(Files.newInputStream(cfg.toPath()), StandardCharsets.UTF_8), JsonObject.class);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return json;

    }

    public static JsonObject example(){
        JsonObject gen = new JsonObject();
        gen.add("lobby", lobby());
        FileWriterMethod.fileWriter(cfg.getPath(), gson.toJson(gen));
        return gen;
    }

    public static JsonObject lobby(){
        JsonObject lobby = new JsonObject();
        lobby.add("world", new JsonArray());
        lobby.addProperty("x",0);
        lobby.addProperty("y",0);
        lobby.addProperty("z",0);
        lobby.addProperty("yaw",0);
        lobby.addProperty("pitch",0);
        lobby.addProperty("MULTI-ARENA", true);
        return lobby;

    }
}
