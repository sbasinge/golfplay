package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import enums.TournamentType;

@Entity
public class Tournament extends BaseEntity {

	public String name;

	@Enumerated(EnumType.STRING)
	public TournamentType type;
	
	@OneToMany(mappedBy="tournament")
	@OrderBy("date ASC")
	public List<TeeTime> teeTimes = new ArrayList<TeeTime>();
	
	public void addTeeTime(TeeTime teeTime) {
		teeTime.tournament = this;
		teeTimes.add(teeTime);
	}
	
	public int getNumTeeTimes() {
		return teeTimes.size();
	}
	
	public boolean hasScores() {
		boolean retVal = false;
		for (TeeTime teetime : teeTimes) {
			if (teetime.hasScores()) {
				retVal = true;
				break;
			}
		}
		return retVal;
	}
}
