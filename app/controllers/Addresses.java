package controllers;

import models.Address;
import play.mvc.With;


@With(Secure.class)
@CRUD.For(Address.class)
public class Addresses extends CRUD {

}
