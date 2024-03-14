package org.kclhi.hands;

import java.util.HashMap;

import org.json.JSONObject;
import org.kclhi.hands.utility.Utils;

public class Success {

  // For seekers that aren't vaccinated from poor payoff due to failed games, what is their baseline immunity (higher better; 0.0 a natural choice)
  public static double BASE_NON_RESOURCE_VACCINATED = 0.0; 
  // What is the immunity of vaccinated seekers (higher better; 1.0 a natural choice)
  public static double BASE_RESOURCE_VACCINATED = 1.0; 
  
  public static double DEFAULT_LEVERAGE_PROBABILITY = 0.6;

  public interface ResourceVaccinatedTraverser {}
  public interface VariableVaccinatedTraverser {}
  
  // ~MDC Ideally this would be held within each vaccinated seeker instance, like variable vaccine resource (as opposed to centrally)
  public static boolean LEVERAGE_IMMUNITY(String traverserType) {

    JSONObject plugin = Utils.getPlugin();
    double leverageProbability = plugin!=null ? plugin.getJSONObject("seekers").getJSONObject("variablesByType").getJSONObject("behaviour").getJSONObject("VariableVaccinated").getDouble("leverageProbability") : DEFAULT_LEVERAGE_PROBABILITY;
    double leverageVaccinatedProbability = plugin!=null ? plugin.getJSONObject("seekers").getJSONObject("variablesByType").getJSONObject("behaviour").getJSONObject("Vaccinated").getDouble("leverageProbability") : DEFAULT_LEVERAGE_PROBABILITY;
    double leverageElderlyProbability = plugin!=null ? plugin.getJSONObject("seekers").getJSONObject("variablesByType").getJSONObject("behaviour").getJSONObject("Elderly").getDouble("leverageProbability") : DEFAULT_LEVERAGE_PROBABILITY;

    HashMap<String, Double> usageUppers = new HashMap<String, Double>() {{
      put("sMetaRandomVaccinated", leverageVaccinatedProbability);
      put("sMetaRandomElderly", leverageElderlyProbability);
      put("sMetaRandomElderlyVaccinated", (leverageElderlyProbability+leverageVaccinatedProbability)/2);
    }};

    if( usageUppers.keySet().contains(traverserType) ) { 

      return Math.random() < usageUppers.get(traverserType);
    
    } else {
    
      System.out.println("WARN: Leverage immunity lookup did not find " + traverserType);
    
      return true;
    
    }
  
  }

}
