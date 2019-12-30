/*
	 * @author: Dikshant Gupta
	 * 
	 */

public class Building implements Comparable<Building> {
	int buildingId;
	int totalTime;
	int exTime;
// Create a Constructor of a Building Class
	public Building(int buildingId, int exTime, int totalTime) {
		this.buildingId = buildingId;
		this.exTime = exTime;
		this.totalTime = totalTime;
		
	}
	//GET Methods
	public int getBuildingId() {
		return buildingId;
	}
	public int getExecutedTime() {
		return exTime;
	}
	public int getTotalTime() {
		return totalTime;
	}
	//SET Methods
	public void setTotalTime(int totalTime) {
		this.totalTime = totalTime;
	}
	public void setExecutedTime(int exTime) {
		this.exTime = exTime;
	}
	public void setBuildingId(int buildingId) {
		this.buildingId = buildingId;
	}

	@Override
	public String toString() {
		return "Building [buildingId=" + buildingId + ", exTime=" + exTime
				+ ", totalTime=" + totalTime + "]";
	}
	//comparsion function
	public int compareTo(Building j) {
		if(this.exTime - j.exTime == 0) {
			return this.buildingId - j.buildingId;
		}
		return this.exTime - j.exTime;
	}

	

}