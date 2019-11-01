package com.froxynetwork.froxycore.api.language;

import java.io.File;

import com.froxynetwork.froxyapi.language.LanguageManager;
import com.froxynetwork.froxyapi.language.Languages;

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
public class LanguageManagerImpl implements LanguageManager {

	@Override
	public Languages getDefaultLanguage() {
		return langGameToApi(com.froxynetwork.froxygame.languages.LanguageManager.DEFAULT);
	}

	@Override
	public void register(File path) {
		com.froxynetwork.froxygame.languages.LanguageManager.register(path);
	}

	@Override
	public String $(String id, Languages lang, String... params) {
		return com.froxynetwork.froxygame.languages.LanguageManager.$(id, langApiToGame(lang), params).replace("&", "§");
	}

	@Override
	public String $_(String id, Languages lang, String... params) {
		return com.froxynetwork.froxygame.languages.LanguageManager.$_(id, langApiToGame(lang), params).replace("&", "§");
	}

	private com.froxynetwork.froxygame.languages.Languages langApiToGame(Languages lang) {
		return com.froxynetwork.froxygame.languages.Languages.fromLang(lang.getLang());
	}

	private Languages langGameToApi(com.froxynetwork.froxygame.languages.Languages lang) {
		return Languages.fromLang(lang.getLang());
	}
}
