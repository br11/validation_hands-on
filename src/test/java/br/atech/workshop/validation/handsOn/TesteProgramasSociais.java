package br.atech.workshop.validation.handsOn;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

import javax.validation.groups.Default;

import org.junit.Test;

import br.atech.workshop.validation.TestUtil;
import br.atech.workshop.validation.util.DateRangeUtil;

public class TesteProgramasSociais {

	private DateRangeUtil util = new DateRangeUtil();

	@Test
	public void testeBolsaFamiliaRequired() {
		Cidadao cidadao = new Cidadao();

		new TestUtil(Default.class, Programas.BolsaFamilia.class).checkViolations(
				cidadao, "cpf", "nome", "dataDeNascimento", "dependentes");

		cidadao.getDependentes().add(new MenorDeIdade());
		new TestUtil(Default.class, Programas.BolsaFamilia.class).checkViolations(
				cidadao, "cpf", "nome", "dataDeNascimento",
				"dependentes[].nome", "dependentes[].dataDeNascimento");
	}

	@Test
	public void testeBolsaFamiliaIdades() {
		Cidadao cidadao = new Cidadao();
		cidadao.setCpf("11111111111");
		cidadao.setNome("fulano de tal");
		MenorDeIdade dependente = new MenorDeIdade();
		dependente.setNome("beltrano de tal");
		cidadao.getDependentes().add(dependente);

		cidadao.setDataDeNascimento(util.add(new Date(), Calendar.YEAR, -40));
		dependente
				.setDataDeNascimento(util.add(new Date(), Calendar.YEAR, -10));
		new TestUtil(Default.class, Programas.BolsaFamilia.class)
				.checkViolations(cidadao);

		cidadao.setDataDeNascimento(util.add(new Date(), Calendar.YEAR, -17));
		dependente
				.setDataDeNascimento(util.add(new Date(), Calendar.YEAR, -19));
		new TestUtil(Default.class, Programas.BolsaFamilia.class).checkViolations(
				cidadao, "dataDeNascimento", "dependentes[].dataDeNascimento");
	}

	@Test
	public void testeSeguroDesempregoRequired() {
		Cidadao cidadao = new Cidadao();

		new TestUtil(Default.class, Programas.SeguroDesemprego.class)
				.checkViolations(cidadao, "cpf", "nome", "dataDeNascimento",
						"empregos");

		cidadao.getEmpregos().add(new Emprego());
		new TestUtil(Default.class, Programas.SeguroDesemprego.class)
				.checkViolations(cidadao, "cpf", "nome", "dataDeNascimento",
						"empregos[].empresa", "empregos[].admissao",
						"empregos[].demissao");
	}

	@Test
	public void testeSeguroDesemprego() {
		Cidadao cidadao = new Cidadao();
		cidadao.setCpf("11111111111");
		cidadao.setNome("fulano de tal");
		cidadao.setDataDeNascimento(util.add(new Date(), Calendar.YEAR, -40));

		Emprego emprego = new Emprego();
		emprego.setEmpresa("XPTO");
		emprego.setAdmissao(new Date());
		emprego.setDemissao(new Date());
		emprego.setUltimoSalario(new BigDecimal("10000.250"));

		cidadao.getEmpregos().add(emprego);

		new TestUtil(Default.class, Programas.SeguroDesemprego.class)
				.checkViolations(cidadao);
	}
}
