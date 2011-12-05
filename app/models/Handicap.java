package models;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import play.data.validation.Required;

@Entity
public class Handicap extends BaseEntity {

    @Required
	@Column
	public BigDecimal handicap;

    @Temporal(TemporalType.TIMESTAMP)
    @Required
    public Date date;

    public Handicap() {}
    
    public Handicap(BigDecimal handicap, Date date) {
    	this.handicap = handicap;
    	this.date = date;
    }
    
	@Override
	public String toString() {
		return "Handicap [handicap=" + handicap + ", date=" + date + "]";
	}
}
