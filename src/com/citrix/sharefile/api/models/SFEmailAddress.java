
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

public class SFEmailAddress extends SFODataObject {

	@SerializedName("Email")
	private String Email;
	@SerializedName("IsConfirmed")
	private Boolean IsConfirmed;
	@SerializedName("IsPrimary")
	private Boolean IsPrimary;

	public String getEmail() {
		return this.Email;
	}

	public void setEmail(String email) {
		this.Email = email;
	}
	public Boolean getIsConfirmed() {
		return this.IsConfirmed;
	}

	public void setIsConfirmed(Boolean isconfirmed) {
		this.IsConfirmed = isconfirmed;
	}
	public Boolean getIsPrimary() {
		return this.IsPrimary;
	}

	public void setIsPrimary(Boolean isprimary) {
		this.IsPrimary = isprimary;
	}

}