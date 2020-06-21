package com.froxynetwork.froxycore.api;

import java.util.Date;

import org.bukkit.plugin.java.JavaPlugin;

import com.froxynetwork.froxyapi.API;
import com.froxynetwork.froxyapi.command.CommandManager;
import com.froxynetwork.froxyapi.inventory.InventoryManager;
import com.froxynetwork.froxyapi.language.LanguageManager;
import com.froxynetwork.froxyapi.player.PlayerManager;
import com.froxynetwork.froxycore.FroxyCore;
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
/**
 * Implementation of the {@link API} interface
 */
public class APIImpl implements API {

	private FroxyCore corePlugin;

	private JavaPlugin gamePlugin;

	private String id;
	private String name;
	private String type;
	private String vps;
	private int port;
	private Date creationTime;

	private String version;

	private LanguageManager languageManager;

	private CommandManager commandManager;

	private InventoryManager inventoryManager;

	private PlayerManager playerManager;

	public APIImpl(FroxyCore corePlugin, Server srv, String version, LanguageManager languageManager,
			CommandManager commandManager, InventoryManager inventoryManager, PlayerManager playerManager) {
		this.corePlugin = corePlugin;
		this.id = srv.getId();
		this.name = srv.getName();
		this.type = srv.getType();
		this.vps = srv.getVps();
		this.port = srv.getPort();
		this.creationTime = srv.getCreationTime();
		this.version = version;
		this.languageManager = languageManager;
		this.commandManager = commandManager;
		this.inventoryManager = inventoryManager;
		this.playerManager = playerManager;
	}

	@Override
	public void register(JavaPlugin plugin) {
		if (this.gamePlugin != null)
			return;
		this.gamePlugin = plugin;
		corePlugin.register();
	}

	@Override
	public FroxyCore getCorePlugin() {
		return corePlugin;
	}

	@Override
	public JavaPlugin getGamePlugin() {
		return gamePlugin;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getType() {
		return type;
	}

	public String getVps() {
		return vps;
	}

	public int getPort() {
		return port;
	}

	@Override
	public Date getCreationTime() {
		return creationTime;
	}

	@Override
	public String getVersion() {
		return version;
	}

	@Override
	public LanguageManager getLanguageManager() {
		return languageManager;
	}

	@Override
	public CommandManager getCommandManager() {
		return commandManager;
	}

	@Override
	public InventoryManager getInventoryManager() {
		return inventoryManager;
	}

	@Override
	public PlayerManager getPlayerManager() {
		return playerManager;
	}
}
