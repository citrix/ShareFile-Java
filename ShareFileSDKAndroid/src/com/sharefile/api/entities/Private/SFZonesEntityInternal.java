
// ------------------------------------------------------------------------------
// <auto-generated>
//     This code was generated by a tool.
//  
//     Changes to this file may cause incorrect behavior and will be lost if
//     the code is regenerated.
//     
//	   Copyright (c) 2014 Citrix ShareFile. All rights reserved.
// </auto-generated>
// ------------------------------------------------------------------------------

package com.sharefile.api.entities.Private;

import java.util.ArrayList;

import com.sharefile.api.SFApiQuery;
import com.sharefile.api.entities.SFZonesEntity;
import com.sharefile.api.enumerations.SFHttpMethod;
import com.sharefile.api.enumerations.SFZoneService;
import com.sharefile.api.models.SFMetadata;
import com.sharefile.api.models.SFODataFeed;
import com.sharefile.api.models.SFZone;


public class SFZonesEntityInternal extends SFZonesEntity
{
    /**
	* Get List of Zones
	* Retrieve the list of Zones accessible to the authenticated user
	* This method will concatenate the list of private zones in the user's account and the
	* list of public zones accessible to this account. Any user can see the list of zones.
	* @param services 	
	* @param includeDisabled 	
	* @return The list of public and private zones accessible to this user
    */
	public SFApiQuery<SFODataFeed<SFZone>> get(SFZoneService services , Boolean includeDisabled)
	{
		SFApiQuery<SFODataFeed<SFZone>> query = new SFApiQuery<SFODataFeed<SFZone>>();
		query.setFrom("Zones");
		query.addQueryString("services", services);
		query.addQueryString("includeDisabled", includeDisabled);
		query.setHttpMethod(SFHttpMethod.GET);
		return query;
	}

    /**
	* Get List of Zones
	* Retrieve the list of Zones accessible to the authenticated user
	* This method will concatenate the list of private zones in the user's account and the
	* list of public zones accessible to this account. Any user can see the list of zones.
	* @param services 	
	* @param includeDisabled 	
	* @return The list of public and private zones accessible to this user
    */
	public SFApiQuery<SFZone> get(String id, Boolean secret)
	{
		SFApiQuery<SFZone> query = new SFApiQuery<SFZone>();
		query.setFrom("Zones");
		query.setId(id);
		query.addQueryString("secret", secret);
		query.setHttpMethod(SFHttpMethod.GET);
		return query;
	}

    /**
	* Create Zone
    * {
    * "Name":"Name",
    * "HeartbeatTolerance":10,
    * "ZoneServices":"StorageZone, SharepointConnector, NetworkShareConnector"
    * }
	* Creates a new Zone.
	* @return the created zone
    */
	public SFApiQuery<SFZone> create(SFZone zone)
	{
		SFApiQuery<SFZone> query = new SFApiQuery<SFZone>();
		query.setFrom("Zones");
		query.setBody(zone);
		query.setHttpMethod(SFHttpMethod.GET);
		return query;
	}

    /**
	* Update Zone
    * {
    * "Name":"Name",
    * "HeartbeatTolerance":10,
    * "ZoneServices":"StorageZone, SharepointConnector, NetworkShareConnector"
    * }
	* Updates an existing zone
	* @param id 	
	* @param zone 	
	* @return The modified zone
    */
	public SFApiQuery<SFZone> update(String id, SFZone zone)
	{
		SFApiQuery<SFZone> query = new SFApiQuery<SFZone>();
		query.setFrom("Zones");
		query.setId(id);
		query.setBody(zone);
		query.setHttpMethod(SFHttpMethod.PATCH);
		return query;
	}

    /**
	* Delete Zone
	* Removes an existing zone
	* @param id 	
	* @param force 	
    */
	public SFApiQuery delete(String id, Boolean force)
	{
		SFApiQuery query = new SFApiQuery();
		query.setFrom("Zones");
		query.setId(id);
		query.addQueryString("force", force);
		query.setHttpMethod(SFHttpMethod.DELETE);
		return query;
	}

    /**
	* Reset Zone Secret
	* Resets the current Zone Secret to a new Random value
	* Caution! This Call will invalidate all Storage Center communications until the Storage Center Zone secret
	* is also updated.
	* User must be a Zone admin to perform this action
	* @param id 	
	* @return The modified Zone object
    */
	public SFApiQuery<SFZone> resetSecret( String id , String parentid)
	{
		SFApiQuery<SFZone> query = new SFApiQuery<SFZone>();
		query.setFrom("Zones");
		query.setAction("ResetSecret");
		query.setId(id);
		query.addQueryString("id", parentid);
		query.setHttpMethod(SFHttpMethod.POST);
		return query;
	}

    /**
	* Get Zone Metadata
	* Gets metadata associated with the specified zone
	* @param id 	
	* @return the zone metadata feed
    */
	public SFApiQuery<SFODataFeed<SFMetadata>> getMetadata(String id)
	{
		SFApiQuery<SFODataFeed<SFMetadata>> query = new SFApiQuery<SFODataFeed<SFMetadata>>();
		query.setFrom("Zones");
		query.setAction("Metadata");
		query.setId(id);
		query.setHttpMethod(SFHttpMethod.GET);
		return query;
	}

    /**
	* Create or update Zone Metadata
    * [
    * {"Name":"metadataName1", "Value":"metadataValue1", "IsPublic":"true"},
    * {"Name":"metadataName2", "Value":"metadataValue2", "IsPublic":"false"},
    * ...
    * ]
	* Creates or updates Metadata entries associated with the specified zone
	* @param id 	
	* @param metadata 	
	* @return the zone metadata feed
    */
	public SFApiQuery<SFODataFeed<SFMetadata>> createMetadata(String id, ArrayList<SFMetadata> metadata)
	{
		SFApiQuery<SFODataFeed<SFMetadata>> query = new SFApiQuery<SFODataFeed<SFMetadata>>();
		query.setFrom("Zones");
		query.setAction("Metadata");
		query.setId(id);
		query.setBody(metadata);
		query.setHttpMethod(SFHttpMethod.POST);
		return query;
	}

    /**
	* Delete Zone Metadata
	* Delete the Metadata entry associated with the specified zone
	* @param id 	
	* @param name 	
	* @return no data on success
    */
	public SFApiQuery deleteMetadata(String id, String name)
	{
		SFApiQuery query = new SFApiQuery();
		query.setFrom("Zones");
		query.setAction("Metadata");
		query.setId(id);
		query.addQueryString("name", name);
		query.setHttpMethod(SFHttpMethod.DELETE);
		return query;
	}

}

