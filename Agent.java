import java.util.Random;

public class Agent {

    int powerLevel;
    int performanceLevel;
    boolean isOn;

    int[] perceptVector; //perception Vector

    World world; //world in which agent resides

    int label; //label to distinguish between first and second agent

    Random rand = new Random();

    int[] location; //array of two coordinates [y][x] for the agent's location
    int direction; //the direction in which the agent is facing: North:0 East:1 South:2 West:3


    /**
     * Constructor for an agent
     * @param: int label: to distinguish between agents (1st agent has label 0, 2nd agent label 1)
     *         World world: this is the World in which the agent is in
     * @post:  powerLevel and performanceLevel are set to 100
     *         perceptVector is initialized
     *         label and world is set
     *         agent#1 is place on tile[0][0] facing North
     *         agent#2 (if label is 1) is place on tile[m-1[n-1] facing South
     * */
    public Agent(int label, World world){

        this.world = world;
        powerLevel = 100;
        performanceLevel = 100;
        perceptVector = new int[11];
        this.label = label;
        this.isOn = true;

        //part1 agent
        if(label == 0){
            location= new int[2];
            location[0] = 0;
            location[1] = 0;

            direction = 0;
        }else{
            location= new int[2];
            location[0] = world.m - 1;
            location[1] = world.n - 1;

            direction = 2;

        }

    }




    /**
     * toString method that returns a representation of the agent:
     *      the direction of the agent is representend by the direction of the triangle
     *      two different triangles to distinguish agents
     *
     * */
    public String toString(){

        if(label == 0) {

            switch(direction){
                case 0: return "▲";

                case 1: return "▶";

                case 2: return "▼";

                case 3: return "◀";

                default: return "ERROR";
            }

        }
        else{

            switch(direction){
                case 0: return "△";

                case 1: return "▷";

                case 2: return "▽";

                case 3: return "◁";

                default: return "ERROR";
            }

        }

    }


    /**
     * This is the method that implements the behavior module of the agent.
     * The modules are implemented with a series of condition/decision pairs
     * and these pairs are arranged so that the decision of one condition
     * will lead to a particular other condition that will in turn
     * set off another action that will bring about another condition
     * and so on.
     *
     * This permits the implementation of "long term" behavior
     * without needing memory of any previous state. Instead the module is
     * implemented directly by the order and the if-statements themselves.
     * It also permits the agent to decide on ONE action everytime it
     * receives ONE perception vector.
     *
     * The metaphor here being that the if-statements and their order
     * represent the "hard coded" circuitry used in actual modules of
     * behavioral systems.
     *
     * note: we assume that the agent CANNOT look at his performanceLevel
     *       but he CAN look at his powerLevel
     *
     * @post: the agent performs THE (one) action that it decides
     *                  (so direction and location of the agent can change)
     *        powerLevel and performanceLevel are updated accordingly
     *        isOn is updated if needs be
     *        this.perceptVector is updated
     *
     * @calls: world.getPerceptVector(label)
     *         world.vacuum
     *
     *
     *
     * */
    public void makeDecision() {


        //do not make any decisions if you are or should be off
        if(this.isOn==false||powerLevel<=2){
            world.turnAgentOff(label);
            return;
        }

        //if you run into an object, turn right or left (with 50% chance of r or l)
        if (perceptVector[0] == 1) {

            int d = rand.nextInt(2);


            if (d == 0) { //turn right

                direction = (direction + 1) % 4;
                powerLevel = powerLevel - 2;
                performanceLevel =  performanceLevel -2;
                perceptVector = world.getPerceptVector(label);
            } else { //turn left

                direction = (direction + 3) % 4;
                powerLevel = powerLevel - 2;
                performanceLevel =  performanceLevel -2;
                perceptVector = world.getPerceptVector(label);
            }
        }

        //if there is dirt under you, vacuum
        else if(perceptVector[1] == 1) {
            world.vacuum(label);
            powerLevel = powerLevel - 4;
            performanceLevel =  performanceLevel -4;
            performanceLevel = performanceLevel + 10;
        }
        //if there is dirt in front of you, go to the dirt
        //this will lead to the condition (dirt under) in the next time step
        else if(perceptVector[2] == 1) {
            world.moveAgent(label);
            powerLevel = powerLevel -2;
            performanceLevel =  performanceLevel -2;
        }

        //if there is dirt behind you, do a right turn
        //this will lead to the condition (dirt on right)
        else if(perceptVector[3] == 1){

            direction = (direction +1)%4; //turn right
            powerLevel = powerLevel -2;
            performanceLevel =  performanceLevel -2;
            perceptVector = world.getPerceptVector(label);

        }
        //if there is dirt on your left, turn left
        //this will lead to the condition (dirt in front)
        else if(perceptVector[4]==1){

            direction = (direction+3)%4; //turn left
            powerLevel = powerLevel -2;
            performanceLevel =  performanceLevel -2;
            perceptVector = world.getPerceptVector(label);
        }
        //if there is dirt on your right, turn right
        //this will lead to the condition (dirt in front)
        else if(perceptVector[5]==1){

            direction = (direction+1)%4; //turn right
            powerLevel = powerLevel -2;
            performanceLevel =  performanceLevel -2;
            perceptVector = world.getPerceptVector(label);
        }
        //if you reach goal, turn off
        else if(perceptVector[6]==1){
            world.turnAgentOff(label);
        }
        //if the goal is in front of you, and you have "low" powerLevel
        //go to the goal. this leads to condition (goal under)
        //here the low powerLevel condition is added in case the agent
        //still has some "spare" powerLevel and we dont want to reach
        //the condition (goal under) just yet, maybe vacuum/roam more
        else if(perceptVector[7]==1&&this.powerLevel<45){

            world.moveAgent(label);
            powerLevel = powerLevel -2;
            performanceLevel =  performanceLevel -2;
        }
        //if the goal is behind and "low" power level turn right
        //this leads to condition (goal on right)
        //same reasoning for "low" powerLevel behavior
        else if(perceptVector[8]==1&&this.powerLevel<45){

            direction = (direction+1)%4; //turn right
            powerLevel = powerLevel -2;
            performanceLevel =  performanceLevel -2;
            perceptVector = world.getPerceptVector(label);
        }
        //if the goal is on left and "low" powerLevel, turn left
        //this leads to condition (goal in front)
        //same reasoning for "low" powerLevel behavior as before
        else if(perceptVector[9]==1&&this.powerLevel<45){

            direction = (direction+3)%4; //turn left
            powerLevel = powerLevel -2;
            performanceLevel =  performanceLevel -2;
            perceptVector = world.getPerceptVector(label);
        }
        //if the goal is on right and "low" powerLevel, turn right
        //this leads to condition (goal in front)
        //same reasoning for "low" powerLevel behavior as before
        else if(perceptVector[10]==1&&this.powerLevel<45){

            direction = (direction+1)%4; //turn left
            powerLevel = powerLevel -2;
            performanceLevel =  performanceLevel -2;
            perceptVector = world.getPerceptVector(label);
        }

        //if no determined action is to be taken, then "roam":
        //advance forward (80% chance)
        //turn right (10% chance)
        //turn left (10% chance)

        else{
            int d = rand.nextInt(10);

            if (d == 0) { //turn right

                direction = (direction + 1) % 4;
                powerLevel = powerLevel - 2;
                performanceLevel =  performanceLevel -2;
                perceptVector = world.getPerceptVector(label);
            } else if(d==1){ //turn left

                direction = (direction + 3) % 4;
                powerLevel = powerLevel - 2;
                performanceLevel =  performanceLevel -2;
                perceptVector = world.getPerceptVector(label);
            }else {
                world.moveAgent(label);
                powerLevel = powerLevel - 2;
                performanceLevel =  performanceLevel -2;
            }
        }


    }
}
