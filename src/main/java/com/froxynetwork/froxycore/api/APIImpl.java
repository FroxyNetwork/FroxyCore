package com.froxynetwork.froxycore.api;

import org.bukkit.plugin.java.JavaPlugin;
import org.slf4j.Logger;

import com.froxynetwork.froxyapi.API;
import com.froxynetwork.froxyapi.command.CommandManager;
import com.froxynetwork.froxyapi.language.LanguageManager;

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

public class APIImpl implements API {

	private JavaPlugin corePlugin;

	private JavaPlugin gamePlugin;

	private String version;

	private Logger logger;

	private LanguageManager languageManager;

	private CommandManager commandManager;

	public APIImpl(JavaPlugin corePlugin, JavaPlugin gamePlugin, String version, Logger logger,
			LanguageManager languageManager, CommandManager commandManager) {
		this.corePlugin = corePlugin;
		this.gamePlugin = gamePlugin;
		this.version = version;
		this.logger = logger;
		this.languageManager = languageManager;
		this.commandManager = commandManager;
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
	public Logger getLogger() {
		return logger;
	}

	@Override
	public LanguageManager getLanguageManager() {
		return languageManager;
	}

	@Override
	public CommandManager getCommandManager() {
		return commandManager;
	}
}
