
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

public class SFClickTrailEvent extends SFODataObject {

	@SerializedName("ClickTrailSessionId")
	private String ClickTrailSessionId;
	@SerializedName("TimeStamp")
	private Date TimeStamp;
	@SerializedName("Action")
	private String Action;
	@SerializedName("ItemId")
	private String ItemId;
	@SerializedName("ItemName")
	private String ItemName;
	@SerializedName("ItemType")
	private String ItemType;
	@SerializedName("Details")
	private String Details;
	@SerializedName("InternalData")
	private String InternalData;

	public String getClickTrailSessionId() {
		return this.ClickTrailSessionId;
	}

	public void setClickTrailSessionId(String clicktrailsessionid) {
		this.ClickTrailSessionId = clicktrailsessionid;
	}
	public Date getTimeStamp() {
		return this.TimeStamp;
	}

	public void setTimeStamp(Date timestamp) {
		this.TimeStamp = timestamp;
	}
	public String getAction() {
		return this.Action;
	}

	public void setAction(String action) {
		this.Action = action;
	}
	public String getItemId() {
		return this.ItemId;
	}

	public void setItemId(String itemid) {
		this.ItemId = itemid;
	}
	public String getItemName() {
		return this.ItemName;
	}

	public void setItemName(String itemname) {
		this.ItemName = itemname;
	}
	public String getItemType() {
		return this.ItemType;
	}

	public void setItemType(String itemtype) {
		this.ItemType = itemtype;
	}
	public String getDetails() {
		return this.Details;
	}

	public void setDetails(String details) {
		this.Details = details;
	}
	public String getInternalData() {
		return this.InternalData;
	}

	public void setInternalData(String internaldata) {
		this.InternalData = internaldata;
	}

}