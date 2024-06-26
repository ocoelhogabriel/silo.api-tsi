package br.com.telematica.siloapi.model.enums;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum ServerMapEnum {
	SILOAPI("/siloapi"), SIRENEAUTH("/sirene.auth"), SIRENEV1("/sirenev1");

	private static final Logger logger = LoggerFactory.getLogger(ServerMapEnum.class);
	private final String server;

	ServerMapEnum(String versao) {
		this.server = versao;
	}

	public String getServer() {
		return server;
	}

	public static String mapDescricaoToServer(String descricao) {
		for (ServerMapEnum du : ServerMapEnum.values()) {
			if (du.getServer().equalsIgnoreCase(descricao)) {
				return du.getServer();
			}
		}
		logger.error("Descrição não mapeada: " + descricao);
		return null;
	}

}
