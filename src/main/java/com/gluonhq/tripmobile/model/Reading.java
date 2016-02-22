package com.gluonhq.tripmobile.model;


public class Reading {
	private int id;
	private double hum, temp;
	private long cpm, distLeft, distRight, distForward;
	private int heading;

	protected Reading() {}

	public Reading(int id, double hum, double temp, long cpm, int heading, long distLeft, long distRight, long distForward) {
		this.id = id;
		this.hum = hum;
		this.temp = temp;
		this.cpm = cpm;
		this.heading = heading;
		this.distLeft = distLeft;
		this.distRight = distRight;
		this.distForward = distForward;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getHum() {
		return hum;
	}

	public void setHum(double hum) {
		this.hum = hum;
	}

	public double getTemp() {
		return temp;
	}

	public void setTemp(double temp) {
		this.temp = temp;
	}

	public long getCpm() {
		return cpm;
	}

	public void setCpm(long cpm) {
		this.cpm = cpm;
	}

	public int getHeading() {
		return heading;
	}

	public void setHeading(int heading) {
		this.heading = heading;
	}

	public long getDistLeft() {
		return distLeft;
	}

	public void setDistLeft(long distLeft) {
		this.distLeft = distLeft;
	}

	public long getDistRight() {
		return distRight;
	}

	public void setDistRight(long distRight) {
		this.distRight = distRight;
	}

	public long getDistForward() {
		return distForward;
	}

	public void setDistForward(long distForward) {
		this.distForward = distForward;
	}

	@Override
	public String toString() {
		return "Reading [id=" + id + ", hum=" + hum + ", temp=" + temp + ", radiation cpm=" + cpm +
				", heading=" + heading + ", distance left=" + distLeft + ", distance right=" + distRight +
				", distance forward=" + distForward + "]";
	}
}
