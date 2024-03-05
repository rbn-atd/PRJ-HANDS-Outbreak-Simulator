package org.kclhi.hands;

import org.json.JSONObject;

import org.kclhi.hands.utility.Utils;

public class Sanitise {
    public static final double DEFAULT_SANITISE_PROPORTION = 0.75;
    public static final double DEFAULT_USAGE_UPPER = 0.6;

    public static double getSanitiseProportion(GraphTraverser traverser) {

        JSONObject properties = Utils.getPlugin().getJSONObject("seekers").getJSONObject("variablesByType").getJSONObject("properties");
        if( traverser.getName().contains("Lower") ) {
        return properties.getJSONObject("LowerSanitise").getDouble("SanitiseProportion");
        } else if( traverser.getName().contains("Low") ) {
        return properties.getJSONObject("LowSanitise").getDouble("SanitiseProportion");
        } else if( traverser.getName().contains("Medium") ) {
        return properties.getJSONObject("MediumSanitise").getDouble("SanitiseProportion");
        } else if( traverser.getName().contains("High") ) {
        return properties.getJSONObject("HighSanitise").getDouble("SanitiseProportion");
        } else if( traverser.getName().contains("Upper") ) {
        return properties.getJSONObject("UpperSanitise").getDouble("SanitiseProportion");
        }
    
        return Sanitise.DEFAULT_SANITISE_PROPORTION;
    
    }
    public interface SanitiseGraphTraverser {

        public boolean sanitiseSeeker();

    }

    public interface HighSanitiseGraphTraverser extends SanitiseGraphTraverser {}
    public interface MediumSanitiseGraphTraverser extends SanitiseGraphTraverser {}
    public interface LowSanitiseGraphTraverser extends SanitiseGraphTraverser {}

    public interface UpperSanitiseGraphTraverser extends SanitiseGraphTraverser {}
    public interface LowerSanitiseGraphTraverser extends SanitiseGraphTraverser {}
}
