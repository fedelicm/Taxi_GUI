
package taxi_gui;

//Taxi Simulator Backend

import java.util.Arrays;
import java.util.Random;
public class Taxi {
	
	
	private int numTaxi = 0;
	private static int maxTaxi = 1;
	private static int maxPassenger = 10;
	private int taxiSize = 2;
	private int boxes =8;
	private int[][] blockWeight;
	private int numBlocks = 4;
	private int maxBlocks = numBlocks - taxiSize;
	private static TaxiObject[] taxi = new TaxiObject[maxTaxi];
	private static Log log = new Log();
	private int[][] obstacle = new int[boxes][boxes];
	private static int time = 0;
	
	public Taxi(int maxPassenger, int taxiSize, int boxes, int numBlocks, int maxTaxi){
		this.time = time;
		this.maxPassenger = maxPassenger;
		this.taxiSize = taxiSize;
		this.boxes = boxes;
		this.maxTaxi = maxTaxi;
		obstacle = new int[boxes][boxes];
		blockWeight = new int[boxes][boxes];
		taxi = new TaxiObject[maxTaxi];
		
		for(int count = 0 ; count< boxes ;count++)
			Arrays.fill(blockWeight[count], 1);
	}
	
	
	public void add(int s_x ,int s_y ,int d_x ,int d_y){
		if( getNumPassenger() < maxPassenger ){
			int countBlock = 0;
			int[][][] list = taxi[0].getPassengerList().getPassengers();
			
			for(int count = 0 ; count < list.length ; count++){
				if( list[count][2][0] == s_x
						&& list[count][2][1] == s_y ){
					countBlock++;
				}
			}
			
			for(int count = 0 ; count < numTaxi ; count++){
				list = taxi[count].getPassenger();
				
				for(int count2 = 0 ; count2 < list.length ; count2++){
					if( list[count2][2][0] == s_x
							&& list[count2][2][1] == s_y ){
						countBlock++;
					}
				}
				
			}
			
			if(	countBlock < maxBlocks){
				taxi[0].getPassengerList().addPassenger( s_x, s_y, d_x, d_y);
			}
		}
	}
	
	public void addTaxi(){
		taxi[numTaxi] = new TaxiObject(1,1,1,new PassengerList(maxPassenger),log,numTaxi+1,boxes);
		taxi[numTaxi].setBlockWeight(blockWeight);
		taxi[numTaxi].setObstacle(obstacle);
		numTaxi++;
	}
	
	public int getNumPassenger(){
		int num = taxi[0].getPassengerList().getNumPassenger() ;
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
		int srcx=0;
		int srcy=0;
		int dstx=0;
		int dsty=0;
		
		while(countBlock >= maxBlocks || obstacle[srcy][srcx]==1){
			countBlock=0;
			srcx = random.nextInt(boxes)+1;
			srcy = random.nextInt(boxes)+1;
			
			dstx = random.nextInt(boxes)+1;
			dsty = random.nextInt(boxes)+1;
			
			while( dstx == srcx && dsty == srcy){
				dstx = random.nextInt(boxes)+1;
				dsty = random.nextInt(boxes)+1;
			}
			
			
			int[][][] list = taxi[0].getPassengerList().getPassengers();
			
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
		int num = taxi[0].getPassengerList().getNumPassenger();
		
		for( int count = 0 ; count < numTaxi ; count++)
			num += taxi[count].getNumPassenger();
		
		int[][][] list = new int[ num][3][2];
		
		int[][][] temp = taxi[0].getPassengerList().getPassengers();
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
		taxi[0].getPassengerList().setTime(time);
		for(int count = 0 ; count < numTaxi ; count++){
			taxi[count].move();
			taxi[count].setTime(time);
		}
		log.flush();
	}
	
		public void setTime(int num){
		time = num;
	}
	
		
	public void printPassengerCoords(){
		int[][][] list = getPassengerCoords();
		for(int count = 0 ; count < list.length ; count++)
			System.out.println( list[count][2][0] + " " + list[count][2][1] );
	}
	
	public void setObstacle(int x, int y){
		obstacle[y-1][x-1] = 1;
	}

	public int[][] getOBstacle(){
		return obstacle;
	}
}

