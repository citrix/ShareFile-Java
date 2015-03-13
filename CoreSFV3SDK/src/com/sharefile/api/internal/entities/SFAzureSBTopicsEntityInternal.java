
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

package com.sharefile.api.internal.entities;

import com.sharefile.api.*;
import com.sharefile.api.entities.*;
import com.sharefile.api.models.*;
import com.sharefile.api.internal.models.*;
import com.sharefile.api.SFApiQuery;
import com.sharefile.api.interfaces.ISFQuery;


import java.io.InputStream;
import java.util.ArrayList;
import java.net.URI;
import java.util.Date;
 
import com.google.gson.annotations.SerializedName;
import com.sharefile.api.enumerations.SFSafeEnum;
import com.sharefile.api.enumerations.SFSafeEnumFlags;

public class SFAzureSBTopicsEntityInternal extends SFODataEntityBase
{
	public SFAzureSBTopicsEntityInternal(ISFApiClient client) {
		super(client);
	}

	/**
	* Check if Topic exists in namespace
	* @param parentUrl 	 	
	*/
	public ISFQuery<SFAzureSBTopicsResponse> checkIfTopicExists(URI parentUrl) throws InvalidOrMissingParameterException 	{
		if (parentUrl == null) {
			throw new InvalidOrMissingParameterException("parentUrl");
		}

		SFApiQuery<SFAzureSBTopicsResponse> sfApiQuery = new SFApiQuery<SFAzureSBTopicsResponse>(this.client);
		sfApiQuery.setFrom("AzureSBTopics");
		sfApiQuery.setAction("CheckIfTopicExists");
		sfApiQuery.addIds(parentUrl);
		sfApiQuery.setHttpMethod("GET");
		return sfApiQuery;
	}

	/**
	* Create Topic in namespace
	* During Topic creation, it creates Manager, Send and Listen access policies
	* Send policy will be used by Publishers and Listens policy will be used by Subscribers
	* @param parentUrl 	 	
	* @return True if creation successful, else returns false
	*/
	public ISFQuery createTopic(URI parentUrl) throws InvalidOrMissingParameterException 	{
		if (parentUrl == null) {
			throw new InvalidOrMissingParameterException("parentUrl");
		}

		SFApiQuery sfApiQuery = new SFApiQuery(this.client);
		sfApiQuery.setFrom("AzureSBTopics");
		sfApiQuery.setAction("CreateTopic");
		sfApiQuery.addIds(parentUrl);
		sfApiQuery.setHttpMethod("POST");
		return sfApiQuery;
	}

	/**
	* Get restricted end-point credentials on Topic
	* @param parentUrl 	 	
	* @param id  (default: )	 	
	* @return Associate end-point credentials for specified policy
	*/
	public ISFQuery<SFAzureSBTopicsEndPointInfo> getTopicEndPoint(URI parentUrl, String id) throws InvalidOrMissingParameterException 	{
		if (parentUrl == null) {
			throw new InvalidOrMissingParameterException("parentUrl");
		}
		if (id == null) {
			throw new InvalidOrMissingParameterException("id");
		}

		SFApiQuery<SFAzureSBTopicsEndPointInfo> sfApiQuery = new SFApiQuery<SFAzureSBTopicsEndPointInfo>(this.client);
		sfApiQuery.setFrom("AzureSBTopics");
		sfApiQuery.setAction("GetTopicEndPoint");
		sfApiQuery.addIds(parentUrl);
		sfApiQuery.addActionIds(id);
		sfApiQuery.setHttpMethod("GET");
		return sfApiQuery;
	}

	/**
	* Regenerate Topics Access policies
	* @param parentUrl 	 	
	* @return Returns true if succeed, else returns false
	*/
	public ISFQuery regenerateTopicCredentials(URI parentUrl) throws InvalidOrMissingParameterException 	{
		if (parentUrl == null) {
			throw new InvalidOrMissingParameterException("parentUrl");
		}

		SFApiQuery sfApiQuery = new SFApiQuery(this.client);
		sfApiQuery.setFrom("AzureSBTopics");
		sfApiQuery.setAction("RegenerateTopicCredentials");
		sfApiQuery.addIds(parentUrl);
		sfApiQuery.setHttpMethod("POST");
		return sfApiQuery;
	}

	/**
	* Delete Topic from namespace
	* @param parentUrl 	 	
	*/
	public ISFQuery deleteTopic(URI parentUrl) throws InvalidOrMissingParameterException 	{
		if (parentUrl == null) {
			throw new InvalidOrMissingParameterException("parentUrl");
		}

		SFApiQuery sfApiQuery = new SFApiQuery(this.client);
		sfApiQuery.setFrom("AzureSBTopics");
		sfApiQuery.setAction("DeleteTopic");
		sfApiQuery.addIds(parentUrl);
		sfApiQuery.setHttpMethod("DELETE");
		return sfApiQuery;
	}

}

