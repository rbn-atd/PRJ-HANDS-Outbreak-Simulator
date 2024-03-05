package org.kclhi.hands;

import org.kclhi.hands.utility.Utils;

import org.json.JSONObject;

/**
 * @author Reuben
 */

public class Vaccine {

  public static final double DEFAULT_VACCINE_PROPORTION = 0.75;
  public static final double DEFAULT_USAGE_UPPER = 0.6;

  public static double getVaccineProportion(GraphTraverser traverser) {

    JSONObject properties = Utils.getPlugin().getJSONObject("seekers").getJSONObject("variablesByType").getJSONObject("properties");
    if( traverser.getName().contains("Vaccinated") ) {
      return properties.getJSONObject("Vaccinated").getDouble("vaccineProportion");
    } else if( traverser.getName().contains("Low") ) {
      return properties.getJSONObject("LowVaccine").getDouble("vaccineProportion");
    } else if( traverser.getName().contains("Medium") ) {
      return properties.getJSONObject("MediumVaccine").getDouble("vaccineProportion");
    } else if( traverser.getName().contains("High") ) {
      return properties.getJSONObject("HighVaccine").getDouble("vaccineProportion");
    } else if( traverser.getName().contains("Upper") ) {
      return properties.getJSONObject("UpperVaccine").getDouble("vaccineProportion");
    }
  
    return Vaccine.DEFAULT_VACCINE_PROPORTION;
  
  }
  public interface VaccineGraphTraverser {

    public boolean useVaccine();

  }

  public interface HighVaccineGraphTraverser extends VaccineGraphTraverser {}
  public interface MediumVaccineGraphTraverser extends VaccineGraphTraverser {}
  public interface LowVaccineGraphTraverser extends VaccineGraphTraverser {}

  public interface UpperVaccineGraphTraverser extends VaccineGraphTraverser {}
  public interface LowerVaccineGraphTraverser extends VaccineGraphTraverser {}

  public interface VaccinatedGraphTraverser extends VaccineGraphTraverser {}

}
