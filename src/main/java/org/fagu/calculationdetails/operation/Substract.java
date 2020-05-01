package org.fagu.calculationdetails.operation;

import org.fagu.calculationdetails.Num;
import org.fagu.calculationdetails.Operation;
import org.fagu.calculationdetails.utils.StringUtils;


public class Substract extends Operation {

	public Substract(Num operandLeft, Num operandRight) {
		super(operandLeft, operandRight);
	}

	@Override
	public void showDetails() {
		final int globalPadding = 6;

		int maxIntLength = Math.max(operandLeft.getIntegerLength(), operandRight.getIntegerLength());
		int maxFloatLength = Math.max(operandLeft.getFloatLength(), operandRight.getFloatLength());
		if(maxFloatLength > 0) {
			++maxFloatLength;
		}

		Num result = operandLeft.substract(operandRight);
		int paddingResult = globalPadding + maxIntLength - result.getIntegerLength();

		System.out.println(StringUtils.repeat(' ', globalPadding)
				+ StringUtils.repeat(' ', maxIntLength - operandLeft.getIntegerLength()) + operandLeft);
		System.out.println(StringUtils.repeat(' ', globalPadding - 2)
				+ "- " + StringUtils.repeat(' ', maxIntLength - operandRight.getIntegerLength()) + operandRight);
		System.out.println(StringUtils.repeat(' ', globalPadding - 2) + "──" + StringUtils.repeat('─', maxIntLength + maxFloatLength));
		System.out.println(StringUtils.repeat(' ', paddingResult) + result);
	}

}
