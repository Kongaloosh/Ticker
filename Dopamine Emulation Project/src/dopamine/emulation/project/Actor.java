/*
 * HEADER NOTE FOR MAT:
 * 
 * This is just a skeleton right now, I'm just working on implementing the formulas
 * I have many of them down, but there's a few more and the statespace needs to be
 * figured out.
 * 
 * -- I'm going to focus on the action section, if you want to take a look at 
 * some of the other formulae in the actor section, that would be a good idea.
 */
package dopamine.emulation.project;

/*
 * Notes (ALEX):
 * 24/1/13:
 * I'm just laying the foundations looking over the actions
 * 
 * 25/1/13:
 * I'm checking out action prime, I built setEStimulus()
 * 
 * 26/1/13:
 * 
 * Things Alex Tends To Forget:
 * 
 * -- if there is an 'l' in the subscript, they're talking about stimulus
 * -- if there's an 'n' in the subscript, they're likely talking about actions
 */
public class Actor {
    
double [] stateSpace;

double learningRateAquisition = 0.1;
double learningRateExtinction = 0.7;

double discountRate = 0.9;

double randomDistributionMaximum = 0.5;
double baselineOfEffectiveReinforcement =0.5;

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

double v11;
double v12;
double v13;
double v14;

double v21;
double v22;
double v23;
double v24;


public Actor (int x){
    stateSpace = new double[x];
    
/* Explanation: stimulus tracs
 *  for this I used a small array to keep the numer of variables contained, as
 *  the definition for e refrences two timesteps prior to the current
 *  
 * For instance, e(0) = current timestep.
 * After this, each incrementation is a step earlier.
 */
}
  
public void run (
        double effectiveReinfocementSignal,
                double StimulusOne,
                double stimulusTwo){
   
    }
    // These are all the Es from two time-steps ago
    public void calculateAllEBar(double e1, double e2, double e3, double e4 ){
        // Alex
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
        // Alex
        // for use in the update of ebar
        if (x < 1) {
            return x;
        }
        return 0;
    }
    
     public double g(){
         // Alex
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
        
        if (System.currentTimeMillis()-lastActionTime >= 300){
            findAPrime();
        }
    }
    
    public void getSigma () {
        // needs a random generator. just math.random?
    }
    
    public void updateV1l(double reinforcementSignal){
        // Alex
        v11 += (findEta(reinforcementSignal))*ebar1[0];
        v12 += (findEta(reinforcementSignal))*ebar2[0];
        v13 += (findEta(reinforcementSignal))*ebar3[0];
        v14 += (findEta(reinforcementSignal))*ebar4[0];    
    }
    
    public void updateVl (double reinforcementSignal){
       // Alex
        v21 += (findEta(reinforcementSignal))*ebar1[0];
        v22 += (findEta(reinforcementSignal))*ebar2[0];
        v23 += (findEta(reinforcementSignal))*ebar3[0];
        v24 += (findEta(reinforcementSignal))*ebar4[0];    
    }
    
    public double findEta (double signal){
        //  Alex
        //  This is the definition of eta which is used for extinction.
        
        if (signal - baselineOfEffectiveReinforcement > 0) {
            // aquisition
            return 0.08;
        }else if (signal - baselineOfEffectiveReinforcement <= 0){
            //extinction
            return 0.002;
        }else{
            return 0;
        }
    }
    
    public void setEStimulus (int e1Prime, int e2Prime, int e3Prime, int e4Prime){
        // Alex
        // a measure of physical salience; if there is feeling 1, otherwise 0.
        
        e1[2]= e1[1];
        e1[1]= e1[0];
        e1[0] = e1Prime;
    
        e2[2]= e2[1];
        e2[1]= e2[0];
        e2[0] = e2Prime;
        
        e3[2]= e3[1];
        e3[1]= e3[0];
        e3[0] = e3Prime;
        
        e4[2]= e4[1];
        e4[1]= e4[0];
        e4[0] = e4Prime;
    }

    public void findAPrime(double[] vnl, double[] sigmaN ){
        // stub, need to figure out what the dot notation is for.
        // I believe there was a mention of it somewhere
        for (int i = 0; i < 10; i++) {
            
        }
    }
}
