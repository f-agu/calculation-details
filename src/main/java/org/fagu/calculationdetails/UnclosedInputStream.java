package org.fagu.calculationdetails;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;


public class UnclosedInputStream extends FilterInputStream {

	public UnclosedInputStream(InputStream in) {
		super(in);
	}

	@Override
	public void close() throws IOException {
		// DO NOTHING
	}

	public void closeForce() throws IOException {
		super.close();
	}

}
