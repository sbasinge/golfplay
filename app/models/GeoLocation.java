package models;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Embeddable
public class GeoLocation implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public double lat;
	public double lng;
	@Temporal(TemporalType.DATE)
	public Date lastGeoLookup;
	
	public GeoLocation() {};
	public GeoLocation(double lat, double lng) {this.lat=lat; this.lng = lng;};
	
}
