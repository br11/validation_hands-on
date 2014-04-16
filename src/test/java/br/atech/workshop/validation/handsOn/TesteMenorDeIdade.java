package br.atech.workshop.validation.handsOn;

import java.util.Calendar;
import java.util.Date;

import org.junit.Test;

import br.atech.workshop.validation.TestUtil;
import br.atech.workshop.validation.util.DateRangeUtil;

public class TesteMenorDeIdade {

	private DateRangeUtil util = new DateRangeUtil();

	@Test
	public void testRequired() {
		MenorDeIdade bean = new MenorDeIdade();

		new TestUtil().checkViolations(bean, "nome");

		bean.setNome("     ");
		new TestUtil().checkViolations(bean, "nome");
	}

	@Test
	public void testBolsaFamiliaRequired() {
		MenorDeIdade bean = new MenorDeIdade();

		new TestUtil(Programas.BolsaFamilia.class).checkViolations(bean,
				"dataDeNascimento");
	}

	// TODO Exercício 3.4
	public void testBolsaFamiliaMenorIdade() {
		MenorDeIdade bean = new MenorDeIdade();
		bean.setNome("fulano de tal");

		/* idades em meses */
		int recemNascido = 0;
		int dezoito = (18 * 12);
		int idadeAdultaQualquer = (21 * 12); // 21 anos

		/* de recem nascido à 17 anos de idade é válido */
		for (int idade = recemNascido; idade < dezoito; idade++) {
			bean.setDataDeNascimento(util.calculaDataDeNascimento((char) idade));
			new TestUtil(Programas.BolsaFamilia.class).checkViolations(bean);
		}

		/*
		 * No dia em que completa dezoito anos é válido.
		 */
		Date dezoitoExatos = util.calculaDataDeNascimento((char) dezoito);
		bean.setDataDeNascimento(dezoitoExatos);
		new TestUtil(Programas.BolsaFamilia.class).checkViolations(bean);

		/*
		 * dezoito anos e uns dias - requisito diz ser valido até o final mês em
		 * que completa a maioridade.
		 */
		Date inicioDoMes = util.set(dezoitoExatos, Calendar.DAY_OF_MONTH, 1);
		bean.setDataDeNascimento(inicioDoMes);
		new TestUtil(Programas.BolsaFamilia.class).checkViolations(bean);

		/* prestes a completar 18 anos é válido */
		Date fimDoMes = util.set(dezoitoExatos, Calendar.DAY_OF_MONTH, 28);
		bean.setDataDeNascimento(fimDoMes);
		new TestUtil(Programas.BolsaFamilia.class).checkViolations(bean);

		/* 18 anos 1 um mês ou mais não é válido */
		for (int idade = idadeAdultaQualquer; idade > dezoito; idade--) {
			bean.setDataDeNascimento(util.calculaDataDeNascimento((char) idade));
			new TestUtil(Programas.BolsaFamilia.class).checkViolations(bean,
					"dataDeNascimento");
		}
	}
}
