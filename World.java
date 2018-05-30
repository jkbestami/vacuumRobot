import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class World {


    String newline = System.getProperty("line.separator");

    Tile[][]    grid;
    int         n; //row (x-axis)
    int         m; //col (y-axis)
    int         numFurniture; //number of pieces of furniture
    int         numDirt; //number of dirt piles

    Agent[] agents;  //array of agents
                     //agents[0] is first agent
                     //agents[1] is 2nd agent


    /**
    * method that "builds" the grid
    *
    * @param: String nameOfInputFile: name of the input file for configuration
    *
    * @post: grid is created with the appropriate tiles and what is in them
    *        numFurniture and numDirt is updated
    *
    * @throws: IOException if file not found etc
    *
    **/
    public void buildGrid(String nameOfInputFile) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(nameOfInputFile));
        Scanner scanner = new Scanner(System.in);
        String line = null;

        line = br.readLine();      //1st line
        String[] tokens = line.split(" ");
        this.n = Integer.parseInt(tokens[0]);
        this.m = Integer.parseInt(tokens[1]);
        this.numFurniture = Integer.parseInt(tokens[2]);
        this.numDirt = Integer.parseInt(tokens[3]);

        grid = new Tile[m][n];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                grid[i][j] = new Tile();
            }
        }

        grid[0][0].isHome = true;

        line = br.readLine();    //2nd line --> goal location(s)
        tokens = line.split(" ");

        grid[Integer.parseInt(tokens[1])][Integer.parseInt(tokens[0])].isGoal = true;

        if (tokens.length == 4) {
            grid[Integer.parseInt(tokens[3])][Integer.parseInt(tokens[2])].isGoal = true;
        }


        line = br.readLine();    //3rd line --> furniture location(s)
        tokens = line.split(" ");
        for (int i = 0; i < 2 * numFurniture; i = i + 2) {
            grid[Integer.parseInt(tokens[i + 1])][Integer.parseInt(tokens[i])].isFurniture = true;
        }

        line = br.readLine();    //4th line --> dirt location(s)
        tokens = line.split(" ");
        for (int i = 0; i < 2 * numDirt; i = i + 2) {
            grid[Integer.parseInt(tokens[i + 1])][Integer.parseInt(tokens[i])].isDirt = true;
        }
    }



    /**
     * method to add an agent
     *
     * @post: if there are no agents already:
     *              init agents and construct put an agent on [0][0]
     *        if there is already one agent:
     *              construct and put an agent on [m-1][n-1]
     *
     * */
    public void addAgent(){

        if(agents==null){

            agents = new Agent[2];
            agents[0] = new Agent(0, this);

            grid[0][0].isAgent = true;
        }else{
            agents[1] = new Agent(1, this);
            grid[m-1][n-1].isAgent = true;
        }


    }


   /**
    * method to generate the appropriate perception vector for an agent at a time step
    *
    * @param: int label: label of the agent for which to get perceptVector
    *
    * @returns: int[11]: which is the perception vector
    * */
    public int[] getPerceptVector(int label){

        int[] ret = new int[11];

        int y = agents[label].location[0];
        int x = agents[label].location[1];
        int direction = agents[label].direction;


        //facing the front
        if(direction == 0) {


            if (grid[y][x].isDirt) {
                ret[1] = 1;
            }
            if (y < m - 1) {
                if (grid[y + 1][x].isDirt)
                    ret[2] = 1;
            }
            if (y > 0) {
                if (grid[y - 1][x].isDirt)
                    ret[3] = 1;
            }
            if (x > 0) {
                if (grid[y][x - 1].isDirt)
                    ret[4] = 1;
            }
            if (x < n - 1) {
                if (grid[y][x + 1].isDirt)
                    ret[5] = 1;
            }


            if (grid[y][x].isGoal) {
                ret[6] = 1;
            }
            if (y < m - 1) {
                if (grid[y + 1][x].isGoal)
                    ret[7] = 1;
            }
            if (y > 0) {
                if (grid[y - 1][x].isGoal)
                    ret[8] = 1;
            }
            if (x > 0) {
                if (grid[y][x - 1].isGoal)
                    ret[9] = 1;
            }
            if (x < n - 1) {
                if (grid[y][x + 1].isGoal)
                    ret[10] = 1;
            }
        }


        //facing the right
        if(direction == 1) {


            if (grid[y][x].isDirt) {
                ret[1] = 1;
            }
            if (y < m - 1) {
                if (grid[y + 1][x].isDirt)
                    ret[4] = 1;
            }
            if (y > 0) {
                if (grid[y - 1][x].isDirt)
                    ret[5] = 1;
            }
            if (x > 0) {
                if (grid[y][x - 1].isDirt)
                    ret[3] = 1;
            }
            if (x < n - 1) {
                if (grid[y][x + 1].isDirt)
                    ret[2] = 1;
            }


            if (grid[y][x].isGoal) {
                ret[6] = 1;
            }
            if (y < m - 1) {
                if (grid[y + 1][x].isGoal)
                    ret[9] = 1;
            }
            if (y > 0) {
                if (grid[y - 1][x].isGoal)
                    ret[10] = 1;
            }
            if (x > 0) {
                if (grid[y][x - 1].isGoal)
                    ret[8] = 1;
            }
            if (x < n - 1) {
                if (grid[y][x + 1].isGoal)
                    ret[7] = 1;
            }
        }

        //facing south
        if(direction == 2) {


            if (grid[y][x].isDirt) {
                ret[1] = 1;
            }
            if (y < m - 1) {
                if (grid[y + 1][x].isDirt)
                    ret[3] = 1;
            }
            if (y > 0) {
                if (grid[y - 1][x].isDirt)
                    ret[2] = 1;
            }
            if (x > 0) {
                if (grid[y][x - 1].isDirt)
                    ret[5] = 1;
            }
            if (x < n - 1) {
                if (grid[y][x + 1].isDirt)
                    ret[4] = 1;
            }


            if (grid[y][x].isGoal) {
                ret[6] = 1;
            }
            if (y < m - 1) {
                if (grid[y + 1][x].isGoal)
                    ret[8] = 1;
            }
            if (y > 0) {
                if (grid[y - 1][x].isGoal)
                    ret[7] = 1;
            }
            if (x > 0) {
                if (grid[y][x - 1].isGoal)
                    ret[10] = 1;
            }
            if (x < n - 1) {
                if (grid[y][x + 1].isGoal)
                    ret[9] = 1;
            }
        }

        //facing the left
        if(direction == 3) {


            if (grid[y][x].isDirt) {
                ret[1] = 1;
            }
            if (y < m - 1) {
                if (grid[y + 1][x].isDirt)
                    ret[5] = 1;
            }
            if (y > 0) {
                if (grid[y - 1][x].isDirt)
                    ret[4] = 1;
            }
            if (x > 0) {
                if (grid[y][x - 1].isDirt)
                    ret[2] = 1;
            }
            if (x < n - 1) {
                if (grid[y][x + 1].isDirt)
                    ret[3] = 1;
            }


            if (grid[y][x].isGoal) {
                ret[6] = 1;
            }
            if (y < m - 1) {
                if (grid[y + 1][x].isGoal)
                    ret[10] = 1;
            }
            if (y > 0) {
                if (grid[y - 1][x].isGoal)
                    ret[9] = 1;
            }
            if (x > 0) {
                if (grid[y][x - 1].isGoal)
                    ret[7] = 1;
            }
            if (x < n - 1) {
                if (grid[y][x + 1].isGoal)
                    ret[8] = 1;
            }
        }

        return ret;

    }


    /**
     * moves an agent forward if there is nothing in front of him
     * if there is, then agent stays at his place and perceptVector is updated
     *
     * @param: int label: label of the agent to move
     *
     * @post: agent is moved forward if no obstacles
     *        or agent stays in place
     *        perceptVector is updated in both cases
     * */
    public void moveAgent(int label){

        int x = agents[label].location[1];
        int y = agents[label].location[0];
        int direction = agents[label].direction;



        if(direction == 0){ //Facing North
            y = y+1;
        }
        if(direction == 1){ //Facing East
            x = x+1;
        }
        if(direction == 2){ //Facing South
            y = y-1;
        }
        if(direction == 3){ //Facing West
            x = x-1;
        }


        if(y>=m|| y<0 || x>=n || x<0||grid[y][x].isFurniture||grid[y][x].isAgent){

            agents[label].perceptVector = getPerceptVector(label);
            agents[label].perceptVector[0] = 1;

        }else{

            grid[agents[label].location[0]][agents[label].location[1]].isAgent = false;

            agents[label].location[0] = y;
            agents[label].location[1] = x;
            grid[y][x].isAgent = true;
            agents[label].perceptVector = getPerceptVector(label);

        }

    }

    /**
     * turn the agent off, and update the tile so as to not make the goal visible
     * if it is a goal.
     *
     * @param: int label: label of the agent to turn off
     *
     * @post: agent is turned off
     *        if tile at which agent is turned off is goal, tile is not goal anymore
     *
     * */
    public void turnAgentOff(int label){

        int y = agents[label].location[0];
        int x = agents[label].location[1];
        agents[label].isOn = false;

        if(grid[y][x].isGoal){
            grid[y][x].isGoal = false;
        }


    }

    /**
     * method for the agent to vacuum
     *
     * @param: int label: label of agent to vacuum
     *
     * @post: tile where the agent is, is no longer dirt
     *      : perceptVector is updated
     * */
    public void vacuum(int label){

        int y = agents[label].location[0];
        int x = agents[label].location[1];

        grid[y][x].isDirt= false;
        agents[label].perceptVector = getPerceptVector(label);
    }


    public String toString(){

        String ret = "";

        String border = "+";

        for(int i = 0; i<n;i++){

            border = border + "-+";
        }


        ret = border;


        for(int i = m-1; i>=0; i--){

            ret = ret + newline + "|";

            for(int j = 0; j<n; j++){

                if(grid[i][j].isAgent){

                    if(i==agents[0].location[0]&&j==agents[0].location[1]){

                        ret = ret + agents[0].toString();
                    }else{

                        ret = ret + agents[1].toString();
                    }


                }else {
                    ret = ret + grid[i][j];
                }

                if(j==n-1) {
                    ret = ret  +"|";
                }else{
                    ret = ret+ ":";
                }
            }

        }

        ret = ret + newline + border + newline + newline;

        if(agents[1]==null){

            ret = ret + "T  DU DF DB DL DR GU GF GB GL GR"+ newline;

            for(int i: agents[0].perceptVector){

                ret = ret + i + "  ";
            }

            ret = ret + newline + "Power Level: "+agents[0].powerLevel+newline;
            ret = ret+ "Performance Level: " + agents[0].performanceLevel;


        }else{

            ret = ret + "Agent #1:" +newline+"T  DU DF DB DL DR GU GF GB GL GR"+ newline;

            for(int i: agents[0].perceptVector){

                ret = ret + i + "  ";
            }
            ret = ret + newline + "Power Level: "+agents[0].powerLevel+newline;
            ret = ret+ "Performance Level: " + agents[0].performanceLevel;
            ret = ret+newline+newline+"Agent #2:" + newline + "T  DU DF DB DL DR GU GF GB GL GR"+ newline;

            for(int i: agents[1].perceptVector){

                ret = ret + i + "  ";
            }
            ret = ret + newline + "Power Level: "+agents[1].powerLevel+newline;
            ret = ret + "Performance Level: " + agents[1].performanceLevel;
        }

        return ret + newline;
    }
}
