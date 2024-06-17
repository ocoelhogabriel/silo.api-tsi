package br.com.telematica.siloapi.model.dto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.telematica.siloapi.model.entity.LoggerEntity;
import br.com.telematica.siloapi.model.enums.LoggerEnum;
import br.com.telematica.siloapi.utils.Utils;

public class LoggerDTO {
	private final Logger logger = LoggerFactory.getLogger(getClass());

	private String data;
	// private String numSerie;
	private LoggerEnum tipoLogger;
	private String mensagem;

	public static String consultaPagable(String value) {
		switch (value) {
		case "data":
			return "logdat";
		// case "numSerie":
		// return "smocod";
		case "tipoLogger":
			return "logtip";
		default:
			throw new IllegalArgumentException("Unexpected value: " + value);
		}
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	// public String getNumSerie() {
	// return numSerie;
	// }

	// public void setNumSerie(String numSerie) {
	// this.numSerie = numSerie;
	// }

	public LoggerEnum getTipoLogger() {
		return tipoLogger;
	}

	public void setTipoLogger(LoggerEnum tipoLogger) {
		this.tipoLogger = tipoLogger;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("LoggerDTO [");
		if (logger != null) {
			builder.append("logger=").append(logger).append(", ");
		}
		if (data != null) {
			builder.append("data=").append(data).append(", ");
		}
		// if (numSerie != null) {
		// builder.append("numSerie=").append(numSerie).append(", ");
		// }
		if (tipoLogger != null) {
			builder.append("tipoLogger=").append(tipoLogger).append(", ");
		}
		if (mensagem != null) {
			builder.append("mensagem=").append(mensagem);
		}
		builder.append("]");
		return builder.toString();
	}

	public LoggerDTO(String data, String numSerie, LoggerEnum tipoLogger, String mensagem) {
		super();
		this.data = data;
		// this.numSerie = numSerie;
		this.tipoLogger = tipoLogger;
		this.mensagem = mensagem;
	}

	public LoggerDTO(LoggerEntity pend) {
		try {
			LoggerEnum enumLogger = LoggerEnum.valueOf(pend.getLogtip());
			this.data = Utils.convertDateToString(pend.getLogdat());
			// this.numSerie = pend.getSmocod().toString();
			this.tipoLogger = enumLogger;
			this.mensagem = pend.getLogmsg();
		} catch (Exception e) {
			logger.info("Error: ", e);
		}
	}

	public LoggerDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

}