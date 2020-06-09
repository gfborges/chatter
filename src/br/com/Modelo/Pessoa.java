package br.com.Modelo;

public class Pessoa {
	private static int ID_COUNTER = 0;
	private String nome, uuid;
	private int id;
	
	Pessoa(String nome){
		this.nome = nome;
		this.id = ID_COUNTER++;
		this.uuid = UUID(id);
	}
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getUuid() {
		return uuid;
	}

	public int getId() {
		return id;
	}

	static String UUID(int id) {
		for(int i=0; i<5;i++) {
			id = id * 37;
		}
		id = id%10000;
		String uuid = String.format("%04", id);
		return uuid;
	}
	
	public String toString() {
		return String.format("%s#%s", nome, uuid);
	}
	
	public boolean equals(Object obj) {
		if(this == obj) {
			return true;
		}
		if(!(obj instanceof Pessoa) || obj == null) {
			return false;
		}
		Pessoa p = (Pessoa) obj;
		return toString().equals(p.toString());
		
	}
}
