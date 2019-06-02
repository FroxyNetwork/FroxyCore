package com.froxynetwork.froxycore;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.froxynetwork.froxyapi.Froxy;
import com.froxynetwork.froxycore.api.APIImpl;
import com.froxynetwork.froxycore.api.command.CommandManagerImpl;
import com.froxynetwork.froxycore.api.inventory.InventoryManagerImpl;
import com.froxynetwork.froxycore.api.language.LanguageManagerImpl;

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
public class FroxyCore extends JavaPlugin {

	private Logger log = LoggerFactory.getLogger(getClass());

	@Override
	public void onEnable() {
		log.info("Starting FroxyCore, please wait");
		// String clientId = "WEBSOCKET_045cfff18fe0ab8393178e7b7826f227";
		// String clientSecret = "SECRET_ecfdc21a8d5022e2db64b1315b087aaf";
		// NetworkManager nm = new NetworkManager("http://localhost/", clientId,
		// clientSecret);

		LanguageManagerImpl languageManager = new LanguageManagerImpl();
		CommandManagerImpl commandManager = new CommandManagerImpl();
		InventoryManagerImpl inventoryManager = new InventoryManagerImpl();
		Bukkit.getPluginManager().registerEvents(commandManager, this);
		APIImpl impl = new APIImpl(this, null, Constants.VERSION, log, languageManager, commandManager,
				inventoryManager);
		Froxy.setAPI(impl);
		// TODO EDIT HERE
		File lang = new File("plugins" + File.separator + getDescription().getName() + File.separator + "lang");
		Froxy.register(lang);
		log.info("FroxyCore started !");
	}
}
