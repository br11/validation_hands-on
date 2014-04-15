package br.atech.workshop.validation.handsOn;

import java.util.Date;

import br.atech.workshop.validation.date.DateRange;

public class FlightPlan {

	private String indicative;
	
	private String adep;
	
	private String ades;
	
	private String alternativeAdes;

	@DateRange(max = DateRange.Ranges.ThisYear, maxGap = -18, groups = Perfis.BolsaFamilia.class)
	private Date eobt;
	
}
