package uk.minersonline.shulker_for_minestom.configs;

public class PaperConfig {
	private Proxies proxies;

	public Proxies getProxies() {
		return proxies;
	}

	public void setProxies(Proxies proxies) {
		this.proxies = proxies;
	}

	public static class ProxyConfig {
		private boolean enabled;
		private String secret;

		public boolean isEnabled() {
			return enabled;
		}

		public void setEnabled(boolean enabled) {
			this.enabled = enabled;
		}

		public String getSecret() {
			return secret;
		}

		public void setSecret(String secret) {
			this.secret = secret;
		}
	}

	public static class Proxies {
		private ProxyConfig velocity;

		public ProxyConfig getVelocity() {
			return velocity;
		}

		public void setVelocity(ProxyConfig velocity) {
			this.velocity = velocity;
		}
	}
}
