package dom.chatfilter;
import org.bukkit.plugin.java.JavaPlugin;


public class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        getLogger().info("onEnable is called!");
        getServer().getPluginManager().registerEvents(new ChatFilter(), this);
    }
    @Override
    public void onDisable() {
        getLogger().info("onDisable is called!");
    }

}
