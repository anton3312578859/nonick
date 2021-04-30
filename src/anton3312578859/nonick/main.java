package anton3312578859.nonick;

import java.io.File;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
//import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class main extends JavaPlugin implements Listener {
	
	Logger log = Logger.getLogger("Minecraft");
	
	public void onEnable() {
		getLogger().info("Nonick sucessfully enabled");
		File config = new File(getDataFolder() + File.separator + "config.yml");
		if(!config.exists()) {
			getLogger().info("Creating new config file");
			getConfig().options().copyDefaults(true);
			saveDefaultConfig();
		}
		String tips = getConfig().getString("settings.showtips");
		String regmsg = getConfig().getString("settings.regmessage");
		if (tips == "true")
		{
			getLogger().info("Tip: for reload you can use /nonick or PlugMan");
			getLogger().info("Tip: you can also use color codes with &");
		}
		Bukkit.getPluginManager().registerEvents(this, this);
		if (regmsg == "true") {
			getLogger().info("Event sucessfully registered");
		}
	}
	
	public void onDisable() {
		getLogger().info("Nonick sucessfully disabled");
	}
	
	@EventHandler
	public void join(PlayerLoginEvent e) {
		//Player p = e.getPlayer();
		File config = new File(getDataFolder() + File.separator + "config.yml");
    	FileConfiguration configfile = YamlConfiguration.loadConfiguration(config);
    	java.util.List<String> list = configfile.getStringList("nicknames");
		if (list.contains(e.getPlayer().getName())) {
			String cfgi = getConfig().getString("messages.invalidnickname");
			cfgi = cfgi.replace("&", "\u00a7");
			e.disallow(PlayerLoginEvent.Result.KICK_BANNED, cfgi);
		}
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!sender.hasPermission("nonick.reload")) {
			String noperm = getConfig().getString("messages.noperm");
			noperm = noperm.replace("&", "\u00a7");
			sender.sendMessage(noperm);
			return false;
		}
		reloadConfig();
		String a = getConfig().getString("messages.reload");
		a = a.replace("&", "\u00a7");
		sender.sendMessage(a);
		return true;
	}
}
