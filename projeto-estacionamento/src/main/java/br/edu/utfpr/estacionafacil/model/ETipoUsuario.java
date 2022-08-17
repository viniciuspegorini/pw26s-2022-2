package br.edu.utfpr.estacionafacil.model;

public enum ETipoUsuario {
	USER(1), ADMIN(2), ALUNO(3), EXTERNO(4);

	private int id;
	
	private ETipoUsuario(int id) {
		this.id = id;
	}
	
	public int getId() {
	    return this.id;
	}	
}
