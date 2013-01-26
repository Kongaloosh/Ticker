/*
 * 
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
 * I just finished things up. All the formulae are set, we just need to clean
 * things up.
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

double aPrime1;
double aPrime2;
double [] action = double [2];

double[] aBar1 = new double[1];
double[] aBar2 = new double[1];

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
           
        }
    }
    
    public double getSigma () {
        // needs a random generator. just math.random?
        return 1;
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
    
    public void setaPrime1 () {
        // Alex
        // This can be simplified later if we want to with some arrays.
        aPrime1 = 
                (((v11*ebar1[0])-getSigma())+
                ((v12*ebar2[0])-getSigma())+
                ((v13*ebar3[0])-getSigma())+
                ((v14*ebar4[0])-getSigma()))*g();    
    }
    
    public void setAPrime2 () {
        aPrime2 =
                (((v21*ebar1[0])-getSigma())+
                ((v22*ebar2[0])-getSigma())+
                ((v23*ebar3[0])-getSigma())+
                ((v24*ebar4[0])-getSigma()))*g();  
    }
    
    public void findAPrime (){
        setaPrime1();
        setAPrime2();
        action [2] = action [1];
        action [1] = action [0];
        
        
        if (aPrime1 > 0 && aPrime1 > aPrime2) {
            action[0] = 1;
        }else{
            action[0] = 0;
        }
    }
    
    public void updateABar(){
        aBar1[0] = h(action[0]+(stimulusDecay*aBar1[1]));
        aBar2[0] = h(action[0]+(stimulusDecay*aBar2[1]));
    }
}
