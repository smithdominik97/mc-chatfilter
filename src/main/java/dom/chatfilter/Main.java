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

//    @EventHandler
//    public void getMessage(AsyncPlayerChatEvent event)  {
//        String message = event.getMessage().toLowerCase();
//
//
//        boolean isMessageToxic = checkMessage(message);
//
//        if (isMessageToxic) {
//            event.setCancelled(true);
//            event.getPlayer().sendMessage("Filtered");
//        }
//
//    }

//    public boolean checkMessage(String message) throws IOException {
////        HttpURLConnection connection = connect();
//
//        if(message.equals("toxic")) {
//            return true;
//        }
//
//        return false;
//    }

//    public HttpURLConnection connect() {
//        URL url = null;
//        try {
//            url = new URL("http://localhost:5000");
//        } catch (MalformedURLException e) {
//            throw new RuntimeException(e);
//        }
//
//        HttpURLConnection conn = null;
//        try {
//            conn = (HttpURLConnection) url.openConnection();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//
//        return conn;
//    }


}
