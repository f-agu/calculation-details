package org.fagu.calculationdetails;

import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.fagu.calculationdetails.operation.Add;
import org.fagu.calculationdetails.operation.Divide;
import org.fagu.calculationdetails.operation.Multiply;
import org.fagu.calculationdetails.operation.Substract;


public abstract class Operation {

	protected final Num operandLeft;

	protected final Num operandRight;

	public Operation(Num operandLeft, Num operandRight) {
		this.operandLeft = operandLeft;
		this.operandRight = operandRight;
	}

	public static Operation parse(String text) throws ParseException {
		final String GROUP_FLOAT_PATTERN = "([-]?[0-9]+[\\.,]?[0-9]*)";
		Pattern pattern = Pattern.compile(GROUP_FLOAT_PATTERN + "\\s*([+\\-\\*/])\\s*" + GROUP_FLOAT_PATTERN);
		Matcher matcher = pattern.matcher(text);
		if( ! matcher.matches()) {
			throw new ParseException(text, 0);
		}
		Number n1 = toNumber(matcher.group(1));
		Operator operator = toOperator(matcher.group(2));
		Number n2 = toNumber(matcher.group(3));

		switch(operator) {
			case ADD:
				return new Add(new Num(n1), new Num(n2));
			case SUBSTRACT:
				return new Substract(new Num(n1), new Num(n2));
			case MULTIPLY:
				return new Multiply(new Num(n1), new Num(n2));
			case DIVIDE:
				return new Divide(new Num(n1), new Num(n2));

			default:
				break;
		}
		throw new ParseException(text, 0);
	}

	public abstract void showDetails();

	// *******************************************************

	private static Number toNumber(String text) {
		String t = text.replace(',', '.');
		if(t.contains(".")) {
			return Float.parseFloat(t);
		}
		return Long.parseLong(t);
	}

	private static Operator toOperator(String text) {
		if("+".equals(text)) {
			return Operator.ADD;
		}
		if("-".equals(text)) {
			return Operator.SUBSTRACT;
		}
		if("*".equals(text)) {
			return Operator.MULTIPLY;
		}
		if("/".equals(text)) {
			return Operator.DIVIDE;
		}
		throw new IllegalArgumentException("Undefined operator: " + text);
	}

	public static void main(String[] args) throws ParseException {
		// Pattern pattern = Pattern.compile("([-]?[0-9]+\\.?[0-9]*)\\s*[+\\-\\*/]");
		// Matcher matcher = pattern.matcher("1+");
		// if(matcher.matches()) {
		// System.out.println("OK");
		// System.out.println(matcher.group(1));
		// } else {
		// System.out.println("Failed");
		// }
		parse("9 / 3").showDetails();
		// parse("999 * 765").showDetails();
	}
}
