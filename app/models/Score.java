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

import play.data.validation.Required;
import util.Constants;

@Entity
public class Score extends BaseEntity {

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TEESET_ID", nullable = false)
	public TeeSet teeSet;
	
    @Temporal(TemporalType.DATE)
    @Required
    public Date date;

    @ManyToOne
    public User user;

    @ManyToOne
    public TeeTime teeTime;

    @Column
    @Required
    public Integer grossScore;

    @Column
    @Required
    public Integer netScore;
	
    public boolean counter = false;
    
	public Score() {};
	
	public Score(TeeSet teeSet, String dateStr, int grossScore, int netScore) throws ParseException {
		this();
		this.teeSet = teeSet;
		this.date = Constants.DATE_FORMAT.parse(dateStr);
		this.grossScore = grossScore;
		this.netScore = netScore;
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
		BigDecimal temp = new BigDecimal(((netScore-teeSet.courseRating)*113)/teeSet.slopeRating).setScale(1,BigDecimal.ROUND_HALF_UP);
		return temp.doubleValue();
	}

	@Override
	public String toString() {
		return "Score [teeSet=" + teeSet + ", date=" + date + ", user=" + user + ", teeTime=" + teeTime + ", grossScore=" + grossScore
				+ ", netScore=" + netScore + ", counter=" + counter + "]";
	}
	
}
