package controllers;

import play.mvc.With;


@With(Secure.class)
public class Roles extends CRUD {

}
