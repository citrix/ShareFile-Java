
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

public class SFTenantAccountCreationParameters extends SFODataObject {

	@SerializedName("PartnerAccountId")
	private String PartnerAccountId;
	@SerializedName("TenantAccountId")
	private String TenantAccountId;
	@SerializedName("TenantAdminUserId")
	private String TenantAdminUserId;
	@SerializedName("MultiTenantZoneId")
	private String MultiTenantZoneId;

		/**
		* Account to be assigned as Partner
		*/
	public String getPartnerAccountId() {
		return this.PartnerAccountId;
	}

		/**
		* Account to be assigned as Partner
		*/
	public void setPartnerAccountId(String partneraccountid) {
		this.PartnerAccountId = partneraccountid;
	}
		/**
		* AccountId to be added as Tenant - Required
		*/
	public String getTenantAccountId() {
		return this.TenantAccountId;
	}

		/**
		* AccountId to be added as Tenant - Required
		*/
	public void setTenantAccountId(String tenantaccountid) {
		this.TenantAccountId = tenantaccountid;
	}
		/**
		* User from Partner Account to be added as Admin for the Tenant - Required
		*/
	public String getTenantAdminUserId() {
		return this.TenantAdminUserId;
	}

		/**
		* User from Partner Account to be added as Admin for the Tenant - Required
		*/
	public void setTenantAdminUserId(String tenantadminuserid) {
		this.TenantAdminUserId = tenantadminuserid;
	}
		/**
		* MultiTenantZone to be added to Tenant - Optional
		*/
	public String getMultiTenantZoneId() {
		return this.MultiTenantZoneId;
	}

		/**
		* MultiTenantZone to be added to Tenant - Optional
		*/
	public void setMultiTenantZoneId(String multitenantzoneid) {
		this.MultiTenantZoneId = multitenantzoneid;
	}

}