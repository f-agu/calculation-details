package org.fagu.calculationdetails.operation;

import org.fagu.calculationdetails.Num;
import org.fagu.calculationdetails.Operation;
import org.fagu.calculationdetails.utils.StringUtils;


public class Divide extends Operation {

	public Divide(Num operandLeft, Num operandRight) {
		super(operandLeft, operandRight);
	}

	@Override
	public void showDetails() {
		final int startPadding = 6;

		System.out.println(StringUtils.repeat(' ', startPadding)
				+ operandLeft + " │ " + operandRight);

		// ├─

	}

}
