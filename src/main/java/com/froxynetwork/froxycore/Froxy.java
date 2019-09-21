package com.froxynetwork.froxycore;

import java.io.File;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.froxynetwork.froxyapi.API;
import com.froxynetwork.froxyapi.command.Command;
import com.froxynetwork.froxyapi.inventory.Inventory;
import com.froxynetwork.froxyapi.inventory.InventoryProvider;
import com.froxynetwork.froxyapi.language.Languages;
import com.froxynetwork.froxycore.api.command.CommandManagerImpl;
import com.froxynetwork.froxycore.api.inventory.InventoryManagerImpl;
import com.froxynetwork.froxycore.api.language.LanguageManagerImpl;
import com.froxynetwork.froxycore.websocket.WebSocketManager;
import com.froxynetwork.froxynetwork.network.NetworkManager;

/**
 * MIT License
 *
 * Copyright (c) 2019 FroxyNetwork
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 * 
 * @author 0ddlyoko
 */
/**
 * This class is linked to
 * {@link com.froxynetwork.froxycom.froxynetwork.froxyapi.Froxy.Froxy} and has
 * more methods than the com.froxynetwork.froxyapi.Froxy.
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
	 * @param com.froxynetwork.froxyapi.Froxy
	 *            API instance
	 */
	public static void setAPI(API api) {
		Froxy.setAPI(api);
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
	 * @return The version of the actual Core
	 */
	public static String getVersion() {
		return com.froxynetwork.froxyapi.Froxy.getVersion();
	}

	// -----------------------------------------
	// |                                       |
	// |           Language Manager            |
	// |                                       |
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
	 * @param path
	 *            The directory
	 */
	public static void register(File path) {
		com.froxynetwork.froxyapi.Froxy.register(path);
	}

	/**
	 * Get the default translate of specific message id.<br />
	 * Same as <code>$(id, getDefaultLanguage(), params)</code>
	 * 
	 * @param id
	 *            The id of the message
	 * @param params
	 *            The parameters
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
	 * @param id
	 *            The id of the message
	 * @param lang
	 *            The specific language
	 * @param params
	 *            The parameters
	 * @return The message translated by specific language, or the message
	 *         translated by default language, or the id if message id doesn't exist
	 */
	public static String $(String id, Languages lang, String... params) {
		return com.froxynetwork.froxyapi.Froxy.$(id, lang, params);
	}

	/**
	 * Get the translate of specific id with specific language
	 * 
	 * @param id
	 *            The id of the message
	 * @param lang
	 *            The specific language
	 * @param params
	 *            The parameters
	 * @return The message translated by specific language, or the id if message id
	 *         doesn't exist
	 */
	public static String $_(String id, Languages lang, String... params) {
		return com.froxynetwork.froxyapi.Froxy.$_(id, lang, params);
	}
	
	// -----------------------------------------
	// |                                       |
	// |            Command Manager            |
	// |                                       |
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
	 * @param cmd
	 *            The command
	 */
	public static void registerCommand(Command cmd) {
		com.froxynetwork.froxyapi.Froxy.registerCommand(cmd);
	}

	/**
	 * Unregister a command
	 * 
	 * @param cmd
	 *            The command
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
	// |                                       |
	// |          Inventory Manager            |
	// |                                       |
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
	 * @param provider
	 *            The provider
	 * @param player
	 *            The player
	 * @return An inventory
	 */
	public static Inventory openInventory(InventoryProvider provider, Player player) {
		return com.froxynetwork.froxyapi.Froxy.openInventory(provider, player);
	}

	/**
	 * @param p
	 *            Player to check
	 * 
	 * @return true if specific Player has an opened inventory
	 */
	public static boolean hasInventoryOpened(Player p) {
		return com.froxynetwork.froxyapi.Froxy.hasInventoryOpened(p);
	}

	/**
	 * @param p
	 *            Specific player
	 * @return The inventory of specific Player. Null if not opened
	 */
	public static Inventory getInventory(Player p) {
		return com.froxynetwork.froxyapi.Froxy.getInventory(p);
	}

	/**
	 * Close player's inventory.<br />
	 * Same as <code>p.closeInventory();</code>
	 * 
	 * @param p
	 *            The player
	 */
	public static void closeInventory(Player p) {
		com.froxynetwork.froxyapi.Froxy.closeInventory(p);
	}
}
