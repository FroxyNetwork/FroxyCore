package com.froxynetwork.froxycore;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.froxynetwork.froxycore.api.APIImpl;
import com.froxynetwork.froxycore.api.command.CommandManagerImpl;
import com.froxynetwork.froxycore.api.inventory.InventoryManagerImpl;
import com.froxynetwork.froxycore.api.language.LanguageManagerImpl;
import com.froxynetwork.froxycore.api.player.PlayerManagerImpl;
import com.froxynetwork.froxycore.event.PlayerEvent;
import com.froxynetwork.froxycore.websocket.WebSocketManager;
import com.froxynetwork.froxynetwork.network.NetworkManager;
import com.froxynetwork.froxynetwork.network.output.data.server.ServerDataOutput.Server;

/**
 * FroxyCore
 * 
 * Copyright (C) 2019 FroxyNetwork
 * 
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 *
 * @author 0ddlyoko
 */
public class FroxyCore extends JavaPlugin {

	private final Logger LOG = LoggerFactory.getLogger(getClass());
	private Config config;

	@Override
	public void onEnable() {
		try {
			LOG.info("Starting FroxyCore, please wait");
			saveDefaultConfig();
			// Config
			config = new Config(new File("plugins" + File.separator + "FroxyCore" + File.separator + "config.yml"));
			LOG.info("Reading auth file ...");
			// Auth file
			String[] auth = readAuthFile(new File("plugins" + File.separator + "FroxyCore" + File.separator + "auth"));
			if (auth == null || auth.length != 2 || !isNotNullOrEmpty(auth)) {
				LOG.error("Auth file is null or there is missing lines ! Stopping ...");
				Bukkit.shutdown();
				return;
			}
			LOG.info("Done");

			LOG.info("Contacting REST ...");
			NetworkManager networkManager = new NetworkManager(config.getString("url"), auth[0], auth[1]);
			Server srv = networkManager.getNetwork().getServerService().syncGetServer(auth[0]);
			LOG.info("Done");

			LOG.info("Loading Managers ...");
			LOG.info("WebSocketManager ...");
			WebSocketManager webSocketManager = new WebSocketManager(new URI(config.getString("websocket")));
			LOG.info("LanguageManager ...");
			LanguageManagerImpl languageManager = new LanguageManagerImpl();
			LOG.info("CommandManager ...");
			CommandManagerImpl commandManager = new CommandManagerImpl();
			LOG.info("InventoryManager ...");
			InventoryManagerImpl inventoryManager = new InventoryManagerImpl();
			LOG.info("PlayerManager ...");
			PlayerManagerImpl playerManager = new PlayerManagerImpl();
			LOG.info("Done");

			LOG.info("Doing some stuff ...");
			// Initializing api
			APIImpl impl = new APIImpl(this, srv, Constants.VERSION, languageManager, commandManager, inventoryManager,
					playerManager);
			Froxy.setAPI(impl);
			Froxy.init(webSocketManager, networkManager);
			LOG.info("Done");

			LOG.info("Initializing InventoryManager ...");
			// Initialize InventoryManager
			inventoryManager.init();
			LOG.info("Done");

			LOG.info("Registering events ...");
			Bukkit.getPluginManager().registerEvents(commandManager, this);
			Bukkit.getPluginManager().registerEvents(playerManager, this);
			Bukkit.getPluginManager().registerEvents(new PlayerEvent(), this);
			LOG.info("Done");

			// TODO EDIT HERE
			// Register lang directory
			File lang = new File("plugins" + File.separator + getDescription().getName() + File.separator + "lang");
			Froxy.register(lang);
			LOG.info("FroxyCore started !");

			// TODO Remove this line in the futur
			// Simulate register
			Froxy.register(this);
		} catch (Exception ex) {
			LOG.error("An error has occured while loading the core: ", ex);
			Bukkit.shutdown();
		}
	}

	@Override
	public void onDisable() {
		LOG.info("Stopping FroxyCore, please wait");
		if (Froxy.getWebSocketManager() != null)
			Froxy.getWebSocketManager().stop();
		LOG.info("FroxyCore stopped !");
	}

	/**
	 * @return [id, client_secret]
	 */
	public String[] readAuthFile(File file) {
		if (file == null || !file.exists())
			return new String[] {};
		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
			String[] result = new String[] { reader.readLine(), reader.readLine() };
			return result;
		} catch (FileNotFoundException ex) {
			LOG.error("auth file not found");
			return new String[] {};
		} catch (IOException ex) {
			LOG.error("Exception while reading auth file:", ex);
			return new String[] {};
		}
	}

	public boolean isNotNullOrEmpty(String[] arr) {
		if (arr == null)
			return false;
		for (String str : arr)
			if (str == null || "".equalsIgnoreCase(str.trim()))
				return false;
		return true;
	}

	/**
	 * Called when the game is registered
	 */
	public void register() {
		// Contact WebSocket
		LOG.info("Contacting WebSocket ...");
		try {
			Froxy.getWebSocketManager().load();
		} catch (URISyntaxException ex) {
			LOG.error("Invalid url while initializing WebSocket: ", ex);
			Bukkit.shutdown();
			return;
		}
		LOG.info("Done");
	}
}
