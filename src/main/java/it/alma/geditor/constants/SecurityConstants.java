package it.alma.geditor.constants;

import it.alma.geditor.security.AuthoritiesConstants;

public class SecurityConstants {
	private SecurityConstants() {}
	public static final String IS_ADMINISTRATOR_CHECK 		= "hasRole('"+AuthoritiesConstants.ADMIN+"')";
}