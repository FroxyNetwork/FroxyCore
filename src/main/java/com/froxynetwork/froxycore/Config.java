package com.froxynetwork.froxycore;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

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
public class Config {
	private File fichierConfig;
	private FileConfiguration fconfig;

	public Config(File file) {
		this.fichierConfig = file;
		loadConfig();
	}

	public void save() {
		try {
			fconfig.save(fichierConfig);
		} catch (IOException ex) {
			Bukkit.getLogger().severe("An error has occured while saving file " + fichierConfig.getPath());
		}
	}

	private void loadConfig() {
		fconfig = YamlConfiguration.loadConfiguration(fichierConfig);
	}

	public void set(String path, Object obj) {
		if (obj instanceof Location) {
			Location loc = (Location) obj;
			fconfig.set(path + ".x", loc.getX());
			fconfig.set(path + ".y", loc.getY());
			fconfig.set(path + ".z", loc.getZ());
			fconfig.set(path + ".yaw", loc.getYaw());
			fconfig.set(path + ".pitch", loc.getPitch());
			fconfig.set(path + ".world", loc.getWorld().getName());
		} else
			fconfig.set(path, obj);
		save();
	}

	public String getString(String path) {
		return getString(path, new Object[] {});
	}

	public String getString(String path, Object... lists) {
		String name = fconfig.getString(path);
		if (name != null && lists != null)
			for (int i = 0; i < lists.length; i++)
				name = name.replace("{" + i + "}", lists[i].toString());

		return name == null ? null : name.replace("&", "§");
	}

	public int getInt(String path) {
		return fconfig.getInt(path);
	}

	public long getLong(String path) {
		return fconfig.getLong(path);
	}

	public boolean getBoolean(String path) {
		return fconfig.getBoolean(path);
	}

	public double getDouble(String path) {
		return fconfig.getDouble(path);
	}

	public List<String> getStringList(String path) {
		List<String> name = new ArrayList<>();
		for (String nom : fconfig.getStringList(path))
			name.add(nom.replace("&", "§"));
		return name;
	}

	public List<Integer> getIntegerList(String path) {
		List<Integer> name = new ArrayList<>();
		for (Integer nom : fconfig.getIntegerList(path))
			name.add(nom);
		return name;
	}

	public List<String> getKeys(String path) {
		List<String> list = new ArrayList<>();
		if ("".equalsIgnoreCase(path)) {
			for (String section : fconfig.getKeys(false))
				list.add(section);
		} else {
			ConfigurationSection cs = fconfig.getConfigurationSection(path);
			if (cs == null)
				return list;
			for (String section : cs.getKeys(false))
				list.add(section);
		}
		return list;
	}

	public ItemStack getItem(String path) {
		return fconfig.getItemStack(path);
	}

	public Location getLocation(String path) {
		double x = fconfig.getDouble(path + ".x");
		double y = fconfig.getDouble(path + ".y");
		double z = fconfig.getDouble(path + ".z");
		float yaw = (float) fconfig.getDouble(path + ".yaw");
		float pitch = (float) fconfig.getDouble(path + ".pitch");
		String world = fconfig.getString(path + ".world");
		if (world == null || "".equalsIgnoreCase(world))
			return null;
		return new Location(Bukkit.getWorld(world), x, y, z, yaw, pitch);
	}

	public boolean exist(String path) {
		return fconfig.contains(path);
	}

	public void reload() {
		loadConfig();
	}

	public File getFile() {
		return fichierConfig;
	}
}
