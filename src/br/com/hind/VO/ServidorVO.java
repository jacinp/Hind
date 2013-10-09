package br.com.hind.VO;

public class ServidorVO {

	private Integer id;
	private String nomeServidor;
	private String nrIp;
	private String descricao;
	private String email;

	public ServidorVO() {

	}

	public ServidorVO(int id, String nmServidor, String nrIp, String descricao, String email) {
		this.id = id;
		this.nomeServidor = nmServidor;
		this.nrIp = nrIp;
		this.descricao = descricao;
		this.email = email;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNmServidor() {
		return nomeServidor;
	}

	public void setNmServidor(String nmServidor) {
		this.nomeServidor = nmServidor;
	}

	public String getIp() {
		return nrIp;
	}

	public void setIp(String ip) {
		this.nrIp = ip;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}