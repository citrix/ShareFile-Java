
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

package com.sharefile.api.entities;

import com.sharefile.api.*;
import com.sharefile.api.entities.*;
import com.sharefile.api.models.*;
import com.sharefile.api.SFApiQuery;
import com.sharefile.api.interfaces.ISFQuery;


import java.io.InputStream;
import java.util.ArrayList;
import java.net.URI;
import java.util.Date;
 
import com.google.gson.annotations.SerializedName;
import com.sharefile.api.enumerations.SFSafeEnum;
import com.sharefile.api.enumerations.SFSafeEnumFlags;

public class SFAsyncOperationsEntity extends SFODataEntityBase
{
	public SFAsyncOperationsEntity(ISFApiClient client) {
		super(client);
	}

	/**
	* Get AsyncOperation by ID
	* Retrieve a single Async Op record by ID
	* @param url 	 	
	* @return A single Async Operation record
	*/
	public ISFQuery<SFAsyncOperation> get(URI url) throws InvalidOrMissingParameterException 	{
		if (url == null) {
			throw new InvalidOrMissingParameterException("url");
		}

		SFApiQuery<SFAsyncOperation> sfApiQuery = new SFApiQuery<SFAsyncOperation>(this.client);
		sfApiQuery.setFrom("AsyncOperations");
		sfApiQuery.addIds(url);
		sfApiQuery.setHttpMethod("GET");
		return sfApiQuery;
	}

	/**
	* Get List of AsyncOperations by Operation Batch ID
	* Retrieves all AsyncOperations on the specified batch
	* @param id  (default: )	 	
	* @return A Feed of AsyncOperation objects, containing all items in the specified batch
	*/
	public ISFQuery<SFODataFeed<SFAsyncOperation>> getByBatch(String id) throws InvalidOrMissingParameterException 	{
		if (id == null) {
			throw new InvalidOrMissingParameterException("id");
		}

		SFApiQuery<SFODataFeed<SFAsyncOperation>> sfApiQuery = new SFApiQuery<SFODataFeed<SFAsyncOperation>>(this.client);
		sfApiQuery.setFrom("AsyncOperations");
		sfApiQuery.setAction("GetByBatch");
		sfApiQuery.addActionIds(id);
		sfApiQuery.setHttpMethod("GET");
		return sfApiQuery;
	}

	/**
	* Get List of AsyncOperations by Folder
	* Retrieves all AsyncOperations associated with the calling user and the Item ID
	* @param id  (default: )	 	
	* @return A Feed of AsyncOperation objects, containing all pending operations in the specific folder, for the authenticated SDK user
	*/
	public ISFQuery<SFODataFeed<SFAsyncOperation>> getByFolder(String id) throws InvalidOrMissingParameterException 	{
		if (id == null) {
			throw new InvalidOrMissingParameterException("id");
		}

		SFApiQuery<SFODataFeed<SFAsyncOperation>> sfApiQuery = new SFApiQuery<SFODataFeed<SFAsyncOperation>>(this.client);
		sfApiQuery.setFrom("AsyncOperations");
		sfApiQuery.setAction("GetByFolder");
		sfApiQuery.addActionIds(id);
		sfApiQuery.setHttpMethod("GET");
		return sfApiQuery;
	}

	/**
	* Cancel AsyncOperation
	* Cancels a single Async operation record
	* @param url 	 	
	* @return The modified Async Operation record
	*/
	public ISFQuery<SFAsyncOperation> cancel(URI url) throws InvalidOrMissingParameterException 	{
		if (url == null) {
			throw new InvalidOrMissingParameterException("url");
		}

		SFApiQuery<SFAsyncOperation> sfApiQuery = new SFApiQuery<SFAsyncOperation>(this.client);
		sfApiQuery.setFrom("AsyncOperations");
		sfApiQuery.setAction("Cancel");
		sfApiQuery.addIds(url);
		sfApiQuery.setHttpMethod("POST");
		return sfApiQuery;
	}

	/**
	* Delete AsyncOperation
	* Cancels a single Async operation record (same as /Cancel)
	* @param url 	 	
	*/
	public ISFQuery delete(URI url) throws InvalidOrMissingParameterException 	{
		if (url == null) {
			throw new InvalidOrMissingParameterException("url");
		}

		SFApiQuery sfApiQuery = new SFApiQuery(this.client);
		sfApiQuery.setFrom("AsyncOperations");
		sfApiQuery.addIds(url);
		sfApiQuery.setHttpMethod("DELETE");
		return sfApiQuery;
	}

	/**
	* Cancel an Operation Batch
	* Cancel an Async Operation batch - all unfinished Async Operation records in that batch
	* will be moved to Cancelled state.
	* @param id  (default: )	 	
	* @return A list of the modified Async Operations in the batch
	*/
	public ISFQuery<SFODataFeed<SFAsyncOperation>> cancelBatch(String id) throws InvalidOrMissingParameterException 	{
		if (id == null) {
			throw new InvalidOrMissingParameterException("id");
		}

		SFApiQuery<SFODataFeed<SFAsyncOperation>> sfApiQuery = new SFApiQuery<SFODataFeed<SFAsyncOperation>>(this.client);
		sfApiQuery.setFrom("AsyncOperations");
		sfApiQuery.setAction("CancelBatch");
		sfApiQuery.addActionIds(id);
		sfApiQuery.setHttpMethod("POST");
		return sfApiQuery;
	}

	/**
	* Changes the state of an AsyncOperation
    * { "State": "..." }
	* Only the State parameter is updated, other fields are ignored
	* @param url 	 	
	* @param newAsyncOp 	 	
	* @return The modified Async Operation
	*/
	public ISFQuery<SFAsyncOperation> patch(URI url, SFAsyncOperation newAsyncOp) throws InvalidOrMissingParameterException 	{
		if (url == null) {
			throw new InvalidOrMissingParameterException("url");
		}
		if (newAsyncOp == null) {
			throw new InvalidOrMissingParameterException("newAsyncOp");
		}

		SFApiQuery<SFAsyncOperation> sfApiQuery = new SFApiQuery<SFAsyncOperation>(this.client);
		sfApiQuery.setFrom("AsyncOperations");
		sfApiQuery.addIds(url);
		sfApiQuery.setBody(newAsyncOp);
		sfApiQuery.setHttpMethod("PUT");
		return sfApiQuery;
	}

}

