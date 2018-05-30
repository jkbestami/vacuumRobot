import java.io.IOException;
import java.util.Scanner;

public class VacuumRobot {

    public static void main(String[] args) throws IOException, IllegalArgumentException{

        World world = new World();
        String nameOfFile="";
        int numberOfAgents;


        if(args.length==0||args.length>2){
            System.out.println("You did not enter any command line arguments.");
            System.out.println("Enter name of file: ");
            Scanner reader = new Scanner(System.in);
            nameOfFile = reader.next();
            System.out.println("Enter number of agents (1 or 2)");
            numberOfAgents = reader.nextInt();
        }else{

            nameOfFile = args[0];
            numberOfAgents = Integer.parseInt(args[1]);
        }

        if(numberOfAgents==0||numberOfAgents>2){
            throw new IllegalArgumentException("Invalid number of agents");
        }

        world.buildGrid(nameOfFile);

        for(int i= 0; i<numberOfAgents; i++){
            world.addAgent();
        }

        if(numberOfAgents==1){
            while (world.agents[0].isOn) {
                world.agents[0].makeDecision();
                System.out.println(world);
            }
        }else {
            while (world.agents[0].isOn || world.agents[1].isOn) {
                world.agents[0].makeDecision();
                world.agents[1].makeDecision();
                System.out.println(world);
            }
        }
    }
}
