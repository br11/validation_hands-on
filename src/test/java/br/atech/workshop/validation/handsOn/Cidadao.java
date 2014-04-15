package br.atech.workshop.validation.handsOn;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.validation.Valid;

import br.atech.workshop.validation.date.DateRange;
import br.atech.workshop.validation.required.Required;
import br.atech.workshop.validation.util.IdentityUtil;

/**
 * 
 * @author marcio
 * 
 */
public class Cidadao {

	private long id;

	@Required
	private String cpf;

	@Required
	private String nome;

	@Required(groups = { Perfis.BolsaFamilia.class,
			Perfis.SeguroDesemprego.class })
	@DateRange(max = DateRange.Ranges.ThisYear, maxGap = -18, groups = Perfis.BolsaFamilia.class)
	private Date dataDeNascimento;

	@Valid
	@Required(groups = Perfis.SeguroDesemprego.class)
	private Set<Emprego> empregos = new LinkedHashSet<>();

	@Valid
	@Required(groups = Perfis.BolsaFamilia.class)
	private Set<MenorDeIdade> dependentes = new LinkedHashSet<>();

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return the cpf
	 */
	public String getCpf() {
		return cpf;
	}

	/**
	 * @param cpf
	 *            the cpf to set
	 */
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

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

	/**
	 * @return the dependentes
	 */
	public Set<MenorDeIdade> getDependentes() {
		return dependentes;
	}

	/**
	 * @param dependentes
	 *            the dependentes to set
	 */
	public void setDependentes(Set<MenorDeIdade> dependentes) {
		this.dependentes = dependentes;
	}

	/**
	 * @return the empregos
	 */
	public Set<Emprego> getEmpregos() {
		return empregos;
	}

	/**
	 * @param empregos
	 *            the empregos to set
	 */
	public void setEmpregos(Set<Emprego> empregos) {
		this.empregos = empregos;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return new IdentityUtil().hashCode(this, "cpf");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		return new IdentityUtil().equals(this, obj, "cpf");
	}
}
