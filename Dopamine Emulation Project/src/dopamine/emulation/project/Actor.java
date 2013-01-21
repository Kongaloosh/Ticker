/*
 * HEADER NOTE FOR MAT:
 * 
 * This is just a skeleton right now, I'm just working on implementing the formulas
 * I have many of them down, but there's a few more and the statespace needs to be
 * figured out.
 * 
 */
package dopamine.emulation.project;

/*
 * Notes:
 * 
 * 5/1/13:
 * 
 * --
 */
public class Actor {
/*
 *  Two actions possible, or more?
 *  Move to left button and move to right button
 *         -- is stay a possibility, given this is over
 *          a period of time?
 *  
 */
double [] stateSpace;

double learningRateAquisition = 0.1;
double learningRateExtinction = 0.7;

double discountRate = 0.9;

double randomDistributionMaximum = 0.5;

double stimulusDecay = 0.96;
double actionDecay  = stimulusDecay;
double lastActionTime;

double [] ebar1 = new double [2];
double [] ebar2 = new double [2];
double [] ebar3 = new double [2];
double [] ebar4 = new double [2];

double [] e1 = new double [2];
double [] e2 = new double [2];
double [] e3 = new double [2];
double [] e4 = new double [2];


public Actor (int x){
    stateSpace = new double[x];
    
/* Explanation: stimulus tracs
 *  for this I used a small array to keep the numer of variables contained, as
 *  the definition for e refrences two timesteps prior to the current
 */
}
  
public void run (
        double effectiveReinfocementSignal,
                double StimulusOne,
                double stimulusTwo){
   
    }
    // These are all the Es from two time-steps ago
    public void calculateAllEBar(double e1, double e2, double e3, double e4 ){
        
        // e(t) = h (el(t-2)+ delta * el(t-1))        
        ebar1[0] = h(e1) + (stimulusDecay * ebar1[1]); 
        ebar1 [1] = ebar1 [0];
        
        ebar2[0] = h(e2) + (stimulusDecay * ebar2[1]); 
        ebar2 [1] = ebar2 [0];
        
        ebar3[0] = h(e3) + (stimulusDecay * ebar3[1]); 
        ebar3 [1] = ebar3 [0];
        
        ebar4[0] = h(e4) + (stimulusDecay * ebar4[1]); 
        ebar4 [1] = ebar4 [0];
        
     
    }
    public double h(double x){
        // for use in the update of ebar
        if (x < 1) {
            return x;
        }
        return 0;
    }
    
     public double g(){
        if (
                e1[0]- e1[1]> 0 ||
                e2[0]- e2[1]> 0 ||
                e3[0]- e3[1]> 0 ||
                e4[0]- e4[1]> 0
                ) {
            return 1;
        }
        return 0;
    }
     
    public void decideAction (){
        if (System.currentTimeMillis()-lastActionTime >= 300)
            chooseNewAction();
    }
    
    public void getSigma () {
        // needs a random generator. just math.random?
    }
    
    public void actorWeights(double reinforcementSignal){
        
    }

    public void chooseNewAction(){
        // stub, need to figure out what the dot notation is for.
        // I believe there was a mention of it somewhere
    }
}
