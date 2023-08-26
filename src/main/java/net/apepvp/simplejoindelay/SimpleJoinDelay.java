package net.apepvp.simplejoindelay;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class SimpleJoinDelay extends JavaPlugin implements Listener {
  private boolean serverJustStarted = true;
  private int joinDelaySeconds; // Variable to store the join delay in seconds

  @Override
  public void onEnable() {
    saveDefaultConfig();
    reloadConfig();
    joinDelaySeconds = getConfig().getInt("join_delay_seconds", 20);
    getServer().getPluginManager().registerEvents(this, this);
  }

  @EventHandler
  public void onPlayerLogin(PlayerLoginEvent event) {
    if (serverJustStarted) {
      event.disallow(PlayerLoginEvent.Result.KICK_OTHER, "sᴇʀᴠᴇʀ ɪs sᴛɪʟʟ sᴛᴀʀᴛɪɴɢ. ᴘʟᴇᴀsᴇ ᴡᴀɪᴛ "+ joinDelaySeconds +" sᴇᴄᴏɴᴅs.");
      getServer().getScheduler().scheduleSyncDelayedTask(this, () -> serverJustStarted = false, joinDelaySeconds * 20);
    }
  }
}
