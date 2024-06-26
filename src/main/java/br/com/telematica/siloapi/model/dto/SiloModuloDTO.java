package br.com.telematica.siloapi.model.dto;

import br.com.telematica.siloapi.model.entity.SiloModulo;
import br.com.telematica.siloapi.model.enums.StatusDeviceEnum;
import br.com.telematica.siloapi.utils.Utils;

public class SiloModuloDTO extends CodigoExtends {

	private SiloDTO silo;
	private String descricao;
	private Long totalSensor;
	private String numSerie;
	private Long timeoutKeepAlive;
	private Long timeoutMedicao;
	private String ultimaKeepAlive;
	private String ultimaMedicao;
	private Integer gmt;
	private String corKeepAlive;
	private String corMedicao;
	private StatusDeviceEnum status;
	private String volumeCheio = "0.00";
	private String volumeStatus = "0.00";

	public static String filtrarDirecao(String str) {
		switch (str.toUpperCase()) {
		case "CODIGO" -> {
			return "smocod";
		}
		case "SILO" -> {
			return "silcod";
		}
		case "DESCRICAO" -> {
			return "smodes";
		}
		case "NUMSERIE" -> {
			return "smonse";
		}
		default -> throw new AssertionError();
		}
	}
	
	public SiloModuloDTO(Long codigo, SiloDTO silo, String descricao, Long totalSensor, String numSerie, Long timeoutKeepAlive, Long timeoutMedicao, String ultimaKeepAlive, String ultimaMedicao, Integer gmt, String corKeepAlive, String corMedicao, StatusDeviceEnum status, String volumeCheio,
			String volumeStatus) {
		super(codigo);
		this.silo = silo;
		this.descricao = descricao;
		this.totalSensor = totalSensor;
		this.numSerie = numSerie;
		this.timeoutKeepAlive = timeoutKeepAlive;
		this.timeoutMedicao = timeoutMedicao;
		this.ultimaKeepAlive = ultimaKeepAlive;
		this.ultimaMedicao = ultimaMedicao;
		this.gmt = gmt;
		this.corKeepAlive = corKeepAlive;
		this.corMedicao = corMedicao;
		this.status = status;
		this.volumeCheio = volumeCheio;
		this.volumeStatus = volumeStatus;
	}

	public SiloModuloDTO(SiloModulo silomodulo) {
		this.setCodigo(silomodulo.getSmocod());
		this.silo = new SiloDTO(silomodulo.getSilo());
		this.descricao = silomodulo.getSmodes();
		this.totalSensor = silomodulo.getSmotse();
		this.numSerie = silomodulo.getSmonse();
		this.timeoutKeepAlive = silomodulo.getSmotke();
		this.timeoutMedicao = silomodulo.getSmotme();
		this.ultimaKeepAlive = silomodulo.getSmohke() == null ? null : Utils.convertDateToString(silomodulo.getSmohke());
		this.ultimaMedicao = silomodulo.getSmohme() == null ? null : Utils.convertDateToString(silomodulo.getSmohme());
		this.gmt = silomodulo.getSmogmt();
		this.corKeepAlive = silomodulo.getSmocke();
		this.corMedicao = silomodulo.getSmocme();
		this.status = StatusDeviceEnum.mapDescricaoToStatusDevice(silomodulo.getSmosta());
	}

	public SiloModuloDTO volumeSilo(double volumeCheioMm3, double volumeStatusMm3) {
		this.volumeCheio = Utils.formatarVolume(Utils.converterMm3ParaM3(volumeCheioMm3));
		this.volumeStatus = Utils.formatarVolume(Utils.converterMm3ParaM3(volumeStatusMm3));
		return this;
	}

	public SiloModuloDTO(Long codigo) {
		super(codigo);
	}

	public SiloDTO getSilo() {
		return silo;
	}

	public void setSilo(SiloDTO silo) {
		this.silo = silo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Long getTotalSensor() {
		return totalSensor;
	}

	public void setTotalSensor(Long totalSensor) {
		this.totalSensor = totalSensor;
	}

	public String getNumSerie() {
		return numSerie;
	}

	public void setNumSerie(String numSerie) {
		this.numSerie = numSerie;
	}

	public Long getTimeoutKeepAlive() {
		return timeoutKeepAlive;
	}

	public void setTimeoutKeepAlive(Long timeoutKeepAlive) {
		this.timeoutKeepAlive = timeoutKeepAlive;
	}

	public Long getTimeoutMedicao() {
		return timeoutMedicao;
	}

	public void setTimeoutMedicao(Long timeoutMedicao) {
		this.timeoutMedicao = timeoutMedicao;
	}

	public String getUltimaKeepAlive() {
		return ultimaKeepAlive;
	}

	public void setUltimaKeepAlive(String ultimaKeepAlive) {
		this.ultimaKeepAlive = ultimaKeepAlive;
	}

	public String getUltimaMedicao() {
		return ultimaMedicao;
	}

	public void setUltimaMedicao(String ultimaMedicao) {
		this.ultimaMedicao = ultimaMedicao;
	}

	public Integer getGmt() {
		return gmt;
	}

	public void setGmt(Integer gmt) {
		this.gmt = gmt;
	}

	public String getCorKeepAlive() {
		return corKeepAlive;
	}

	public void setCorKeepAlive(String corKeepAlive) {
		this.corKeepAlive = corKeepAlive;
	}

	public String getCorMedicao() {
		return corMedicao;
	}

	public void setCorMedicao(String corMedicao) {
		this.corMedicao = corMedicao;
	}

	public StatusDeviceEnum getStatus() {
		return status;
	}

	public void setStatus(StatusDeviceEnum status) {
		this.status = status;
	}

	public String getVolumeCheio() {
		return volumeCheio;
	}

	public void setVolumeCheio(String volumeCheio) {
		this.volumeCheio = volumeCheio;
	}

	public String getVolumeStatus() {
		return volumeStatus;
	}

	public void setVolumeStatus(String volumeStatus) {
		this.volumeStatus = volumeStatus;
	}

}
