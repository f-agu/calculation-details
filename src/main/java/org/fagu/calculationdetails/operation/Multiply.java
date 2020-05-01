package org.fagu.calculationdetails.operation;

import java.util.Collections;
import java.util.List;

import org.fagu.calculationdetails.Num;
import org.fagu.calculationdetails.Operation;
import org.fagu.calculationdetails.utils.StringUtils;


public class Multiply extends Operation {

	public Multiply(Num operandLeft, Num operandRight) {
		super(operandLeft, operandRight);
	}

	@Override
	public void showDetails() {
		final int startPadding = 6;
		int globalPadding = startPadding + operandRight.toString().length() + 1;

		int maxIntLength = Math.max(operandLeft.getIntegerLength(), operandRight.getIntegerLength());
		int maxFloatLength = Math.max(operandLeft.getFloatLength(), operandRight.getFloatLength());
		if(maxFloatLength > 0) {
			++maxFloatLength;
		}

		Num result = operandLeft.multiply(operandRight);
		int paddingResult = globalPadding + maxIntLength - result.getIntegerLength();

		System.out.println(StringUtils.repeat(' ', globalPadding)
				+ StringUtils.repeat(' ', maxIntLength - operandLeft.getIntegerLength()) + operandLeft);
		System.out.println(StringUtils.repeat(' ', globalPadding - 2)
				+ "x " + StringUtils.repeat(' ', maxIntLength - operandRight.getIntegerLength()) + operandRight);
		System.out.println(StringUtils.repeat(' ', globalPadding - 2) + "──" + StringUtils.repeat('─', maxIntLength + maxFloatLength));

		int revertPadding = 0;
		List<Num> decompose = operandRight.decompose();
		Collections.reverse(decompose);
		int n = 0;
		for(Num decomposed : decompose) {
			Num rnum = operandLeft.multiply(decomposed);
			int pad = globalPadding + maxIntLength - rnum.getIntegerLength() - revertPadding;

			System.out.println(StringUtils.repeat(' ', startPadding - 1)
					+ (n == 0 ? ' ' : '+') + StringUtils.repeat(' ', pad - startPadding)
					+ rnum);
			if( ! rnum.hasFloat()) {
				++revertPadding;
			}
			++n;
		}

		System.out.println(StringUtils.repeat(' ', startPadding) + "──" + StringUtils.repeat('─', result.toString().length()));
		System.out.println(StringUtils.repeat(' ', paddingResult) + result);
	}

}
