package org.fagu.calculationdetails.operation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import org.fagu.calculationdetails.Num;
import org.fagu.calculationdetails.Operation;
import org.fagu.calculationdetails.utils.StringUtils;


public class Divide extends Operation {

	private static final int MAX_FLOAT = 5;

	public Divide(Num operandLeft, Num operandRight) {
		super(operandLeft, operandRight);
	}

	@Override
	public void showDetails() {
		final int startPadding = 6;

		System.out.println(StringUtils.repeat(' ', startPadding)
				+ operandLeft + " │ " + operandRight);

		Num result = operandLeft.divide(operandRight);
		Iterator<String> rightIterator = infiniteIterator(displayOnRight(result).iterator(), "");

		String[] zeros = new String[MAX_FLOAT];
		Arrays.fill(zeros, "0");

		final Iterator<String> origCharNumIterator = new JoinIterator<>(operandLeft.toString().chars()
				.filter(c -> c != ',')
				.mapToObj(i -> Character.toString((char)i))
				.iterator(), Arrays.asList(zeros).iterator());

		String prefix = "";
		Num currentNum = operandLeft;
		int padding = 0;
		Iterator<String> charNumIterator = origCharNumIterator;
		FindNum previousFind = null;
		int sepLen = 0;
		for(char cn : result.toString().toCharArray()) {
			if(cn == ',') {
				continue;
			}

			int coef = Integer.parseInt(Character.toString(cn));
			Num quot = operandRight.multiply(new Num(coef));
			FindNum find = findNextNum(prefix, charNumIterator, currentNum, quot);
			if(find == null) {
				break;
			}
			if(previousFind != null) {
				System.out.println(StringUtils.repeat(' ', startPadding + sepLen - find.startNum.length() + padding)
						+ find.startNum
						+ StringUtils.repeat(' ', sepLen - find.startNum.length())
						+ rightIterator.next());
			}
			previousFind = find;
			prefix = find.diffNum.toString();

			System.out.println(StringUtils.repeat(' ', startPadding - 2 + padding)
					+ "- " + find.substractNum
					+ StringUtils.repeat(' ', operandLeft.length() - find.substractNum.length())
					+ rightIterator.next());
			sepLen = Math.max(find.substractNum.length(), find.startNum.length());
			System.out.println(StringUtils.repeat(' ', startPadding - 2 + padding)
					+ "──" + StringUtils.repeat('─', sepLen)
					+ StringUtils.repeat(' ', operandLeft.length() - sepLen)
					+ rightIterator.next());

			if(find.diffNum.getNumber().doubleValue() == 0D) {
				System.out.println(StringUtils.repeat(' ', startPadding + sepLen - find.diffNum.length() + padding)
						+ find.diffNum
						+ StringUtils.repeat(' ', sepLen - find.diffNum.length())
						+ rightIterator.next());
				return;
			}
			++padding;
		}
	}

	// *****************************************

	private List<String> displayOnRight(Num result) {
		List<String> list = new ArrayList<>(3);
		Num roundNum = result.roundFloor(MAX_FLOAT);
		list.add(" ├─" + StringUtils.repeat('─', roundNum.length() + 1));
		list.add(" │ " + roundNum);
		list.add(" │");
		return list;
	}

	private Iterator<String> infiniteIterator(Iterator<String> iterator, String infiniteValue) {
		return new Iterator<String>() {

			@Override
			public String next() {
				return iterator.hasNext() ? iterator.next() : infiniteValue;
			}

			@Override
			public boolean hasNext() {
				return true;
			}
		};
	}

	private static FindNum findNextNum(String prefix, Iterator<String> charNumIterator, Num startNum, Num goalNum) {
		String concat = prefix;
		while(charNumIterator.hasNext()) {
			concat += charNumIterator.next();
			Num longNum = parseFromLong(concat);
			if(longNum.isOverOrEquals(goalNum)) {
				return new FindNum(longNum, goalNum, longNum.substract(goalNum));
			}
		}
		return null;
	}

	private static Num parseFromLong(String value) {
		return new Num(Long.parseLong(value));
	}

	// -------------------------------------------------------------

	private static class FindNum {

		private final Num startNum;

		private final Num substractNum;

		private final Num diffNum;

		private FindNum(Num startNum, Num substractNum, Num diffNum) {
			this.startNum = Objects.requireNonNull(startNum);
			this.substractNum = Objects.requireNonNull(substractNum);
			this.diffNum = Objects.requireNonNull(diffNum);
		}

	}

	// -------------------------------------------------------------

	private static class JoinIterator<T> implements Iterator<T> {

		private final Iterator<T> it1;

		private final Iterator<T> it2;

		private JoinIterator(Iterator<T> it1, Iterator<T> it2) {
			this.it1 = it1;
			this.it2 = it2;
		}

		@Override
		public boolean hasNext() {
			return it1.hasNext() || it2.hasNext();
		}

		@Override
		public T next() {
			return it1.hasNext() ? it1.next() : it2.next();
		}
	}

}
