package com.github.eeval;

import org.junit.Assert;
import org.junit.Test;

public class EEValTest {
	private static final double DELTA = 0.00001;

	@Test(expected = IllegalArgumentException.class)
	public void shouldThrowIllegalArgumentExceptionIfExpressionIsNull() {
		EEVal.evalToDouble(null);
	}

	@Test
	public void testScenario0() {
		String expression = "3-2-1";

		Double expected = 0.0;

		Assert.assertEquals(expected, EEVal.evalToDouble(expression), DELTA);
	}

	@Test
	public void testScenario1() {
		String expression = "(12-1)*2";

		Double expected = 22.0;

		Assert.assertEquals(expected, EEVal.evalToDouble(expression));
	}

	@Test
	public void testScenario2() {
		String expression = "11^2+2.5*4-(2+3)";

		Double expected = 126.0;

		Assert.assertEquals(expected, EEVal.evalToDouble(expression));
	}

	@Test
	public void testScenario3() {
		String expression = "2-(4*2-1)/5";

		Double expected = 0.6;

		Assert.assertEquals(expected, EEVal.evalToDouble(expression), DELTA);
	}

	@Test
	public void testScenario4() {
		String expression = "20^3-(4.757*2.2)^3-1";

		Double expected = 6852.78128;

		Assert.assertEquals(expected, EEVal.evalToDouble(expression), DELTA);
	}

	@Test
	public void testScenario5Power() {
		String expression = "(2.5^4.5^2^1)/2^5^2";

		Double expected = 3.40828014055;

		Assert.assertEquals(expected, EEVal.evalToDouble(expression), DELTA);
	}

}
