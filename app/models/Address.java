package models;

import java.util.Calendar;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import enums.State;

@Entity
public class Address extends BaseEntity {

	public String addressLine1;
	public String addressLine2;
	public String city;
	public String zipCode;

	@Enumerated(EnumType.STRING)
	public State state;
	
	@Embedded
	public GeoLocation geoLocation;

	public Address() {
	}

	public Address(String addressLine1, String city, State state, String zip, double lat, double lng) {
		this.addressLine1 = addressLine1;
		this.city = city;
		this.state = state;
		this.geoLocation = new GeoLocation();
		this.geoLocation.lat = lat;
		this.geoLocation.lng = lng;
		this.geoLocation.lastGeoLookup = Calendar.getInstance().getTime();
		this.zipCode = zip;
	}

	@Override
	public String toString() {
		return "Address [addressLine1=" + addressLine1 + ", city=" + city + ", zipCode=" + zipCode + ", state=" + state + "]";
	}

}
