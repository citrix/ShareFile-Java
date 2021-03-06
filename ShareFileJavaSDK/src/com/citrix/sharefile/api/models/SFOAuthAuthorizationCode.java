
// ------------------------------------------------------------------------------
// <auto-generated>
//     This code was generated by a tool.
//  
//     Changes to this file may cause incorrect behavior and will be lost if
//     the code is regenerated.
//     
//	   Copyright (c) 2017 Citrix ShareFile. All rights reserved.
// </auto-generated>
// ------------------------------------------------------------------------------

package com.citrix.sharefile.api.models;

import java.io.InputStream;
import java.util.ArrayList;
import java.net.URI;
import java.util.Date;
import java.util.Map;
import java.util.HashMap;

import com.google.gson.annotations.SerializedName;
import com.citrix.sharefile.api.*;
import com.citrix.sharefile.api.enumerations.*;
import com.citrix.sharefile.api.models.*;

public class SFOAuthAuthorizationCode extends SFODataObject {

	@SerializedName("Code")
	private String Code;
	@SerializedName("AppCp")
	private String AppCp;
	@SerializedName("ApiCp")
	private String ApiCp;
	@SerializedName("Subdomain")
	private String Subdomain;

	public String getCode() {
		return this.Code;
	}

	public void setCode(String code) {
		this.Code = code;
	}
	public String getAppCp() {
		return this.AppCp;
	}

	public void setAppCp(String appcp) {
		this.AppCp = appcp;
	}
	public String getApiCp() {
		return this.ApiCp;
	}

	public void setApiCp(String apicp) {
		this.ApiCp = apicp;
	}
	public String getSubdomain() {
		return this.Subdomain;
	}

	public void setSubdomain(String subdomain) {
		this.Subdomain = subdomain;
	}

}