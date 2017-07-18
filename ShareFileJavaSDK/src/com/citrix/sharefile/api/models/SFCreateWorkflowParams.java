
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
import com.citrix.sharefile.api.internal.models.*;

public class SFCreateWorkflowParams extends SFODataObject {

	@SerializedName("Participants")
	private ArrayList<SFWorkflowParticipant> Participants;
	@SerializedName("Message")
	private String Message;
	@SerializedName("Due")
	private Date Due;
	@SerializedName("IsSequenced")
	private Boolean IsSequenced;
	@SerializedName("RestartOnNewVersion")
	private Boolean RestartOnNewVersion;
	@SerializedName("DistributionMethod")
	private SFSafeEnum<SFDistributionMethod> DistributionMethod;
	@SerializedName("VersioningDisabled")
	private Boolean VersioningDisabled;
	@SerializedName("CallbackUrl")
	private URI CallbackUrl;

		/**
		* List of Participants for the workflow.
		*/
	public ArrayList<SFWorkflowParticipant> getParticipants() {
		return this.Participants;
	}

		/**
		* List of Participants for the workflow.
		*/
	public void setParticipants(ArrayList<SFWorkflowParticipant> participants) {
		this.Participants = participants;
	}
		/**
		* Optional message to be included to contact.
		*/
	public String getMessage() {
		return this.Message;
	}

		/**
		* Optional message to be included to contact.
		*/
	public void setMessage(String message) {
		this.Message = message;
	}
	public Date getDue() {
		return this.Due;
	}

	public void setDue(Date due) {
		this.Due = due;
	}
		/**
		* Enforce approval order. If true, then participant ordinals must be in sequential order
		*/
	public Boolean getIsSequenced() {
		return this.IsSequenced;
	}

		/**
		* Enforce approval order. If true, then participant ordinals must be in sequential order
		*/
	public void setIsSequenced(Boolean issequenced) {
		this.IsSequenced = issequenced;
	}
		/**
		* If true, then every approver must re-approve newly uploaded versions.
		*/
	public Boolean getRestartOnNewVersion() {
		return this.RestartOnNewVersion;
	}

		/**
		* If true, then every approver must re-approve newly uploaded versions.
		*/
	public void setRestartOnNewVersion(Boolean restartonnewversion) {
		this.RestartOnNewVersion = restartonnewversion;
	}
		/**
		* Determines if the server will email the workflow links or not.
		* If using , then will be populated on the result.
		* For Feedback workflows, if set to , then participants are not accepted.For Approval workflows, participants are required for both distribution methods.
		*/
	public SFSafeEnum<SFDistributionMethod> getDistributionMethod() {
		return this.DistributionMethod;
	}

		/**
		* Determines if the server will email the workflow links or not.
		* If using , then will be populated on the result.
		* For Feedback workflows, if set to , then participants are not accepted.For Approval workflows, participants are required for both distribution methods.
		*/
	public void setDistributionMethod(SFSafeEnum<SFDistributionMethod> distributionmethod) {
		this.DistributionMethod = distributionmethod;
	}
		/**
		* Set versioning to be disabled. This will remove the ability to request changes for this workflow instance.
		*/
	public Boolean getVersioningDisabled() {
		return this.VersioningDisabled;
	}

		/**
		* Set versioning to be disabled. This will remove the ability to request changes for this workflow instance.
		*/
	public void setVersioningDisabled(Boolean versioningdisabled) {
		this.VersioningDisabled = versioningdisabled;
	}
		/**
		* Webhook url to be notified when status on a Workflow changes. This is limited to first party use.
		*/
	public URI getCallbackUrl() {
		return this.CallbackUrl;
	}

		/**
		* Webhook url to be notified when status on a Workflow changes. This is limited to first party use.
		*/
	public void setCallbackUrl(URI callbackurl) {
		this.CallbackUrl = callbackurl;
	}

}