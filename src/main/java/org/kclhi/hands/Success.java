package org.kclhi.hands;

import java.util.HashMap;

import org.json.JSONObject;
import org.kclhi.hands.utility.Utils;

public class Success {

  // For seekers that aren't vaccinated from poor payoff due to failed games, what is their baseline immunity (higher better; 0.0 a natural choice)
  public static double BASE_NON_RESOURCE_INFECTION_BONUS = 0.0; 
  // What is the immunity of vaccinated seekers (higher better; 1.0 a natural choice)
  public static double BASE_RESOURCE_INFECTION_BONUS= 1.0; 
  
  public static double DEFAULT_LEVERAGE_PROBABILITY = 0.45;

  public interface ResourceVaccinatedTraverser {}
  public interface VariableVaccinatedTraverser {}
  
  // ~MDC Ideally this would be held within each vaccinated seeker instance, like variable vaccine resource (as opposed to centrally)

  // this function collects all existing leverage probabilities defined in the json file and makes the behaviour variable such that
  // if the randomly generated number is less than the leverage probability then true is returned.
  // the function determines behaviour change success.
  public static boolean LEVERAGE_IMMUNITY(String traverserType) {

    JSONObject plugin = Utils.getPlugin();

    double leverageMetaRandomProbability = plugin!=null ? plugin.getJSONObject("seekers").getJSONObject("variablesByType").getJSONObject("behaviour").getJSONObject("MetaRandom").getDouble("leverageProbability"): DEFAULT_LEVERAGE_PROBABILITY;
    double leverageElderlyProbability = plugin!=null ? plugin.getJSONObject("seekers").getJSONObject("variablesByType").getJSONObject("behaviour").getJSONObject("Elderly").getDouble("leverageProbability"): DEFAULT_LEVERAGE_PROBABILITY;
    double leverageAdultProbability = plugin!=null ? plugin.getJSONObject("seekers").getJSONObject("variablesByType").getJSONObject("behaviour").getJSONObject("Adult").getDouble("leverageProbability"): DEFAULT_LEVERAGE_PROBABILITY;
    double leverageChildProbability = plugin!=null ? plugin.getJSONObject("seekers").getJSONObject("variablesByType").getJSONObject("behaviour").getJSONObject("Child").getDouble("leverageProbability"): DEFAULT_LEVERAGE_PROBABILITY;
    double leverageVaccinatedProbability = plugin!=null ? plugin.getJSONObject("seekers").getJSONObject("variablesByType").getJSONObject("behaviour").getJSONObject("Vaccinated").getDouble("leverageProbability"): DEFAULT_LEVERAGE_PROBABILITY;
    double leverageImmunocompromisedProbability = plugin!=null ? plugin.getJSONObject("seekers").getJSONObject("variablesByType").getJSONObject("behaviour").getJSONObject("Immunocompromised").getDouble("leverageProbability"): DEFAULT_LEVERAGE_PROBABILITY;
    double leverageActiveProbability = plugin!=null ? plugin.getJSONObject("seekers").getJSONObject("variablesByType").getJSONObject("behaviour").getJSONObject("Active").getDouble("leverageProbability"): DEFAULT_LEVERAGE_PROBABILITY;
    double leverageObeseProbability = plugin!=null ? plugin.getJSONObject("seekers").getJSONObject("variablesByType").getJSONObject("behaviour").getJSONObject("Obese").getDouble("leverageProbability"): DEFAULT_LEVERAGE_PROBABILITY;
    double leverageSmokerProbability = plugin!=null ? plugin.getJSONObject("seekers").getJSONObject("variablesByType").getJSONObject("behaviour").getJSONObject("Smoker").getDouble("leverageProbability"): DEFAULT_LEVERAGE_PROBABILITY;
    double leverageAsthmaticProbability = plugin!=null ? plugin.getJSONObject("seekers").getJSONObject("variablesByType").getJSONObject("behaviour").getJSONObject("Asthmatic").getDouble("leverageProbability"): DEFAULT_LEVERAGE_PROBABILITY;
    HashMap<String, Double> usageUppers = new HashMap<String, Double>() {{
      put("sMetaRandom", leverageMetaRandomProbability);
      put("sMetaRandomVaccinated", leverageVaccinatedProbability);
      put("sMetaRandomElderly", leverageElderlyProbability);
      put("sMetaRandomAdult", leverageAdultProbability);
      put("sMetaRandomChild", leverageChildProbability);
      put("sMetaRandomImmunocompromisedElderly", (leverageImmunocompromisedProbability+leverageElderlyProbability)/2);
      put("sMetaRandomImmunocompromisedAdult", (leverageImmunocompromisedProbability+leverageAdultProbability)/2);
      put("sMetaRandomImmunocompromisedChild", (leverageImmunocompromisedProbability+leverageChildProbability)/2);
      put("sMetaRandomActiveElderly", (leverageActiveProbability+leverageElderlyProbability)/2);
      put("sMetaRandomActiveAdult", (leverageActiveProbability+leverageAdultProbability)/2);
      put("sMetaRandomActiveChild", (leverageActiveProbability+leverageChildProbability)/2);
      put("sMetaRandomObeseElderly", (leverageObeseProbability+leverageElderlyProbability)/2);
      put("sMetaRandomObeseAdult", (leverageObeseProbability+leverageAdultProbability)/2);
      put("sMetaRandomObeseChild", (leverageObeseProbability+leverageChildProbability)/2);
      put("sMetaRandomSmokerElderly", (leverageSmokerProbability+leverageElderlyProbability)/2);
      put("sMetaRandomSmokerAdult", (leverageSmokerProbability+leverageAdultProbability)/2);
      put("sMetaRandomSmokerChild", (leverageSmokerProbability+leverageChildProbability)/2);
      put("sMetaRandomAsthmaticElderly", (leverageAsthmaticProbability+leverageElderlyProbability)/2);
      put("sMetaRandomAsthmaticAdult", (leverageAsthmaticProbability+leverageAdultProbability)/2);
      put("sMetaRandomAsthmaticChild", (leverageAsthmaticProbability+leverageChildProbability)/2);
      put("sMetaRandomVaccinatedElderly", (leverageElderlyProbability+leverageElderlyProbability+leverageVaccinatedProbability)/3);
      put("sMetaRandomVaccinatedAdult", (leverageAdultProbability+leverageAdultProbability+leverageVaccinatedProbability)/3);
      put("sMetaRandomVaccinatedChild", (leverageChildProbability+leverageChildProbability+leverageVaccinatedProbability)/3);
      put("sMetaRandomVaccinatedImmunocompromisedElderly", (leverageImmunocompromisedProbability+leverageElderlyProbability+leverageVaccinatedProbability)/3);
      put("sMetaRandomVaccinatedImmunocompromisedAdult", (leverageImmunocompromisedProbability+leverageAdultProbability+leverageVaccinatedProbability)/3);
      put("sMetaRandomVaccinatedImmunocompromisedChild", (leverageImmunocompromisedProbability+leverageChildProbability+leverageVaccinatedProbability)/3);
      put("sMetaRandomVaccinatedActiveElderly", (leverageActiveProbability+leverageElderlyProbability+leverageVaccinatedProbability)/3);
      put("sMetaRandomVaccinatedActiveAdult", (leverageActiveProbability+leverageAdultProbability+leverageVaccinatedProbability)/3);
      put("sMetaRandomVaccinatedActiveChild", (leverageActiveProbability+leverageChildProbability+leverageVaccinatedProbability)/3);
      put("sMetaRandomVaccinatedObeseElderly", (leverageObeseProbability+leverageElderlyProbability+leverageVaccinatedProbability)/3);
      put("sMetaRandomVaccinatedObeseAdult", (leverageObeseProbability+leverageAdultProbability+leverageVaccinatedProbability)/3);
      put("sMetaRandomVaccinatedObeseChild", (leverageObeseProbability+leverageChildProbability+leverageVaccinatedProbability)/3);
      put("sMetaRandomVaccinatedActiveElderly", (leverageActiveProbability+leverageElderlyProbability+leverageVaccinatedProbability)/3);
      put("sMetaRandomVaccinatedActiveAdult", (leverageActiveProbability+leverageAdultProbability+leverageVaccinatedProbability)/3);
      put("sMetaRandomVaccinatedActiveChild", (leverageActiveProbability+leverageChildProbability+leverageVaccinatedProbability)/3);
      put("sMetaRandomVaccinatedSmokerElderly", (leverageSmokerProbability+leverageElderlyProbability+leverageVaccinatedProbability)/3);
      put("sMetaRandomVaccinatedSmokerAdult", (leverageSmokerProbability+leverageAdultProbability+leverageVaccinatedProbability)/3);
      put("sMetaRandomVaccinatedSmokerChild", (leverageSmokerProbability+leverageChildProbability+leverageVaccinatedProbability)/3);
      put("sMetaRandomVaccinatedAsthmaticElderly", (leverageAsthmaticProbability+leverageElderlyProbability+leverageVaccinatedProbability)/3);
      put("sMetaRandomVaccinatedAsthmaticAdult", (leverageAsthmaticProbability+leverageAdultProbability+leverageVaccinatedProbability)/3);
      put("sMetaRandomVaccinatedAsthmaticChild", (leverageAsthmaticProbability+leverageChildProbability+leverageVaccinatedProbability)/3);
    }};

    if( usageUppers.keySet().contains(traverserType) ) { 

      return Math.random() < usageUppers.get(traverserType);
    
    } else {
    
      System.out.println("WARN: Leverage immunity lookup did not find " + traverserType);
    
      return true;
    
    }
  
  }

}
