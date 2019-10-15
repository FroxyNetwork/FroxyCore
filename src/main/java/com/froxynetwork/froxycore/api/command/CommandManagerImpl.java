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
