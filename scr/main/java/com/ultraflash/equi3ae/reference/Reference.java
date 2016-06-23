package com.ultraflash.equi3ae.reference;

public class Reference 
{
	public static final String MOD_ID = "equi3ae";
	public static final String MOD_NAME = "Equivalent Exchange 3 AE";
	public static final String MOD_VERSION = "1.7.10-1.0";
	public static final String GUI_FACTORY_CLASS = "com.ultraflash.equi3ae.client.gui.GuiFactory";
	public static final String CLIENT_PROXY_CLASS = "com.ultraflash.equi3ae.proxy.ClientProxy";
	public static final String SERVER_PROXY_CLASS = "com.ultraflash.equi3ae.proxy.ServerProxy";
	public static final String LOWERCASE_MOD_ID = MOD_ID.toLowerCase();


	public static String getId(final String str) {
		return MOD_ID + ":" + str;
	}
}
