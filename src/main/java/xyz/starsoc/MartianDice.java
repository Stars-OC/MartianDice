package xyz.starsoc;

import net.mamoe.mirai.console.plugin.jvm.JavaPlugin;
import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescriptionBuilder;
import xyz.starsoc.file.Config;
import xyz.starsoc.file.Data;
import xyz.starsoc.file.Message;

public final class MartianDice extends JavaPlugin{
    public static final MartianDice INSTANCE=new MartianDice();

    private MartianDice(){
        super(new JvmPluginDescriptionBuilder("xyz.starsoc.martiandice","0.1.0")
                .name("MartianDice")
                .author("Clusters_stars")
                .build());
    }

    @Override
    public void onEnable(){
        reload();
        getLogger().info("MartianDice 插件加载成功");
    }

    public void reload(){
        reloadPluginConfig(Config.INSTANCE);
        reloadPluginConfig(Message.INSTANCE);
        reloadPluginData(Data.INSTANCE);
    }
}