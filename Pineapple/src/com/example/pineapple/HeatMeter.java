package com.example.pineapple;

public class HeatMeter {
	private final double coolingRate;
	private double heat;
	private boolean coolingDown;
	
	public HeatMeter(double coolingRate){
		heat = 0;
		coolingDown = false;
		this.coolingRate = coolingRate;
	}
	
	public void addHeat(double amount){
		heat += amount;
		if(heat >= 1){
			heat = 1;
			coolingDown = true;
		}
	}
	
	public void coolDown(){
		if(coolingDown){
			heat -= coolingRate;
		} else {
			heat -= 2*coolingRate;
		}
		if(heat <= 0){
			coolingDown = false;
			heat = 0;
		}
	}

	public boolean isCoolingDown() {
		return coolingDown;
	}
	
	public double getHeat(){
		return heat;
	}

}
