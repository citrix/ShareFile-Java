
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

public class SFEncryptedEmailSendParams extends SFODataObject {

	@SerializedName("Message")
	private SFItem Message;
	@SerializedName("Attachments")
	private ArrayList<SFItem> Attachments;

	public SFItem getMessage() {
		return this.Message;
	}

	public void setMessage(SFItem message) {
		this.Message = message;
	}
	public ArrayList<SFItem> getAttachments() {
		return this.Attachments;
	}

	public void setAttachments(ArrayList<SFItem> attachments) {
		this.Attachments = attachments;
	}

}