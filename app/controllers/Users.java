package controllers;

import play.mvc.With;


@With(Secure.class)
public class Users extends CRUD {

	public static void view(String username) throws Exception {
		show(username);
	}
}
