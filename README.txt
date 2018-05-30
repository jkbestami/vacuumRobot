A rudimentary setup for a behavior-based Vacuum Robot Agent
The "robots" here live in the virtual world: a grid
In this grid there are pieces of furniture, patches of dirt and a Goal
Moreover, each robot has an "energy level", thata depletes while moving or vacuuming
The objective for the robots is to avoid obstacles (wall and furniture), vacuum the dirt 
and reach the goal before running out of energy

The robots' actions is implemented using a simple behavior-based approach
which involves classifying actions into different "layers" by priority:

Each robot has a perception layer: 
[collided][dirtUnder][dirtFront][dirtBehind][dirtLeft][dirtRight]
    [goalUnder][goalFront][goalBehind][goalLeft][goalRight]

which he updates every move and he has to do an action:
    turn, go forward, vacuum or turn off.


Which action each robot chooses which action to do with
a simple behavior based approach 
    in which different actions are lumped into "layers" of different priorities:


e.g.
    0: evading obstacles
    1: vacuuming
    2: reaching goal





Input format:

n m numFurniture numDirt
goalLocation(s)
furnitureLocation(s)
dirtLocation(s)




e.g:



5 8 2 4
2 4 4 0 
1 4 2 3
1 2 3 3 4 6 2 5

this will generate a 5x8 grid with 2 pieces of furniture and 4 patches of dirt
Two goals: at (2,4) and (4,0)
furnitures: at (1,4) and (2,3)
dirts @ : (1,2) (3,3) (4,6) (2,5)





TO COMPILE:

javac VacuumRobot.java

(the Main function is in VacuumRobot.java)



TO RUN, two choices:

1) java VacuumRobot [inputfile] [numberOfAgents]
   
    eg: VacuumRobot inputTest1.txt 2

2) java VacuumRobot
    
   it will then prompt for the inputfile name and number of agents to be entered


