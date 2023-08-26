package net.apepvp.simplejoindelay;

import org.bstats.bukkit.Metrics;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class SimpleJoinDelay extends JavaPlugin implements Listener {
  private boolean serverJustStarted = true;
  private String playerKickMessage;
  private int joinDelaySeconds; // Variable to store the join delay in seconds

  @Override
  public void onEnable() {
    saveDefaultConfig();
    reloadConfig();
    joinDelaySeconds = getConfig().getInt("join_delay_seconds", 20);
    playerKickMessage = getConfig().getString("kick_message", "Server is still starting, please wait!");
    getServer().getPluginManager().registerEvents(this, this);
    int pluginId = 19662;
    Metrics metrics = new Metrics(this, pluginId);
    getServer().getScheduler().scheduleSyncDelayedTask(this, () -> serverJustStarted = false, joinDelaySeconds * 20);
  }

  @EventHandler
  public void onPlayerLogin(PlayerLoginEvent event) {
    if (serverJustStarted) {
      event.disallow(PlayerLoginEvent.Result.KICK_OTHER, playerKickMessage);
    }
  }
}
