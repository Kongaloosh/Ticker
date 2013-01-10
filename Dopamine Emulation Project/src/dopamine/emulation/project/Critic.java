/*
 * HEADER NOTE FOR MAT:
 * 
 * I haven't worked on this recently
 * 
 * critic to-do:
 * 
 *  - How are constants derived?
 *      a. eta
 *      b. tau
 *      c. rho
 *      d. sigma
 *      e. delta
 *      f. gamma
 *      g. baselineEffectiveRewardSignal
 *      h. inititalWeights
 * 
 */
package dopamine.emulation.project;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * 
 */
public class Critic {
//   constants
   
    double eta;
    double tau;
    double rho = 0.94;
    double sigma;
    double delta;
    double gamma;
    double baselineERsignal;
    double initialWeights;
    
    double[] e;
/*   e is the form that the stimuli in take, there are four corresponding to 
 * each of the lights that are availible
 * 
 *          [ L ] [ C ] [ R ]
 *                
 *          The final stimulus is the receptor on the tounge
 */ 
    int eBar;
    double [] x;
    /* xlm is the temporal relresentation from a stimulus e
     * this depends on the onset of the stimulus el(t)
     */
    
    public Critic (){
        e = new double [4];
        x = new double [e.length*3];
        eBar = 0;
    }
    
    public void updateE (int left, int centre, int right, int tounge) {
        if (!(left <= 1 && right <=1 && centre <= 1 && tounge <= 1)){
            try {
                throw new Exception("Input to updateE incorrect");
            } catch (Exception ex) {
                Logger.getLogger(Critic.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
//        if the values are correct
        e [0] = left;
        e [1] = centre;
        e [2] = right;
        e [3] = tounge;
        
    }
    
    public void prediction(){
        
        // to be filled
        
    }
    
    public void weight(){
        
        // to be filled
        
    }
    
    public void effectiveReinforcementSignal(){
        
        // to be filled
        
    }
    
    public void updatex (){
        /*
         * There are three cases to be considered
         */
        for (int i = 0; i < 3; i++) {
            
            if () {
//          Case 1: Onset of stimulus el(t) elicited the first representation component
                x[i] = e[i];
                x[(i*3)+1]=0;
                x[(i*3)+2]=0;
            }
            
            if (exp) {
//          Case 2: The slower components followed one time-step (100ms) after the onset of the stimulus el(t)
                x[i] = rho*e[i];
            }
            
            if (exp) {
//          Case 3: More than one time-step after the onset of stumulus el(t), the components
                
            }
        }
    }
    
    public int eBarCalculation (){
        
        if (eBar == 0){
            return 0;
        }
        return eBar;
    }
}
