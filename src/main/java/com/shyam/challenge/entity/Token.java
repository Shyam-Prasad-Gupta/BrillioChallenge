package com.shyam.challenge.entity;

import java.io.Serializable;

public class Token implements Serializable {

	private static final long serialVersionUID = 1L;

	private long tokenId;
	private boolean isPremium;

	public Token() {
	}

	public Token(long tokenId, boolean isPremium) {
		this.tokenId = tokenId;
		this.isPremium = isPremium;
	}

	public long getTokenId() {
		return tokenId;
	}

	public void setTokenId(long tokenId) {
		this.tokenId = tokenId;
	}

	public boolean isPremium() {
		return isPremium;
	}

	public void setPremium(boolean isPremium) {
		this.isPremium = isPremium;
	}

	public String toString() {
		return "[TokenId: " + this.tokenId + ", TokenValue: " + this.isPremium + "]";
	}

}
