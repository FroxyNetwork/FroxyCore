/**
 * Copyright (c) Smals
 */
package com.froxynetwork.froxycore.api;

import org.bukkit.plugin.java.JavaPlugin;
import org.slf4j.Logger;

import com.froxynetwork.froxyapi.API;
import com.froxynetwork.froxyapi.language.LanguageManager;

/**
 * @author Nathan Giacomello (nagi)
 *
 */
public class APIImpl implements API {

	private JavaPlugin corePlugin;

	private JavaPlugin gamePlugin;

	private String version;

	private Logger logger;

	private LanguageManager languageManager;

	public APIImpl(JavaPlugin corePlugin, JavaPlugin gamePlugin, String version, Logger logger, LanguageManager languageManager) {
		this.corePlugin = corePlugin;
		this.gamePlugin = gamePlugin;
		this.version = version;
		this.logger = logger;
		this.languageManager = languageManager;
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
}
