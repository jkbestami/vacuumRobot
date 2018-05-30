public class Tile {

    boolean isFurniture;
    boolean isDirt;
    boolean isGoal;
    boolean isAgent;
    boolean isHome;

    /*
    * toSring method that prints a tile, depending on what is on it
    * note that it prints a space even if there is an agent
    * in order to print the agent, you must call the toString method of the Agent class
    * */
    public String toString(){

        if(this.isFurniture)
            return "X";

        if(this.isDirt)
            return "#";

        if(this.isHome)
            return "H";

        if(this.isGoal)
            return "$";

        return " ";
    }
}
