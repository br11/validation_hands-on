package br.atech.workshop.validation.handsOn;

import java.util.Date;

import br.atech.workshop.validation.date.DateRange;
import br.atech.workshop.validation.required.Required;
import br.atech.workshop.validation.util.IdentityUtil;

public class MenorDeIdade {

	@Required
	private String nome;

	// TODO Exercício 3.3
	@Required(groups = Perfis.BolsaFamilia.class)
	private Date dataDeNascimento;
	
	/**
	 * @return the nome
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * @param nome
	 *            the nome to set
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}

	/**
	 * @return the dataDeNascimento
	 */
	public Date getDataDeNascimento() {
		return dataDeNascimento;
	}

	/**
	 * @param dataDeNascimento
	 *            the dataDeNascimento to set
	 */
	public void setDataDeNascimento(Date dataDeNascimento) {
		this.dataDeNascimento = dataDeNascimento;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return new IdentityUtil().hashCode(this, "nome");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		return new IdentityUtil().equals(this, obj, "nome");
	}

}
