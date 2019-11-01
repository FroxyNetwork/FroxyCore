package com.froxynetwork.froxycore.api;

import org.bukkit.plugin.java.JavaPlugin;

import com.froxynetwork.froxyapi.API;
import com.froxynetwork.froxyapi.command.CommandManager;
import com.froxynetwork.froxyapi.inventory.InventoryManager;
import com.froxynetwork.froxyapi.language.LanguageManager;

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
public class APIImpl implements API {

	private JavaPlugin corePlugin;

	private JavaPlugin gamePlugin;

	private String version;

	private LanguageManager languageManager;

	private CommandManager commandManager;

	private InventoryManager inventoryManager;

	public APIImpl(JavaPlugin corePlugin, JavaPlugin gamePlugin, String version, LanguageManager languageManager,
			CommandManager commandManager, InventoryManager inventoryManager) {
		this.corePlugin = corePlugin;
		this.gamePlugin = gamePlugin;
		this.version = version;
		this.languageManager = languageManager;
		this.commandManager = commandManager;
		this.inventoryManager = inventoryManager;
	}

	@Override
	public JavaPlugin getCorePlugin() {
		return corePlugin;
	}

	@Override
	public JavaPlugin getGamePlugin() {
		return gamePlugin;
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
}
