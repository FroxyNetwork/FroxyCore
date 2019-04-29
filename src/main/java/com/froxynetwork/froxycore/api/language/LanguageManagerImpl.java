package com.froxynetwork.froxycore.api.language;

import java.io.File;

import com.froxynetwork.froxyapi.language.LanguageManager;
import com.froxynetwork.froxyapi.language.Languages;

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
		return com.froxynetwork.froxygame.languages.LanguageManager.$(id, langApiToGame(lang), params);
	}

	@Override
	public String $_(String id, Languages lang, String... params) {
		return com.froxynetwork.froxygame.languages.LanguageManager.$_(id, langApiToGame(lang), params);
	}

	private com.froxynetwork.froxygame.languages.Languages langApiToGame(Languages lang) {
		return com.froxynetwork.froxygame.languages.Languages.fromLang(lang.getLang());
	}

	private Languages langGameToApi(com.froxynetwork.froxygame.languages.Languages lang) {
		return Languages.fromLang(lang.getLang());
	}
}
