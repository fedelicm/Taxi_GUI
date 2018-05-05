
package taxi_gui;

//Taxi Simulator Backend

import java.util.Arrays;
import java.lang.Math;
import java.util.Random;
public class Taxi {
	
	
	private int numTaxi = 0;
	private static int maxTaxi = 1;
	private static int maxPassenger = 10;
	private int taxiSize = 2;
	private int boxes =8;
	private int[][] blockWeight;
	private int maxBlocks = 4;
	private static PassengerList passengerList = new PassengerList(maxPassenger);
	private static TaxiObject[] taxi = new TaxiObject[maxTaxi];
	

	public Taxi(int maxPassenger, int taxiSize, int boxes, int maxTaxi){
		this.maxPassenger = maxPassenger;
		this.passengerList = new PassengerList(maxPassenger);
		this.taxiSize = taxiSize;
		this.boxes = boxes;
		blockWeight = new int[boxes+1][boxes+1];;

		taxi = new TaxiObject[maxTaxi];
		for(int count = 0 ; count < maxTaxi ; count++)
			taxi[count] = new TaxiObject(1,0,0,passengerList);
		
		for(int count = 0 ; count< boxes ;count++)
			Arrays.fill(blockWeight[count], 1);
	}
	
	
	public void add(int s_x ,int s_y ,int d_x ,int d_y){
		passengerList.addPassenger(s_x, s_y, d_x, d_y);
	}
	
	public void addTaxi(){
		taxi[numTaxi] = new TaxiObject(1,1,1,passengerList);
		numTaxi++;
	}
	
	public int getNumPassenger(){
		int num = passengerList.getNumPassenger() ;
		for( int count = 0 ; count < numTaxi ; count++)
			num += taxi[count].getNumPassenger();
		
		return num;
	}
	
	public int[][] getBlockWeight(){
		return blockWeight;
	}
	
	public void setBlockWeight(int x , int y , int weight){
		blockWeight[y][x] = weight;
	}
	
	public void addRandomPassenger(){
		Random random = new Random();

		int countBlock = maxBlocks;
		
		while(countBlock >= maxBlocks){
			countBlock=0;
			int srcx = random.nextInt(boxes)+1;
			int srcy = random.nextInt(boxes)+1;
			
			int dstx = random.nextInt(boxes)+1;
			int dsty = random.nextInt(boxes)+1;
			
			while( dstx == srcx && dsty == srcy){
				dstx = random.nextInt(boxes)+1;
				dsty = random.nextInt(boxes)+1;
			}
			
			
			int[][][] list = passengerList.getPassengers();
			
			for(int count = 0 ; count < list.length ; count++){
				if( list[count][2][0] == srcx
						&& list[count][2][1] == srcy ){
					countBlock++;
				}
			}
			
			for(int count = 0 ; count < numTaxi ; count++){
				list = taxi[count].getPassenger();
				
				for(int count2 = 0 ; count2 < list.length ; count2++){
					if( list[count2][2][0] == srcx
							&& list[count2][2][1] == srcy ){
						countBlock++;
					}
				}
				
			}
			
			if(	countBlock < maxBlocks){
				add( srcx,srcy,dstx,dsty);
				break;
			}
			
		}
	
	}
	
	public int[][][] getPassengerCoords(){
		int num = passengerList.getNumPassenger() ;
		for( int count = 0 ; count < numTaxi ; count++)
			num += taxi[count].getNumPassenger();
		
		int[][][] list = new int[ num][3][2];
		
		int[][][] temp = passengerList.getPassengers();
		int count = 0;
		for( int count2 = 0; count2 < temp.length ; count2++,count++)
			list[count] = temp[count2];
		
		for(int count3 = 0 ; count3  < numTaxi ; count3++){
			temp = taxi[count3].getPassenger();
			for( int count2 = 0; count2 < temp.length ; count2++,count++)
				list[count] = temp[count2];
		}
		
		return list;
	}
	
	public int[][] getTaxiCoords(){
		int[][] list = new int[numTaxi][2];
		
		
		for(int count = 0 ; count < numTaxi ; count++)
			list[count] = taxi[count].getCoords();
	
		return list;
	}
	
	public void move(){
		for(int count = 0 ; count < numTaxi ; count++)
			taxi[count].move();
	}
}
