package com.ebizprise.das.web.controller.suite.forecast;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ebizprise.das.web.controller.minor.ForecastParameter;
import com.ebizprise.das.web.controller.minor.PaceBase;
import com.ebizprise.das.web.controller.minor.PaceStyle;

@Component
public class PaceStyleFactory extends PaceBase {

	private static final Log logger = LogFactory.getLog(PaceStyleFactory.class);

	@Autowired
	protected List<PaceStyle> paceStyles;

	protected PaceStyle paceStyle;

	@Autowired
	protected ForecastParameter forecastParameter;

	@Before
	public void setUp() throws Exception {
		this.paceStyle = getStyle(forecastParameter.style);
	}

	public PaceStyle getStyle(String style) {
		for (PaceStyle paceStyle1 : paceStyles) {
			if (paceStyle1.getStyles().equals(style)) {
				return paceStyle1;
			}
		}
		logger.error("no style: " + style);
		return null;
	}

}
