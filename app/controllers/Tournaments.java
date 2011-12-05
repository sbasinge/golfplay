package controllers;

import play.mvc.With;


@With(Secure.class)
public class Tournaments extends CRUD {

}
