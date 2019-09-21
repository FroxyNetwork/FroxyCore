package com.froxynetwork.froxycore.websocket;

import java.net.URISyntaxException;

import org.bukkit.Bukkit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.froxynetwork.froxycore.Froxy;

import lombok.Getter;

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
 * A manager for the WebSocket
 */
public class WebSocketManager {
	private final Logger LOG = LoggerFactory.getLogger(getClass());

	private String url;
	private String id;
	private String clientId;
	@Getter
	private com.froxynetwork.froxynetwork.network.websocket.WebSocketManager webSocketManager;

	public WebSocketManager(String url, String id, String clientId) throws URISyntaxException {
		this.webSocketManager = new com.froxynetwork.froxynetwork.network.websocket.WebSocketManager(url);
	}

	/**
	 * Connects to the WebSocket and authentificates itself<br />
	 * This method create a new Thread ! To be notified, see
	 * {@link com.froxynetwork.froxynetwork.network.websocket.WebSocketManager#registerWebSocketAuthentified(Runnable)}
	 */
	public void connect() {
		new Thread(() -> {
			if (Froxy.getNetworkManager().isTokenExpired()) {
				LOG.info("Expired token, asking another one");
				// Expired token, asking another one
				try {
					Froxy.getNetworkManager().getNetwork().getServerService().syncGetServer(id);
				} catch (Exception ex) {
					// We don't have to handle exception (even if an exception here is strange)
					// because we only need the token
				}
				// Test if token is still expired
				if (Froxy.getNetworkManager().isTokenExpired()) {
					// Error
					LOG.error("Token is still expired !");
					// TODO Do not shutdown the app
					Bukkit.shutdown();
					return;
				}
			}
			webSocketManager.connect(id, clientId, Froxy.getNetworkManager().getAuthenticationToken());
		}, "WebSocketManager-core-connect").start();
	}

	public void disconnect() {
		webSocketManager.disconnect();
	}
}
