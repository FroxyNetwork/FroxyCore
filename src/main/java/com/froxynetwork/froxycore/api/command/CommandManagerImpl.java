package com.froxynetwork.froxycore.api.command;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import com.froxynetwork.froxyapi.command.Command;
import com.froxynetwork.froxyapi.command.CommandManager;

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
public class CommandManagerImpl implements CommandManager, Listener {
	private List<Command> commands;

	public CommandManagerImpl() {
		commands = new ArrayList<>();
	}

	@Override
	public void registerCommand(Command cmd) {
		commands.add(cmd);
	}

	@Override
	public void unregisterCommand(Command cmd) {
		commands.remove(cmd);
	}

	@Override
	public List<Command> getCommands() {
		return commands;
	}

	private Command getCommand(String label) {
		for (Command cmd : commands) {
			if (label.equalsIgnoreCase(cmd.getCommand()))
				return cmd;
			if (cmd.getAliases() != null)
				for (String alias : cmd.getAliases())
					if (label.equalsIgnoreCase(alias))
						return cmd;
		}
		return null;
	}

	private Pattern pattern = Pattern.compile(" ");

	@EventHandler
	public void onPlayerCommand(PlayerCommandPreprocessEvent e) {
		Player p = e.getPlayer();
		String msg = e.getMessage();
		if (p == null || msg == null)
			return;
		if (msg.startsWith("/")) {
			String[] split = pattern.split(msg);
			String label = split[0].substring(1);
			String[] args = new String[split.length - 1];
			if (split.length >= 2)
				for (int i = 1; i < split.length; i++)
					args[i - 1] = split[i];
			if (_onPlayerCommand(p, label, args))
				e.setCancelled(true);
		}
	}

	private boolean _onPlayerCommand(Player p, String label, String[] args) {
		Command cmd = getCommand(label);
		if (cmd == null)
			return false;
		String perm = cmd.getPermission();
		if (perm != null && !p.hasPermission(perm)) {
			cmd.noPermission(p);
			return true;
		}
		return cmd.execute(label, p, args);
	}
}
