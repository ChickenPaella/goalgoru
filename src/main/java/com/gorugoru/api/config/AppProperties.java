package com.gorugoru.api.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Custom app properties in applcation.properties file
 * @author Administrator
 *
 */

@Component
@ConfigurationProperties("app")
public class AppProperties {

	/**
	 * application's name.
	 */
	private String name = "application";
	private Version version = new Version();
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Version getVersion() {
		return version;
	}

	public void setVersion(Version version) {
		this.version = version;
	}

	public static class Version {
		
		/**
		 * application's version id.
		 */
		private int id = 1;
		/**
		 * application's version name.
		 */
        private String name = "0.0.1";
        
        public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}

    }

}
