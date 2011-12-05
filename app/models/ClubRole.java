package models;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;

@Entity
@NamedQueries(value = { 
		@NamedQuery(name = "findClubRoleByNameAndClub", query = "select cr from ClubRole cr where cr.role.name = :roleName and cr.club.name = :clubName") }
)
public class ClubRole extends BaseEntity {

	@OneToOne(cascade={CascadeType.ALL})
	public Role role;
	
	@OneToOne
	public Club club;
	
	public ClubRole() {};
	public ClubRole(Club club, Role role) {
		this.club = club;
		this.role = role;
	}
	
}
