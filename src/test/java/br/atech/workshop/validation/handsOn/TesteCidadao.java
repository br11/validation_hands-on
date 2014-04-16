package br.atech.workshop.validation.handsOn;

import java.util.Calendar;
import java.util.Date;

import org.junit.Test;

import br.atech.workshop.validation.TestUtil;
import br.atech.workshop.validation.util.DateRangeUtil;

public class TesteCidadao {

	private DateRangeUtil util = new DateRangeUtil();

	@Test
	public void testRequired() {
		Cidadao bean = new Cidadao();

		new TestUtil().checkViolations(bean, "cpf", "nome");
		
		bean.setCpf("");
		bean.setNome("       ");
		new TestUtil().checkViolations(bean, "cpf", "nome");
	}

	@Test
	public void testSeguroDesemprego() {
		Cidadao bean = new Cidadao();
		
		new TestUtil(Programas.SeguroDesemprego.class).checkViolations(bean,
				"dataDeNascimento", "empregos");
	}

	@Test
	public void testBolsaFamiliaRequired() {
		Cidadao bean = new Cidadao();
		
		new TestUtil(Programas.BolsaFamilia.class).checkViolations(bean,
				"dataDeNascimento", "dependentes");
	}

	@Test
	public void testBolsaFamiliaMaiorIdade() {
		Cidadao bean = new Cidadao();
		bean.setCpf("11111111111");
		bean.setNome("fulano de tal");

		bean.setDataDeNascimento(util.add(new Date(), Calendar.YEAR, -17));
		new TestUtil(Programas.BolsaFamilia.class).checkViolations(bean,
				"dataDeNascimento", "dependentes");

		bean.setDataDeNascimento(util.add(new Date(), Calendar.YEAR, -18));
		new TestUtil(Programas.BolsaFamilia.class).checkViolations(bean,
				"dependentes");
	}

}
