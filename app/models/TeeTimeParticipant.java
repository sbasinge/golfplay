package models;

import java.math.BigDecimal;
import java.math.MathContext;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
public class TeeTimeParticipant extends BaseEntity {

	@OneToOne public TeeTime teetime;
	@OneToOne public User user;
	@OneToOne(cascade=CascadeType.ALL, orphanRemoval=true) public Score score;
	
	public BigDecimal courseIndex;
	public BigDecimal bestBallIndex;
	
	public TeeTimeParticipant() {};

	public TeeTimeParticipant(TeeTime teetime, User user) {
		this.teetime = teetime;
		this.user = user;
		calculateCourseIndex();
	};

	public void calculateCourseIndex() {
		if (teetime.teeSet != null) {
			BigDecimal handicap = user.getHandicap();
			if (handicap != null) {
				BigDecimal temp = handicap.multiply(new BigDecimal(teetime.teeSet.slopeRating));
				BigDecimal index = temp.divideToIntegralValue(new BigDecimal(113), MathContext.DECIMAL32);
				courseIndex = index;
				bestBallIndex = index.multiply(new BigDecimal(0.9), MathContext.DECIMAL32);
			}
		}
	}

	public void addScore(Integer grossScore, Integer netScore) {
		Score score = new Score();
		if (this.score != null) {
			score = this.score;
		}
		score.date = teetime.date;
		score.grossScore = grossScore;
		score.adjustedScore = netScore;
		score.teeTime = this.teetime;
		score.teeSet = teetime.teeSet;
		score.user = user;
		this.score = score;
	}
	
	public Integer getNetScore() {
		if (score==null ||score.grossScore==null) 
			return null;
		int grossScore = score.grossScore.intValue();
		int courseIndex = this.courseIndex.intValue();
		int total = grossScore - courseIndex;
		return total;
	}
	
	public Integer getScoreToPar() {
		int par = teetime.teeSet.par;
		if (getNetScore() == null)
			return null;
		int total = getNetScore() - par;
		return total;
	}

	@Override
	public String toString() {
		return "TeeTimeParticipant [score=" + score + ", courseIndex=" + courseIndex + ", bestBallIndex="
				+ bestBallIndex + "]";
	}
}
