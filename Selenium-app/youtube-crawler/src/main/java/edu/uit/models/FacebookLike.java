package edu.uit.models;

public class FacebookLike {
	private long numberLike;
	private long numberLove;
	private long numberSmile;
	private long numberSurprise;
	private long numberSad;
	private long numberAngry;
	
	public long getNumberLike() {
		return numberLike;
	}
	public void setNumberLike(long numberLike) {
		this.numberLike = numberLike;
	}
	public long getNumberLove() {
		return numberLove;
	}
	public void setNumberLove(long numberLove) {
		this.numberLove = numberLove;
	}
	public long getNumberSmile() {
		return numberSmile;
	}
	public void setNumberSmile(long numberSmile) {
		this.numberSmile = numberSmile;
	}
	public long getNumberSurprise() {
		return numberSurprise;
	}
	public void setNumberSurprise(long numberSurprise) {
		this.numberSurprise = numberSurprise;
	}
	public long getNumberSad() {
		return numberSad;
	}
	public void setNumberSad(long numberSad) {
		this.numberSad = numberSad;
	}
	public long getNumberAngry() {
		return numberAngry;
	}
	public void setNumberAngry(long numberAngry) {
		this.numberAngry = numberAngry;
	}
	public void setDefault() {
		numberLike = 0;
		numberLove = 0;
		numberSmile = 0;
		numberSurprise = 0;
		numberSad = 0;
		numberAngry = 0;
	}
}
