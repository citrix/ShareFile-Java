
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

import com.google.gson.annotations.SerializedName;

public class SFUserInfo extends SFODataObject {

	@SerializedName("CompanyName")
	private String CompanyName;
	@SerializedName("PlanName")
	private String PlanName;
	@SerializedName("PlanFeatures")
	private SFPlanFeatures PlanFeatures;
	@SerializedName("ApplicationUrl")
	private String ApplicationUrl;
	@SerializedName("StorageCenterUrl")
	private String StorageCenterUrl;

		/**
		* Company Name
		*/
	public String getCompanyName() {
		return this.CompanyName;
	}

		/**
		* Company Name
		*/
	public void setCompanyName(String companyname) {
		this.CompanyName = companyname;
	}
		/**
		* Plan Name
		*/
	public String getPlanName() {
		return this.PlanName;
	}

		/**
		* Plan Name
		*/
	public void setPlanName(String planname) {
		this.PlanName = planname;
	}
		/**
		* Plan Features
		*/
	public SFPlanFeatures getPlanFeatures() {
		return this.PlanFeatures;
	}

		/**
		* Plan Features
		*/
	public void setPlanFeatures(SFPlanFeatures planfeatures) {
		this.PlanFeatures = planfeatures;
	}
		/**
		* Application URL
		*/
	public String getApplicationUrl() {
		return this.ApplicationUrl;
	}

		/**
		* Application URL
		*/
	public void setApplicationUrl(String applicationurl) {
		this.ApplicationUrl = applicationurl;
	}
		/**
		* Default Storage Center Url
		*/
	public String getStorageCenterUrl() {
		return this.StorageCenterUrl;
	}

		/**
		* Default Storage Center Url
		*/
	public void setStorageCenterUrl(String storagecenterurl) {
		this.StorageCenterUrl = storagecenterurl;
	}

}