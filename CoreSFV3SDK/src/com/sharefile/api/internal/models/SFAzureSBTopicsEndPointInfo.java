
// ------------------------------------------------------------------------------
// <auto-generated>
//     This code was generated by a tool.
//  
//     Changes to this file may cause incorrect behavior and will be lost if
//     the code is regenerated.
//     
//	   Copyright (c) 2015 Citrix ShareFile. All rights reserved.
// </auto-generated>
// ------------------------------------------------------------------------------

package com.sharefile.api.internal.models;

import java.io.InputStream;
import java.util.ArrayList;
import java.net.URI;
import java.util.Date;
import java.util.Map;
import java.util.HashMap;

import com.google.gson.annotations.SerializedName;
import com.sharefile.api.*;
import com.sharefile.api.enumerations.*;
import com.sharefile.api.models.*;

public class SFAzureSBTopicsEndPointInfo extends SFODataObject {

	@SerializedName("Key")
	private String Key;
	@SerializedName("Secret")
	private String Secret;
	@SerializedName("EndPoint")
	private String EndPoint;

	public String getKey() {
		return this.Key;
	}

	public void setKey(String key) {
		this.Key = key;
	}
	public String getSecret() {
		return this.Secret;
	}

	public void setSecret(String secret) {
		this.Secret = secret;
	}
	public String getEndPoint() {
		return this.EndPoint;
	}

	public void setEndPoint(String endpoint) {
		this.EndPoint = endpoint;
	}

}