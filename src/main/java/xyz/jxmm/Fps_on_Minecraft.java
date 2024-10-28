package xyz.jxmm;


import com.google.gson.JsonObject;
import org.bukkit.*;
import org.bukkit.command.CommandMap;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.jxmm.commands.Lobby;
import xyz.jxmm.commands.MainCommand;
import xyz.jxmm.commands.Respawn;
import xyz.jxmm.commands.TestCommand;
import xyz.jxmm.events.EventListener;
import xyz.jxmm.gaming.team_sd.inventory.InventoryList;
import xyz.jxmm.map.CreateScoreboard;
import xyz.jxmm.map.config.MapList;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;

public final class Fps_on_Minecraft extends JavaPlugin {

    public static String mainCmd = "fps", link ="https://github.com/jxmm52547/FPS-On-Minecraft";
    public static Plugin plugin;
    public static JsonObject config = new JsonObject();

    public static String lobbyWorld = "";
    public static Location lobbyLocation = null;
    private static xyz.jxmm.api.Duels api;

    @Override
    public void onEnable() {
        getLogger().info("插件已启用!");
        plugin = this;
        api = new API();

        //配置文件
        try {
            config = xyz.jxmm.config.Main.main();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //设置主大厅
        if (!config.getAsJsonObject("lobby").get("world").isJsonArray()){
            setLobbyLocation();
            this.getServer().getWorld(config.getAsJsonObject("lobby").get("world").getAsString()).setTime(6000);
            this.getServer().getWorld(config.getAsJsonObject("lobby").get("world").getAsString()).setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
            this.getServer().getWorld(config.getAsJsonObject("lobby").get("world").getAsString()).setGameRule(GameRule.DO_WEATHER_CYCLE, false);
        }

        // 注册指令
        try{
            Field bukkitCommandMap = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            bukkitCommandMap.setAccessible(true);
            CommandMap commandMap = (CommandMap) bukkitCommandMap.get(Bukkit.getServer());
            commandMap.register(mainCmd, new MainCommand("fps"));
            commandMap.register("", new Lobby("lobby"));
            commandMap.register("", new Respawn("respawn"));
            commandMap.register("", new TestCommand("test"));
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        Bukkit.getServicesManager().register(xyz.jxmm.api.Duels.class, api, this, ServicePriority.Highest);

        // 注册事件监听
        EventListener.main();

        //竞技场目录创建
        if (!new File(plugin.getDataFolder() + "\\arenas").exists()){
            new File(plugin.getDataFolder() + "\\arenas").mkdir();
        }

        //加载地图
        MapList.main();

        //创建计分板
        new CreateScoreboard();

        //加载所有菜单
        new InventoryList();

        //加载计分板及队伍
        CreateScoreboard.load();


        for (World w : Bukkit.getWorlds()){
            w.setDifficulty(Difficulty.PEACEFUL);
        }

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static Plugin getPlugin() {
        return plugin;
    }

    public static void setLobbyLocation(){

        lobbyWorld = config.getAsJsonObject("lobby").get("world").getAsString();
        lobbyLocation = new Location(
                Bukkit.getWorld(lobbyWorld),
                config.getAsJsonObject("lobby").get("x").getAsDouble(),
                config.getAsJsonObject("lobby").get("y").getAsDouble(),
                config.getAsJsonObject("lobby").get("z").getAsDouble());
        Bukkit.getWorld(lobbyWorld).setSpawnLocation(lobbyLocation);
    }
}
