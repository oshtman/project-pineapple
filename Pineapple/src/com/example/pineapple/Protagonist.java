		package com.example.pineapple;
		
		public class Protagonist {
			
			private double xPos;
			private double yPos;
			private double height;
			private double length;
		//	private bitmap portrait;
			private double xVel;
			private double yVel;
			private double xAcc;
			private double yAcc;
			private int health;
		
			// CONSTRUCTOR
			public Protagonist(double i,double j){
				this.setXPos(i);
				this.setYPos(j);
				health = 100;
			}
			
			public Protagonist(){
				this.setXPos(75);
				this.setYPos(25);
				health = 100;
			}
			
			// GET AND SET
			public double getXPos() {
				return xPos;
			}
			
			public double getYPos() {
				return yPos;
			}
			
			public double getXVel() {
				return xVel;
			}
			
			public double getYVel() {
				return yVel;
			}
			
			public double getXAcc() {
				return xAcc;
			}
			
			public double getYAcc() {
				return yAcc;
			}
			
			public void setXPos(double n) {
				xPos = n;
			}
			
			public void setYPos(double n) {
				yPos = n;
			}
			
			public void setXVel(double n) {
				xVel = n;
			}
			
			public void setYVel(double n) {
				yVel = n;
			}
			public void setXAcc(double n) {
				xAcc = n;
			}
			
			public void setYAcc(double n) {
				yAcc = n;
			}
			
			public void setAim(int angle) {
				
			}
			public double getAim() {
				return 8;
			}
			
			// ACTIONS
			public void aim(){
				
			}
			public void fire(){
				
			}
			public int reduceHealth(int n) {
				health = n-1;
				return health;
			}
		}
