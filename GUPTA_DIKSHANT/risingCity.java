/*
	 * @author: Dikshant Gupta
	 * 
	 */
import java.io.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
//Main class 
public class risingCity  {

	public static MinHeap min_heap = new MinHeap();
	public static RBT rbt = new RBT();

	
	public static Building cur_building = null;
	static int current_building_count = 0;   // local timer which keeps the day count so as to check the selected building is worked on until complete or for 5 days  
	
	

	
	//main function
	public static void main(String[] args) throws Exception {
		//This will create a output file where the output result will get displayed
		PrintStream of = new PrintStream(new File("output_file.txt"));
		System.setOut(of);
		
		FileReader fr = null;
		try {
			// Input file as an argument is given
			File file = new File(args[0]);
			fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			PrintWriter pw= new PrintWriter("output_file.txt");
			//pw.print("");
			pw.close();
			construction(br);
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			fr.close();
		}
	}


	//construction_function
	/* It will start the construction process of a building and process the input from the input file 
	 * to retrieve the timer and will start the global timer and perform operations on the Building object by selecting a 
	 * particular Building object based on Executed time and Total time of a Building  */
	private static void construction(BufferedReader bfr) throws Exception {
		
		String line = null;
		ArrayList<String> line_timer = new ArrayList<String>();
		while((line = bfr.readLine()) != null) {
			
			line_timer.add(line);
					}
		for (int i = 0;; i++) {
			if(min_heap.isEmpty() && rbt.root == rbt.nil && cur_building == null && line_timer.size() == 0) {            
				break;
				// This is the condition for stopping the global timer when all the Buildings have been constructed and there are no more line of Input to read.
			}
			
			// This will extract the minimum element from the Heap
			if(cur_building==null) {
				cur_building=min_heap.extractMin();
				current_building_count = 0;
			}
			
			/*This will increase the count of number of days of a particular building which is under construction  
			*/
			if(cur_building != null) {
				cur_building.setExecutedTime(cur_building.getExecutedTime()+1);
				current_building_count++;
			}
			if(line_timer.size() > 0 && get_time(line_timer.get(0)) == i) {
				processInput(line_timer.remove(0));
			}
			
			// This will print out the building whose construction is completed.
			if(cur_building != null && cur_building.getExecutedTime()==cur_building.getTotalTime())
			{	
				System.out.println("(" + cur_building.getBuildingId() + ","
					+ (i)+")");
				rbt.removeBuilding(cur_building.getBuildingId());
				cur_building=null;
				current_building_count=0;				
			}
			
			// Check if the current Building is worked for 5 days and then put it back into the heap
			else if(current_building_count==5)
			{
				min_heap.insert(cur_building);
				cur_building=null;
				current_building_count = 0;				
			}

		}
					

	}

	
	/* This function will return the time of a particular operation of either
	Inserting or Printing of a Building which is present in the Input file */
	private static int get_time(String line) throws Exception {
		String[] str;
		int f = -1;
		line = line.trim();
		if (line.contains(":")) {
			str = line.split(":");
			f = Integer.parseInt(str[0]);
		} else {
			if (line.length() > 1)
				throw new Exception("Invalid Input:" + line);
		}
		return f;
	}
	
	/*This will split the Instruction Set into two parts, first
	 *  contains the Instruction i.e. Insert or PrintBuilding and 
	 *  second will contain the Building_Id and Total_time
	 *  i.e. It is Splitted into Insert and 5,10)
	*/
	private static void process_line(String line) throws Exception {
		String inst_set = null;
		String[] str;
		str = line.split("\\(");
		inst_set = str[0];
		exe(inst_set, str[1]);
	}
	/* Process the Input line and it will split the timer and the Instruction command.
	 * i.e. E.g. 0: Insert(5,10) is splitted into 0 and Insert(5,10) 
	 */
	private static void processInput(String line) throws Exception {
		String[] str;
		
		line = line.trim();
		str = line.split(":");
		process_line(str[1]);

	}

	/*
	This is the main method that performs the necessary logical operations like insertion,
	deletion to Red black tree and and Minimum Heap. Also, perform operation based on the
	instruction set provided as input.
	*/
	
	private static void exe(String inst_set, String str) throws Exception {
		
		int building_iD = 0;
		int total_time = 0;
		
		String[] s;
		
		str = str.trim();
		inst_set = inst_set.trim();
		if (str.charAt(str.length() - 1) == ')') {
			str = (String) str.substring(0, str.length() - 1);
		} else {
			throw new Exception("Something went wrong :(");
		}

		s = str.split("\\,");
		//comparsion Starts --> with instruction processed
		if (inst_set.equals("Insert")) {
			
			building_iD = Integer.parseInt(s[0]);
			total_time = Integer.parseInt(s[1]);
			Building j = create_building(building_iD, 0, total_time);
			

		} 

		else if (inst_set.equals("PrintBuilding")) {

			int get_rBid = -1;
			boolean isPrintRunningBuilding = false;
			if (cur_building != null) {
				get_rBid = cur_building.getBuildingId();
				isPrintRunningBuilding = true;
			}
			if (s.length > 1) {
				boolean isFound = false;
				int start_id = Integer.parseInt(s[0]);
				int end_id = Integer.parseInt(s[1]);
				List<Building> res = new ArrayList<>();
				/*
				 *It will output all (buildingNum,executed_time,total_time) triplets separated by 
				 * commas in a single line including buildingNum1 and buildingNum2; if they exist.
				 *  If there is no building in the specified range, the output is (0,0,0). 
				 * 
				 */
				rbt.findBuildingsBetweenRange(rbt.root, start_id, end_id, res);
				for (int i = 0; i < res.size(); i++) {
					Building sBuilding = res.get(i);
					if (isFound) {
						System.out.print(",");
					}
					System.out.print("(" + sBuilding.getBuildingId() + ","
							+ sBuilding.getExecutedTime() + ","
							+ sBuilding.getTotalTime() + ")");
					isFound = true;
					}
				if (!isFound) {
					System.out.print("(0,0,0)");
				}
				
			} 
			/*
			 * It will output the (buildingNum,executed_time,total_time) triplet if the buildingNum exists. If not print (0,0,0).
			 */
			
			
			else {
				building_iD = Integer.parseInt(s[0]);
				Building sBuilding = find_in_RBT(building_iD);
				if (sBuilding != null
						&& sBuilding.getExecutedTime() < sBuilding.getTotalTime()) {
					System.out.print("(" + sBuilding.getBuildingId() + ","
							+ sBuilding.getExecutedTime() + ","
							+ sBuilding.getTotalTime() + ")");
				} else {
					System.out.print("(0,0,0)");
				}
				if (get_rBid == building_iD) {
					isPrintRunningBuilding = false;
				}
			}
			System.out.println();

		} 
		 else {
			throw new Exception("Something went wrong :(");
		}
		//end of comparison
		
	}
	
	/*
	 * This will create a new Building object and will Insert that object into both Min-Heap
	 * and Red Black Tree
	*/
	private static Building create_building(int building_iD, int executedTime, int totalTime) {
		Building building = new Building(building_iD, executedTime, totalTime);
		min_heap.insert(building);
		rbt.insertBuilding(building);
		return building;
	}
	
	
	
	// search a particular Building in a Red black tree
	private static Building find_in_RBT(int building_iD) {
		return rbt.findBuilding(building_iD);
	}
	
	
}//end of class








