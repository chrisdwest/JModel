/**
 * 
 */
package jModel;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

import repast.simphony.engine.environment.RunEnvironment;
import repast.simphony.random.RandomHelper;

/**
 * @author Chris West
 *
 */
public class Auctioneer {
	private ArrayList<Integer> allDemand = new ArrayList<Integer>();
	private ArrayList<Integer> allSupplyIDs = new ArrayList<Integer>();
	private ArrayList<Integer> allSupplyTypes = new ArrayList<Integer>();
	private HashMap<Integer, Integer> demandMap = new HashMap<Integer, Integer>();
	private HashMap<Integer, Integer> supplyMap = new HashMap<Integer, Integer>();
	private HashMap<Integer, Double> priceList = new HashMap<Integer, Double>();
		
	
		
	public Auctioneer(HashMap<Integer, Double> goodTypePrice){ //initialisation stuff
		this.priceList.putAll(goodTypePrice);		
	}
	
	public void calculateDemand() {
		//System.out.println(JModelBuilder.goodTypes);
		File file = new File("testGoodTypes_" + JModelBuilder.goodTypes + "_Consumers_" + JModelBuilder.totalConsumers + "_RunNo_" + RunEnvironment.getInstance().getParameters().getValueAsString("run_number") + ".txt");
		File file2 = new File("rawGoodTypes_" + JModelBuilder.goodTypes + "_Consumers_" + JModelBuilder.totalConsumers + "_RunNo_" + RunEnvironment.getInstance().getParameters().getValueAsString("run_number") + ".txt");
		int[] demandTypeCount = new int[(Integer) RunEnvironment.getInstance().getParameters().getValue("good_types")];
		int[] supplyTypeCount = new int[(Integer) RunEnvironment.getInstance().getParameters().getValue("good_types")];
		demandMap.clear();
		supplyMap.clear();
		allDemand.clear();
		allSupplyIDs.clear();
		allSupplyTypes.clear();
		int totalSupply = 0;
		int totalDemand = 0;
		
		PrintWriter output = null;
		PrintWriter output2 = null;
		try {
			output = new PrintWriter(new BufferedWriter(new FileWriter(file, true)));
			output2 = new PrintWriter(new BufferedWriter(new FileWriter(file2, true)));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		output.print("Step: ");
		output.println(Controller.getStep());
		output.println("GoodType, Demand, Supply, Price");
		
		for (int i = 0; i<demandTypeCount.length;i++){
			demandTypeCount[i] = 0;
			supplyTypeCount[i] = 0;
		}
		
		for(int i = 0; i<JModelBuilder.consumers.size(); i++){
			allDemand.addAll(JModelBuilder.consumers.get(i).getShoppingBasket());
			allSupplyIDs.addAll(JModelBuilder.consumers.get(i).getGoodID());
		}
		
			
				
		for (int i = 0; i<allSupplyIDs.size(); i++){
			allSupplyTypes.add(JModelBuilder.goods.get(allSupplyIDs.get(i)).getType()); 
		}
		
		for(int i = 0; i<demandTypeCount.length; i++){
			for(int j = 0; j<allDemand.size();j++){
				if (allDemand.get(j) == i){
					demandTypeCount[i]++;
				}
				
			}
		
		demandMap.put(i, demandTypeCount[i]); 
		
		for(int j = 0; j<allSupplyTypes.size(); j++){
			if (allSupplyTypes.get(j) == i){
				supplyTypeCount[i]++;
			}
		}
		
		supplyMap.put(i, supplyTypeCount[i]); 
		
		output.print(i);
		output.print(",");
		output.print(demandMap.get(i));
		output2.print(demandMap.get(i));
		output.print(",");
		output2.print(",");
		output.print(supplyMap.get(i));
		output2.print(supplyMap.get(i));
		output.print(",");
		output2.print(",");
		output.println(priceList.get(i));
		output2.println(priceList.get(i));
				
		}
		
		for (int i=0;i<supplyMap.size();i++){
			totalSupply = totalSupply + supplyMap.get(i);
			totalDemand = totalDemand + demandMap.get(i);
		}
		//System.out.println(totalSupply - totalDemand);
		
		output.println();
		
		output.close();
		output2.close();
		
	}
	
	public void setNewPrices(){
		int[] demandTypeCount = new int[(Integer) RunEnvironment.getInstance().getParameters().getValue("good_types")];
		//double a=0;
		double[] b = new double[demandTypeCount.length];
		double c = 0;
		//double max = 0;
		//System.out.println(JModelBuilder.goodTypePrice);
		//priceList.clear();
		double sumPrice = 0;
		
		//System.out.println(JModelBuilder.goodTypePrice);
		
		for (int i=0; i<demandTypeCount.length;i++){
			
			/* old method
			b[i] = (((double)demandMap.get(i) - (double)supplyMap.get(i)) / (double)supplyMap.get(i));
			*/
			
			//new method
			b[i] = ((double)demandMap.get(i) / (double)supplyMap.get(i));
			
			if (Double.isNaN(b[i])){
				c = 0.0;
			}
			else{
			//c = (-0.5)*Math.exp(Math.log(0.5)*b[i])+1.25;
			c = Math.min((0.1*b[i])+0.9,1.1);
			}
			//System.out.println(i);
			//System.out.println(JModelBuilder.goodTypePrice);						
			JModelBuilder.goodTypePrice.put(i, (JModelBuilder.goodTypePrice.get(i)*c));	
			/*not needed for new method
			if(b[i]>max){
				max = b[i];
			}
			*/
		}
		
		for (int i=0; i<demandTypeCount.length; i++){
			sumPrice = sumPrice + JModelBuilder.goodTypePrice.get(i);
		}
		
		for (int i=0; i<demandTypeCount.length; i++){
			JModelBuilder.goodTypePrice.put(i, (JModelBuilder.goodTypePrice.get(i)/sumPrice));
		}
		
		/*OLD METHOD
		for (int i=0; i<demandTypeCount.length;i++){
			
			
			if(b[i]>0){
				b[i]=b[i]/max;
			}
		
			double c = Math.abs(b[i]);
			a = a+c;
			
		}
		
		for (int i=0; i<demandTypeCount.length;i++){
						
			JModelBuilder.goodTypePrice.put(i, Math.max(JModelBuilder.goodTypePrice.get(i) + ((b[i]/a)/* JModelBuilder.goodTypePrice.get(i)*//*),0.00001) );  
			
		}
	    */
				
		for (int i=0; i<JModelBuilder.goodAmount; i++){
			double price = JModelBuilder.goodTypePrice.get(JModelBuilder.goods.get(i).getType());
			JModelBuilder.goods.get(i).setPrice(price);
			priceList.put(JModelBuilder.goods.get(i).getType(), price);
		}
				
	}
	
	
	public double getPrice(int type){
		//System.out.println(type);
		//System.out.println(priceList.get(type));
		return priceList.get(type);
	}
	
	public void setPriceList(int type, double price){
		priceList.put(type, price);
	}
	
	public HashMap<Integer,Integer> getSupplyMap(){
		return supplyMap;
	}
	
	
}


