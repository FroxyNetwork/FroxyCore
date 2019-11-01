package com.froxynetwork.froxycore;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.froxynetwork.froxycore.api.APIImpl;
import com.froxynetwork.froxycore.api.command.CommandManagerImpl;
import com.froxynetwork.froxycore.api.inventory.InventoryManagerImpl;
import com.froxynetwork.froxycore.api.language.LanguageManagerImpl;
import com.froxynetwork.froxycore.websocket.CustomInteractionImpl;
import com.froxynetwork.froxycore.websocket.WebSocketManager;
import com.froxynetwork.froxynetwork.network.NetworkManager;
import com.froxynetwork.froxynetwork.network.output.RestException;

/**
 * FroxyCore
 * Copyright (C) 2019 FroxyNetwork
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
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
			// Config
			config = new Config(new File("plugins" + File.separator + "FroxyCore" + File.separator + "config.yml"));
			LOG.info("Reading auth file ...");
			// Auth file
			String[] auth = readAuthFile();
			if (auth == null || auth.length != 3 || !checkNotNullOrEmpty(auth)) {
				LOG.error("Auth file is null or there is missing lines ! Stopping ...");
				Bukkit.shutdown();
				return;
			}
			LOG.info("Done");

			LOG.info("Contacting REST ...");
			// Contacting rest
			NetworkManager networkManager = new NetworkManager(config.getString("url"), auth[1], auth[2]);
			try {
				// Used to generate the token
				networkManager.getNetwork().getServerService().syncGetServer(auth[0]);
			} catch (RestException ex) {
				LOG.error("Error {} while contacting REST server:", ex.getError().getErrorId());
				LOG.error("", ex);
				Bukkit.shutdown();
				return;
			} catch (Exception ex) {
				LOG.error("Error while contacting REST server:", ex);
				Bukkit.shutdown();
				return;
			}
			LOG.info("Done");

			LOG.info("Contacting WebSocket server");
			WebSocketManager webSocketManager;
			try {
				webSocketManager = new WebSocketManager(config.getString("websocket"), auth[0], auth[1],
						new CustomInteractionImpl());
			} catch (URISyntaxException ex) {
				LOG.error("Invalid url while initializing WebSocket: ", ex);
				Bukkit.shutdown();
				return;
			}
			LOG.info("Done");

			LOG.info("Loading Managers ...");
			LanguageManagerImpl languageManager = new LanguageManagerImpl();
			CommandManagerImpl commandManager = new CommandManagerImpl();
			InventoryManagerImpl inventoryManager = new InventoryManagerImpl();
			LOG.info("Done");

			LOG.info("Doing some stuff ...");
			// Register events
			Bukkit.getPluginManager().registerEvents(commandManager, this);
			// Initializing api
			APIImpl impl = new APIImpl(this, null, Constants.VERSION, languageManager, commandManager,
					inventoryManager);
			Froxy.setAPI(impl);
			Froxy.init(webSocketManager, networkManager);
			LOG.info("Done");

			LOG.info("Initializing InventoryManager ...");
			// Initialize InventoryManager
			inventoryManager.init();
			LOG.info("Done");

			LOG.info("Starting Thread for WebSocket checker ...");
			webSocketManager.startThread();
			LOG.info("Done");

			// TODO EDIT HERE
			// Register lang directory
			File lang = new File("plugins" + File.separator + getDescription().getName() + File.separator + "lang");
			Froxy.register(lang);
			LOG.info("FroxyCore started !");
		} catch (Exception ex) {
			LOG.error("An error has occured while loading the core: ", ex);
			Bukkit.shutdown();
		}
	}

	@Override
	public void onDisable() {
		LOG.info("Stopping FroxyCore, please wait");
		Froxy.getWebSocketManager().stop();
		LOG.info("FroxyCore stopped !");
	}

	/**
	 * @return [id, client_id, client_secret]
	 */
	private String[] readAuthFile() {
		// The file name
		String fileName = "plugins" + File.separator + "FroxyCore" + File.separator + "auth";
		try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
			String[] result = new String[] { reader.readLine(), reader.readLine(), reader.readLine() };
			return result;
		} catch (FileNotFoundException ex) {
			LOG.error("auth file not found");
			return new String[] {};
		} catch (IOException ex) {
			LOG.error("Exception while reading auth file:", ex);
			return new String[] {};
		}
	}

	private boolean checkNotNullOrEmpty(String[] arr) {
		if (arr == null)
			return false;
		for (String str : arr)
			if (str == null || "".equalsIgnoreCase(str.trim()))
				return false;
		return true;
	}
}
