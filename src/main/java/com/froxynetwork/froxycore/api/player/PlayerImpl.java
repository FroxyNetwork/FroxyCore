package com.froxynetwork.froxycore.api.player;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.froxynetwork.froxyapi.language.Languages;
import com.froxynetwork.froxyapi.player.Player;
import com.froxynetwork.froxynetwork.network.output.data.PlayerDataOutput;

import lombok.Getter;

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
@Getter
public class PlayerImpl implements Player {
	private org.bukkit.entity.Player bukkitPlayer;
	private PlayerDataOutput.Player restPlayer;
	private UUID uuid;
	private String nickname;
	private int coins;
	private int level;
	private int exp;
	private Date firstLogin;
	private Date lastLogin;
	private Languages language;

	public PlayerImpl(org.bukkit.entity.Player bukkitPlayer, PlayerDataOutput.Player restPlayer) {
		this.bukkitPlayer = bukkitPlayer;
		this.restPlayer = restPlayer;
		this.uuid = bukkitPlayer.getUniqueId();
		this.nickname = restPlayer.getNickname();
		bukkitPlayer.setDisplayName(restPlayer.getDisplayName());
		this.coins = restPlayer.getCoins();
		this.level = restPlayer.getLevel();
		this.exp = restPlayer.getExp();
		this.firstLogin = restPlayer.getFirstLogin();
		this.lastLogin = restPlayer.getLastLogin();
		this.language = Languages.fromLang(restPlayer.getLang());
	}

	@Override
	public UUID getUUID() {
		return uuid;
	}

	@Override
	public String getRealName() {
		return nickname;
	}

	@Override
	public String getDisplayName() {
		return bukkitPlayer.getDisplayName();
	}

	@Override
	@Deprecated
	public void sendMessage(String message) {
		bukkitPlayer.sendMessage(message);
	}

	@Override
	public UUID getKiller() {
		// TODO
		return null;
	}

	@Override
	public List<UUID> getAssists() {
		// TODO
		return new ArrayList<>();
	}
}
