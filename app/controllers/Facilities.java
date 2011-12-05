package controllers;

import models.Facility;
import play.mvc.With;


@With(Secure.class)
@CRUD.For(Facility.class)
public class Facilities extends CRUD {

}
