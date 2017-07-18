
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

package com.citrix.sharefile.api.entities;

import com.citrix.sharefile.api.*;
import com.citrix.sharefile.api.entities.*;
import com.citrix.sharefile.api.models.*;
import com.citrix.sharefile.api.SFApiQuery;
import com.citrix.sharefile.api.interfaces.ISFQuery;


import java.io.InputStream;
import java.util.ArrayList;
import java.net.URI;
import java.util.Date;
 
import com.google.gson.annotations.SerializedName;
import com.citrix.sharefile.api.enumerations.SFSafeEnum;
import com.citrix.sharefile.api.enumerations.SFSafeEnumFlags;
import com.citrix.sharefile.api.interfaces.ISFApiClient;
import com.citrix.sharefile.api.exceptions.InvalidOrMissingParameterException;

public class SFStorageCentersEntity extends SFEntitiesBase
{
	public SFStorageCentersEntity(ISFApiClient client) {
		super(client);
	}

	/**
	* Get Storage Center
	* @param url 	 	
	* @return A single Storage Center
	*/
	public ISFQuery<SFStorageCenter> get(URI url) throws InvalidOrMissingParameterException 	{
		if (url == null) {
			throw new InvalidOrMissingParameterException("url");
		}

		SFApiQuery<SFStorageCenter> sfApiQuery = new SFApiQuery<SFStorageCenter>(this.client);
		sfApiQuery.setFrom("StorageCenters");
		sfApiQuery.addIds(url);
		sfApiQuery.setHttpMethod("GET");
		return sfApiQuery;
	}

	/**
	* Patch Storage Center
    * {
    * "ExternalAddress":"https://server/",
    * "Version":"4.12.20",
    * "HostName":"hostname" }
	* @param url 	 	
	* @param sc 	 	
	* @return Modified Storage Center
	*/
	public ISFQuery<SFStorageCenter> update(URI url, SFStorageCenter sc) throws InvalidOrMissingParameterException 	{
		if (url == null) {
			throw new InvalidOrMissingParameterException("url");
		}
		if (sc == null) {
			throw new InvalidOrMissingParameterException("sc");
		}

		SFApiQuery<SFStorageCenter> sfApiQuery = new SFApiQuery<SFStorageCenter>(this.client);
		sfApiQuery.setFrom("StorageCenters");
		sfApiQuery.addIds(url);
		sfApiQuery.setBody(sc);
		sfApiQuery.setHttpMethod("PATCH");
		return sfApiQuery;
	}

	/**
	* Delete Storage Center
	* @param url 	 	
	*/
	public ISFQuery delete(URI url) throws InvalidOrMissingParameterException 	{
		if (url == null) {
			throw new InvalidOrMissingParameterException("url");
		}

		SFApiQuery sfApiQuery = new SFApiQuery(this.client);
		sfApiQuery.setFrom("StorageCenters");
		sfApiQuery.addIds(url);
		sfApiQuery.setHttpMethod("DELETE");
		return sfApiQuery;
	}

	/**
	* Get List of StorageCenters from Zone
	* Lists Storage Centers of a given Zone
	* @param url 	 	
	* @return A list of Storage Centers associated with the provided zone
	*/
	public ISFQuery<SFODataFeed<SFStorageCenter>> getByZone(URI url) throws InvalidOrMissingParameterException 	{
		if (url == null) {
			throw new InvalidOrMissingParameterException("url");
		}

		SFApiQuery<SFODataFeed<SFStorageCenter>> sfApiQuery = new SFApiQuery<SFODataFeed<SFStorageCenter>>(this.client);
		sfApiQuery.setFrom("Zones");
		sfApiQuery.setAction("StorageCenters");
		sfApiQuery.addIds(url);
		sfApiQuery.setHttpMethod("GET");
		return sfApiQuery;
	}

	/**
	* Create StorageCenter
    * {
    * "ExternalAddress":"https://server/",
    * "Version":"4.12.20",
    * "HostName":"hostname"
    * }
	* Creates a new Storage Center associated with a specific zone
	* @param url 	 	
	* @param storageCenter 	 	
	* @return The new storage center
	*/
	public ISFQuery<SFStorageCenter> createByZone(URI url, SFStorageCenter storageCenter) throws InvalidOrMissingParameterException 	{
		if (url == null) {
			throw new InvalidOrMissingParameterException("url");
		}
		if (storageCenter == null) {
			throw new InvalidOrMissingParameterException("storageCenter");
		}

		SFApiQuery<SFStorageCenter> sfApiQuery = new SFApiQuery<SFStorageCenter>(this.client);
		sfApiQuery.setFrom("Zones");
		sfApiQuery.setAction("StorageCenters");
		sfApiQuery.addIds(url);
		sfApiQuery.setBody(storageCenter);
		sfApiQuery.setHttpMethod("POST");
		return sfApiQuery;
	}

	/**
	* Update StorageCenter
    * {
    * "ExternalAddress":"https://server/",
    * "Version":"4.12.20",
    * "HostName":"hostname"
    * }
	* Updates an existing Storage Center
	* @param zUrl 	 	
	* @param storageCenter 	 	
	* @return the modified storage center
	*/
	public ISFQuery<SFStorageCenter> updateByZone(URI zUrl, String scid, SFStorageCenter storageCenter) throws InvalidOrMissingParameterException 	{
		if (zUrl == null) {
			throw new InvalidOrMissingParameterException("zUrl");
		}
		if (scid == null) {
			throw new InvalidOrMissingParameterException("scid");
		}
		if (storageCenter == null) {
			throw new InvalidOrMissingParameterException("storageCenter");
		}

		SFApiQuery<SFStorageCenter> sfApiQuery = new SFApiQuery<SFStorageCenter>(this.client);
		sfApiQuery.setFrom("Zones");
		sfApiQuery.setAction("StorageCenters");
		sfApiQuery.addIds(zUrl);
		sfApiQuery.addActionIds(scid);
		sfApiQuery.setBody(storageCenter);
		sfApiQuery.setHttpMethod("PATCH");
		return sfApiQuery;
	}

	/**
	* Delete StorageCenter
	* Removes an existing storage center
	* @param zUrl 	 	
	*/
	public ISFQuery deleteByZone(URI zUrl, String scid) throws InvalidOrMissingParameterException 	{
		if (zUrl == null) {
			throw new InvalidOrMissingParameterException("zUrl");
		}
		if (scid == null) {
			throw new InvalidOrMissingParameterException("scid");
		}

		SFApiQuery sfApiQuery = new SFApiQuery(this.client);
		sfApiQuery.setFrom("Zones");
		sfApiQuery.setAction("StorageCenters");
		sfApiQuery.addIds(zUrl);
		sfApiQuery.addActionIds(scid);
		sfApiQuery.setHttpMethod("DELETE");
		return sfApiQuery;
	}

	/**
	* Get StorageCenter Metadata
	* Gets metadata associated with the specified storage center
	* @param zUrl 	 	
	* @return the storage center metadata feed
	*/
	public ISFQuery<SFODataFeed<SFMetadata>> getMetadata(URI zUrl, String scid) throws InvalidOrMissingParameterException 	{
		if (zUrl == null) {
			throw new InvalidOrMissingParameterException("zUrl");
		}
		if (scid == null) {
			throw new InvalidOrMissingParameterException("scid");
		}

		SFApiQuery<SFODataFeed<SFMetadata>> sfApiQuery = new SFApiQuery<SFODataFeed<SFMetadata>>(this.client);
		sfApiQuery.setFrom("Zones");
		sfApiQuery.setAction("StorageCenters");
		sfApiQuery.addIds(zUrl);
		sfApiQuery.addActionIds(scid);
		sfApiQuery.addSubAction("Metadata");
		sfApiQuery.setHttpMethod("GET");
		return sfApiQuery;
	}

	/**
	* Create or update StorageCenter Metadata
    * [
    * {"Name":"metadataName1", "Value":"metadataValue1", "IsPublic":"true"},
    * {"Name":"metadataName2", "Value":"metadataValue2", "IsPublic":"false"},
    * ...
    * ]
	* Creates or updates Metadata entries associated with the specified storage center
	* @param zUrl 	 	
	* @param metadata 	 	
	* @return the storage center metadata feed
	*/
	public ISFQuery<SFODataFeed<SFMetadata>> createMetadata(URI zUrl, String scid, ArrayList<SFMetadata> metadata) throws InvalidOrMissingParameterException 	{
		if (zUrl == null) {
			throw new InvalidOrMissingParameterException("zUrl");
		}
		if (scid == null) {
			throw new InvalidOrMissingParameterException("scid");
		}
		if (metadata == null) {
			throw new InvalidOrMissingParameterException("metadata");
		}

		SFApiQuery<SFODataFeed<SFMetadata>> sfApiQuery = new SFApiQuery<SFODataFeed<SFMetadata>>(this.client);
		sfApiQuery.setFrom("Zones");
		sfApiQuery.setAction("StorageCenters");
		sfApiQuery.addIds(zUrl);
		sfApiQuery.addActionIds(scid);
		sfApiQuery.addSubAction("Metadata");
		sfApiQuery.setBody(metadata);
		sfApiQuery.setHttpMethod("POST");
		return sfApiQuery;
	}

	/**
	* Delete StorageCenter Metadata
	* Delete the Metadata entry associated with the specified storage center
	* @param zUrl 	 	
	* @param name 	 	
	* @return no data on success
	*/
	public ISFQuery deleteMetadata(URI zUrl, String scid, String name) throws InvalidOrMissingParameterException 	{
		if (zUrl == null) {
			throw new InvalidOrMissingParameterException("zUrl");
		}
		if (scid == null) {
			throw new InvalidOrMissingParameterException("scid");
		}
		if (name == null) {
			throw new InvalidOrMissingParameterException("name");
		}

		SFApiQuery sfApiQuery = new SFApiQuery(this.client);
		sfApiQuery.setFrom("Zones");
		sfApiQuery.setAction("StorageCenters");
		sfApiQuery.addIds(zUrl);
		sfApiQuery.addActionIds(scid);
		sfApiQuery.addSubAction("Metadata");
		sfApiQuery.addQueryString("name", name);
		sfApiQuery.setHttpMethod("DELETE");
		return sfApiQuery;
	}

}

