package org.kclhi.hands.graph;

import org.kclhi.hands.utility.ComparatorResult;

public class StringVertex implements Comparable<StringVertex> {
  
  private static int nodes = -1;
  
  private int node;
  private int nearbyHospitals;
  private int nearbySanitaryStations;
  private int vectorSurface;
  
  public StringVertex() {
    
    nodes++;
    
    node = nodes;
    
  }

  public StringVertex(int node, int nearbyHospitals, int nearbySanitaryStations, int vectorSurface) {
    this.node = node;
    this.nearbyHospitals = nearbyHospitals;
    this.nearbySanitaryStations = nearbySanitaryStations;
    this.vectorSurface = vectorSurface;
  }
  
  /**
  * @return
  */
  public int getNode() { 
    
    return node;
    
  }

  public void setNearbyHospitals(int nearbyHospitals) {
    this.nearbyHospitals = nearbyHospitals;
  }
  
  public int getNearbyHospitals() {
    return nearbyHospitals;
  }
  
  public void setNearbySanitaryStations(int nearbySanitaryStations) {
    this.nearbySanitaryStations = nearbySanitaryStations;
  }
  
  public int getNearbySanitaryStations() {
    return nearbySanitaryStations;
  }

  public void setVectorSurfaces(int vectorSurface) {
    this.vectorSurface = vectorSurface;
  }
  
  public int getVectorSurfaces() {
    return vectorSurface;
  }
  
  /**
  * 
  */
  public static void resetNodes() {
    
    nodes = -1;
    
  }
  
  public StringVertex(int node) {
    
    this.node = node;
    
  }
  
  public String toString() {
    
    return "v" + node;
    
  }
  
  /* (non-Javadoc)
  * @see java.lang.Object#hashCode()
  */
  @Override
  public int hashCode() {
    
    final int prime = 31;
    
    int result = 1;
    
    result = prime * result + node;
    
    return result;
    
  }
  
  /* (non-Javadoc)
  * @see java.lang.Object#equals(java.lang.Object)
  */
  @Override
  public boolean equals(Object obj) {
    
    if (this == obj) return true;
    
    if (obj == null) return false;
    
    if (getClass() != obj.getClass()) return false;
    
    StringVertex other = (StringVertex) obj;
    
    if (node != other.getNode()) return false;
    
    return true;
  }
  
  /* (non-Javadoc)
  * @see java.lang.Comparable#compareTo(java.lang.Object)
  */
  @Override
  public int compareTo(StringVertex o) {
    
    if (node < o.getNode()) { 
      
      return ComparatorResult.BEFORE;
      
    } else if (node > o.getNode()) {
      
      return ComparatorResult.AFTER;
      
    } else {
      
      return ComparatorResult.EQUAL;
      
    }
    
  }
  
}
