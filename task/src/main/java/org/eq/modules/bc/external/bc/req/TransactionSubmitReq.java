package org.eq.modules.bc.external.bc.req;

import java.util.Arrays;

import io.bumo.model.response.result.data.Signature;

public class TransactionSubmitReq {
	private String transactionBlob;
	private Signature[] signatures;

	public String getTransactionBlob() {
		return this.transactionBlob;
	}

	public void setTransactionBlob(String transactionBlob) {
		this.transactionBlob = transactionBlob;
	}

	public Signature[] getSignatures() {
		return this.signatures;
	}

	public void setSignatures(Signature[] signatures) {
		this.signatures = signatures;
	}

	public void addSignature(Signature signature) {
		if (null == this.signatures) {
			this.signatures = new Signature[1];
		} else {
			this.signatures = (Signature[]) Arrays.copyOf(this.signatures, this.signatures.length + 1);
		}

		this.signatures[this.signatures.length - 1] = signature;
	}
}
