package models;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import play.data.binding.As;
import play.data.validation.Required;
import util.Constants;

@Entity
public class Score extends BaseEntity {

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TEESET_ID", nullable = false)
	@Required
	public TeeSet teeSet;
	
    @Temporal(TemporalType.DATE)
    @Required
    @As(lang={"*"}, value={"MM/dd/yyyy"}) 
    public Date date;

    @ManyToOne
	@Required
    public User user;

    @ManyToOne
    public TeeTime teeTime;

    @Column(nullable=false)
    @Required
    public Integer grossScore;

    @Column(nullable=false)
    @Required
    public Integer adjustedScore;
	
    public boolean counter = false;
    
	public Score() {};
	
	public Score(TeeSet teeSet, String dateStr, int grossScore, int netScore) throws ParseException {
		this();
		this.teeSet = teeSet;
		this.date = Constants.DATE_FORMAT.parse(dateStr);
		this.grossScore = grossScore;
		this.adjustedScore = netScore;
		teeSet.addScore(this);
	}
	
	public Score(TeeSet teeSet, String dateStr, User user, int grossScore, int netScore) throws ParseException {
		this(teeSet, dateStr, grossScore, netScore);
		this.user = user;
		teeSet.addScore(this);
	}
	
	public Score(TeeTime teetime, User user) {
		this.teeSet = teetime.teeSet;
		this.date = teetime.date;
		this.user = user;
	}
	
	@Transient
	public double getDifferential() {
		BigDecimal temp = new BigDecimal(((adjustedScore-teeSet.courseRating)*113)/teeSet.slopeRating).setScale(1,BigDecimal.ROUND_HALF_UP);
		return temp.doubleValue();
	}

	@Override
	public String toString() {
		return "Score [id="+id+", date=" + date + ", grossScore=" + grossScore
				+ ", netScore=" + adjustedScore + ", counter=" + counter + "]";
	}
	
}
