package com.froxynetwork.froxycore;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.froxynetwork.froxyapi.API;
import com.froxynetwork.froxyapi.command.Command;
import com.froxynetwork.froxyapi.inventory.Inventory;
import com.froxynetwork.froxyapi.inventory.InventoryProvider;
import com.froxynetwork.froxyapi.language.Languages;
import com.froxynetwork.froxyapi.player.PlayerManager;
import com.froxynetwork.froxycore.api.command.CommandManagerImpl;
import com.froxynetwork.froxycore.api.inventory.InventoryManagerImpl;
import com.froxynetwork.froxycore.api.language.LanguageManagerImpl;
import com.froxynetwork.froxycore.websocket.WebSocketManager;
import com.froxynetwork.froxynetwork.network.NetworkManager;

/**
 * FroxyCore
 * 
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
/**
 * This class is the same as
 * {@link com.froxynetwork.froxyapi.Froxy this class} but has
 * more methods
 */
public class Froxy {
	private static WebSocketManager webSocketManager;
	private static NetworkManager networkManager;

	private Froxy() {
	}

	public static void init(WebSocketManager webSocketManager, NetworkManager networkManager) {
		Froxy.webSocketManager = webSocketManager;
		Froxy.networkManager = networkManager;
	}

	public static WebSocketManager getWebSocketManager() {
		return webSocketManager;
	}

	public static NetworkManager getNetworkManager() {
		return networkManager;
	}

	// ------------------------------------------------------------

	/**
	 * Gets the current {@link API} singleton
	 *
	 * @return API instance being ran
	 */
	public static API getAPI() {
		return com.froxynetwork.froxyapi.Froxy.getAPI();
	}

	/**
	 * Attempts to set the {@link API} singleton.
	 * <p>
	 * This cannot be done if the API is already set.
	 *
	 * @param com.froxynetwork.froxyapi.Froxy API instance
	 */
	public static void setAPI(API api) {
		com.froxynetwork.froxyapi.Froxy.setAPI(api);
	}

	/**
	 * Register specific plugin as the game
	 */
	public static void register(JavaPlugin plugin) {
		// TODO change JavaPlugin (because it's useless)
		com.froxynetwork.froxyapi.Froxy.register(plugin);
	}

	/**
	 * @return The JavaPlugin implementation of the Core plugin
	 */
	public static JavaPlugin getCorePlugin() {
		return com.froxynetwork.froxyapi.Froxy.getCorePlugin();
	}

	/**
	 * @return The JavaPlugin implementation of the Game plugin
	 */
	public static JavaPlugin getGamePlugin() {
		return com.froxynetwork.froxyapi.Froxy.getGamePlugin();
	}
	
	/**
	 * @return The id of this server
	 */
	public static String getId() {
		return com.froxynetwork.froxyapi.Froxy.getId();
	}
	
	/**
	 * @return The name of this server
	 */
	public static String getName() {
		return com.froxynetwork.froxyapi.Froxy.getName();
	}
	
	/**
	 * @return The type of this server
	 */
	public static String getType() {
		return com.froxynetwork.froxyapi.Froxy.getType();
	}

	/**
	 * @return The creation time of this server
	 */
	public static Date getCreationTime() {
		return com.froxynetwork.froxyapi.Froxy.getCreationTime();
	}

	/**
	 * @return The version of the actual Core
	 */
	public static String getVersion() {
		return com.froxynetwork.froxyapi.Froxy.getVersion();
	}

	// -----------------------------------------
	// |
	// | 			Language Manager
	// |
	// -----------------------------------------

	/**
	 * @return The LanguageManager
	 */
	public static LanguageManagerImpl getLanguageManager() {
		return (LanguageManagerImpl) com.froxynetwork.froxyapi.Froxy.getLanguageManager();
	}

	/**
	 * @return The default language
	 */
	public static Languages getDefaultLanguage() {
		return com.froxynetwork.froxyapi.Froxy.getDefaultLanguage();
	}

	/**
	 * Register a path as a language directory.<br />
	 * All language files MUST have the correct name to be loaded.<br />
	 * Files name MUST be of this form: "{name}.lang".<br />
	 * Example: <code>fr_FR.lang or en_US.lang</code>
	 * 
	 * @param path The directory
	 */
	public static void register(File path) {
		com.froxynetwork.froxyapi.Froxy.register(path);
	}

	/**
	 * Get the default translate of specific message id.<br />
	 * Same as <code>$(id, getDefaultLanguage(), params)</code>
	 * 
	 * @param id     The id of the message
	 * @param params The parameters
	 * @return The message translated by default language, or the id if message id
	 *         doesn't exist
	 */
	public static String $(String id, String... params) {
		return com.froxynetwork.froxyapi.Froxy.$(id, params);
	}

	/**
	 * Get the translation of specific message id with specific language. If message
	 * id not found, return the translation with DEFAULT language
	 * 
	 * @param id     The id of the message
	 * @param lang   The specific language
	 * @param params The parameters
	 * @return The message translated by specific language, or the message
	 *         translated by default language, or the id if message id doesn't exist
	 */
	public static String $(String id, Languages lang, String... params) {
		return com.froxynetwork.froxyapi.Froxy.$(id, lang, params);
	}

	/**
	 * Get the translate of specific id with specific language
	 * 
	 * @param id     The id of the message
	 * @param lang   The specific language
	 * @param params The parameters
	 * @return The message translated by specific language, or the id if message id
	 *         doesn't exist
	 */
	public static String $_(String id, Languages lang, String... params) {
		return com.froxynetwork.froxyapi.Froxy.$_(id, lang, params);
	}

	// -----------------------------------------
	// |
	// | 			Command Manager
	// |
	// -----------------------------------------

	/**
	 * @return The CommandManager
	 */
	public static CommandManagerImpl getCommandManager() {
		return (CommandManagerImpl) com.froxynetwork.froxyapi.Froxy.getCommandManager();
	}

	/**
	 * Register a command
	 * 
	 * @param cmd The command
	 */
	public static void registerCommand(Command cmd) {
		com.froxynetwork.froxyapi.Froxy.registerCommand(cmd);
	}

	/**
	 * Unregister a command
	 * 
	 * @param cmd The command
	 */
	public static void unregisterCommand(Command cmd) {
		com.froxynetwork.froxyapi.Froxy.unregisterCommand(cmd);
	}

	/**
	 * @return All commands
	 */
	public static List<Command> getCommands() {
		return com.froxynetwork.froxyapi.Froxy.getCommands();
	}

	// -----------------------------------------
	// |
	// | 			Inventory Manager
	// |
	// -----------------------------------------

	/**
	 * @return The InventoryManager
	 */
	public static InventoryManagerImpl getInventoryManager() {
		return (InventoryManagerImpl) com.froxynetwork.froxyapi.Froxy.getInventoryManager();
	}

	/**
	 * Create an Inventory and open it
	 * 
	 * @param provider The provider
	 * @param player   The player
	 * @return An inventory
	 */
	public static Inventory openInventory(InventoryProvider provider, Player player) {
		return com.froxynetwork.froxyapi.Froxy.openInventory(provider, player);
	}

	/**
	 * @param p Player to check
	 * 
	 * @return true if specific Player has an opened inventory
	 */
	public static boolean hasInventoryOpened(Player p) {
		return com.froxynetwork.froxyapi.Froxy.hasInventoryOpened(p);
	}

	/**
	 * @param p Specific player
	 * @return The inventory of specific Player. Null if not opened
	 */
	public static Inventory getInventory(Player p) {
		return com.froxynetwork.froxyapi.Froxy.getInventory(p);
	}

	/**
	 * Close player's inventory.<br />
	 * Same as <code>p.closeInventory();</code>
	 * 
	 * @param p The player
	 */
	public static void closeInventory(Player p) {
		com.froxynetwork.froxyapi.Froxy.closeInventory(p);
	}

	// -----------------------------------------
	// |
	// | 			Player Manager
	// |
	// -----------------------------------------

	/**
	 * @return The PlayerManager
	 */
	public static PlayerManager getPlayerManager() {
		return com.froxynetwork.froxyapi.Froxy.getPlayerManager();
	}

	/**
	 * @param name The name of the player
	 * @return The player or null if not found
	 */
	public static com.froxynetwork.froxyapi.player.Player getPlayer(String name) {
		return com.froxynetwork.froxyapi.Froxy.getPlayer(name);
	}

	/**
	 * @param uuid The uuid of the player
	 * @return The player or null if not found
	 */
	public static com.froxynetwork.froxyapi.player.Player getPlayer(UUID uuid) {
		return com.froxynetwork.froxyapi.Froxy.getPlayer(uuid);
	}

	/**
	 * @return An immutable list containing all players
	 */
	public static List<? extends com.froxynetwork.froxyapi.player.Player> getPlayers() {
		return com.froxynetwork.froxyapi.Froxy.getPlayers();
	}

	/**
	 * Edit kill time. Set to -1 to keep last kill or 0 to disable it
	 * 
	 * @param time The new time in seconds
	 */
	public static void setKillTime(int time) {
		com.froxynetwork.froxyapi.Froxy.setKillTime(time);
	}

	/**
	 * @return The current kill time
	 */
	public static int getKillTime() {
		return com.froxynetwork.froxyapi.Froxy.getKillTime();
	}

	/**
	 * Edit assist time. Set to -1 to keep all assists or 0 to disable it
	 * 
	 * @param time The new time in seconds
	 */
	public static void setAssistTime(int time) {
		com.froxynetwork.froxyapi.Froxy.setAssistTime(time);
	}

	/**
	 * @return The current assist time
	 */
	public static int getAssistTime() {
		return com.froxynetwork.froxyapi.Froxy.getAssistTime();
	}
}
