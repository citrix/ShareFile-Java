
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

import com.citrix.sharefile.api.models.*;
import com.citrix.sharefile.api.SFApiQuery;
import com.citrix.sharefile.api.interfaces.ISFQuery;


import java.util.ArrayList;
import java.net.URI;

import com.google.gson.annotations.SerializedName;
import com.citrix.sharefile.api.interfaces.ISFApiClient;
import com.citrix.sharefile.api.exceptions.InvalidOrMissingParameterException;

public class SFAccessControlsEntity extends SFEntitiesBase
{
	public SFAccessControlsEntity(ISFApiClient client) {
		super(client);
	}

	/**
	* Get AccessControl by ID
	* Retrieves a single Access Control entry for a given Item and Principal
	* @return A single AccessControl object matching the query
	*/
	public ISFQuery<SFAccessControl> get(URI url) throws InvalidOrMissingParameterException 	{
		if (url == null) {
			throw new InvalidOrMissingParameterException("url");
		}

		SFApiQuery<SFAccessControl> sfApiQuery = new SFApiQuery<SFAccessControl>(this.client);
		sfApiQuery.setFrom("AccessControls");
		sfApiQuery.addIds(url);
		sfApiQuery.setHttpMethod("GET");
		return sfApiQuery;
	}

	/**
	* Get AccessControl List By Item
	* Retrieves the Access Control List for a given Item.
	* @param url 	 	
	* @return Access Control List of the given object ID.
	*/
	public ISFQuery<SFODataFeed<SFAccessControl>> getByItem(URI url) throws InvalidOrMissingParameterException 	{
		if (url == null) {
			throw new InvalidOrMissingParameterException("url");
		}

		SFApiQuery<SFODataFeed<SFAccessControl>> sfApiQuery = new SFApiQuery<SFODataFeed<SFAccessControl>>(this.client);
		sfApiQuery.setFrom("Items");
		sfApiQuery.setAction("AccessControls");
		sfApiQuery.addIds(url);
		sfApiQuery.setHttpMethod("GET");
		return sfApiQuery;
	}

	/**
	* Create AccessControl
    * {
    * "Principal":{"url":"https://account.sf-api.com/v3/Groups(id)"},
    * "CanUpload":true,
    * "CanDownload":true,
    * "CanView":true,
    * "CanDelete":true,
    * "CanManagePermissions":true,
    * "Message":"Message"
    * }
	* Creates a new Access Controls entry for a given Item. Access controls can only define a single Principal,
	* which can be either a Group or User. The 'Principal' element is specified as an object - you should populate
	* either the URL or the ID reference.
	* 
	* If CanDownload is set to true, CanView will automatically be set to true.
	* If CanDownload is set to false, CanDelete will be set to false.
	* In order for a user/group to be able to manage permissions, they must be able to upload, download, and delete. Otherwise it will be set to false.
	* @param url 	 	
	* @param accessControl 	 	
	* @param recursive  (default: false)	 	
	* @param sendDefaultNotification  (default: false)	 	
	* @return the created or modified AccessControl instance
	*/
	public ISFQuery<SFAccessControl> createByItem(URI url, SFAccessControl accessControl, Boolean recursive, Boolean sendDefaultNotification, String message) throws InvalidOrMissingParameterException 	{
		if (url == null) {
			throw new InvalidOrMissingParameterException("url");
		}
		if (accessControl == null) {
			throw new InvalidOrMissingParameterException("accessControl");
		}
		if (recursive == null) {
			throw new InvalidOrMissingParameterException("recursive");
		}
		if (sendDefaultNotification == null) {
			throw new InvalidOrMissingParameterException("sendDefaultNotification");
		}
		if (message == null) {
			throw new InvalidOrMissingParameterException("message");
		}

		SFApiQuery<SFAccessControl> sfApiQuery = new SFApiQuery<SFAccessControl>(this.client);
		sfApiQuery.setFrom("Items");
		sfApiQuery.setAction("AccessControls");
		sfApiQuery.addIds(url);
		sfApiQuery.addQueryString("recursive", recursive);
		sfApiQuery.addQueryString("sendDefaultNotification", sendDefaultNotification);
		accessControl.addProperty("Message", message);
		sfApiQuery.setBody(accessControl);
		sfApiQuery.setHttpMethod("POST");
		return sfApiQuery;
	}

	/**
	* Create AccessControl
    * {
    * "Principal":{"url":"https://account.sf-api.com/v3/Groups(id)"},
    * "CanUpload":true,
    * "CanDownload":true,
    * "CanView":true,
    * "CanDelete":true,
    * "CanManagePermissions":true,
    * "Message":"Message"
    * }
	* Creates a new Access Controls entry for a given Item. Access controls can only define a single Principal,
	* which can be either a Group or User. The 'Principal' element is specified as an object - you should populate
	* either the URL or the ID reference.
	* 
	* If CanDownload is set to true, CanView will automatically be set to true.
	* If CanDownload is set to false, CanDelete will be set to false.
	* In order for a user/group to be able to manage permissions, they must be able to upload, download, and delete. Otherwise it will be set to false.
	* @param url 	 	
	* @param accessControl 	 	
	* @param recursive  (default: false)	 	
	* @param sendDefaultNotification  (default: false)	 	
	* @return the created or modified AccessControl instance
	*/
	public ISFQuery<SFAccessControl> createByItem(URI url, SFAccessControl accessControl, Boolean recursive, Boolean sendDefaultNotification) throws InvalidOrMissingParameterException 	{
		if (url == null) {
			throw new InvalidOrMissingParameterException("url");
		}
		if (accessControl == null) {
			throw new InvalidOrMissingParameterException("accessControl");
		}
		if (recursive == null) {
			throw new InvalidOrMissingParameterException("recursive");
		}
		if (sendDefaultNotification == null) {
			throw new InvalidOrMissingParameterException("sendDefaultNotification");
		}

		SFApiQuery<SFAccessControl> sfApiQuery = new SFApiQuery<SFAccessControl>(this.client);
		sfApiQuery.setFrom("Items");
		sfApiQuery.setAction("AccessControls");
		sfApiQuery.addIds(url);
		sfApiQuery.addQueryString("recursive", recursive);
		sfApiQuery.addQueryString("sendDefaultNotification", sendDefaultNotification);
		sfApiQuery.setBody(accessControl);
		sfApiQuery.setHttpMethod("POST");
		return sfApiQuery;
	}

	/**
	* Create AccessControl
    * {
    * "Principal":{"url":"https://account.sf-api.com/v3/Groups(id)"},
    * "CanUpload":true,
    * "CanDownload":true,
    * "CanView":true,
    * "CanDelete":true,
    * "CanManagePermissions":true,
    * "Message":"Message"
    * }
	* Creates a new Access Controls entry for a given Item. Access controls can only define a single Principal,
	* which can be either a Group or User. The 'Principal' element is specified as an object - you should populate
	* either the URL or the ID reference.
	* 
	* If CanDownload is set to true, CanView will automatically be set to true.
	* If CanDownload is set to false, CanDelete will be set to false.
	* In order for a user/group to be able to manage permissions, they must be able to upload, download, and delete. Otherwise it will be set to false.
	* @param url 	 	
	* @param accessControl 	 	
	* @param recursive  (default: false)	 	
	* @return the created or modified AccessControl instance
	*/
	public ISFQuery<SFAccessControl> createByItem(URI url, SFAccessControl accessControl, Boolean recursive) throws InvalidOrMissingParameterException 	{
		if (url == null) {
			throw new InvalidOrMissingParameterException("url");
		}
		if (accessControl == null) {
			throw new InvalidOrMissingParameterException("accessControl");
		}
		if (recursive == null) {
			throw new InvalidOrMissingParameterException("recursive");
		}

		SFApiQuery<SFAccessControl> sfApiQuery = new SFApiQuery<SFAccessControl>(this.client);
		sfApiQuery.setFrom("Items");
		sfApiQuery.setAction("AccessControls");
		sfApiQuery.addIds(url);
		sfApiQuery.addQueryString("recursive", recursive);
		sfApiQuery.setBody(accessControl);
		sfApiQuery.setHttpMethod("POST");
		return sfApiQuery;
	}

	/**
	* Create AccessControl
    * {
    * "Principal":{"url":"https://account.sf-api.com/v3/Groups(id)"},
    * "CanUpload":true,
    * "CanDownload":true,
    * "CanView":true,
    * "CanDelete":true,
    * "CanManagePermissions":true,
    * "Message":"Message"
    * }
	* Creates a new Access Controls entry for a given Item. Access controls can only define a single Principal,
	* which can be either a Group or User. The 'Principal' element is specified as an object - you should populate
	* either the URL or the ID reference.
	* 
	* If CanDownload is set to true, CanView will automatically be set to true.
	* If CanDownload is set to false, CanDelete will be set to false.
	* In order for a user/group to be able to manage permissions, they must be able to upload, download, and delete. Otherwise it will be set to false.
	* @param url 	 	
	* @param accessControl 	 	
	* @return the created or modified AccessControl instance
	*/
	public ISFQuery<SFAccessControl> createByItem(URI url, SFAccessControl accessControl) throws InvalidOrMissingParameterException 	{
		if (url == null) {
			throw new InvalidOrMissingParameterException("url");
		}
		if (accessControl == null) {
			throw new InvalidOrMissingParameterException("accessControl");
		}

		SFApiQuery<SFAccessControl> sfApiQuery = new SFApiQuery<SFAccessControl>(this.client);
		sfApiQuery.setFrom("Items");
		sfApiQuery.setAction("AccessControls");
		sfApiQuery.addIds(url);
		sfApiQuery.setBody(accessControl);
		sfApiQuery.setHttpMethod("POST");
		return sfApiQuery;
	}

	/**
	* Update AccessControl
    * {
    * "Principal":{"Email":"user@domain.com"},
    * "CanUpload":true,
    * "CanDownload":true,
    * "CanView":true,
    * "CanDelete":true,
    * "CanManagePermissions":true
    * }
	* Updates an existing Access Controls of a given Item. The Principal element cannot be modified, it is provided
	* in the Body to identity the AccessControl element to be modified. You can provide an ID, Email or URL on the
	* Principal object.
	* @param url 	 	
	* @param accessControl 	 	
	* @param recursive  (default: false)	 	
	* @return the created or modified AccessControl instance
	*/
	public ISFQuery<SFAccessControl> updateByItem(URI url, SFAccessControl accessControl, Boolean recursive) throws InvalidOrMissingParameterException 	{
		if (url == null) {
			throw new InvalidOrMissingParameterException("url");
		}
		if (accessControl == null) {
			throw new InvalidOrMissingParameterException("accessControl");
		}
		if (recursive == null) {
			throw new InvalidOrMissingParameterException("recursive");
		}

		SFApiQuery<SFAccessControl> sfApiQuery = new SFApiQuery<SFAccessControl>(this.client);
		sfApiQuery.setFrom("Items");
		sfApiQuery.setAction("AccessControls");
		sfApiQuery.addIds(url);
		sfApiQuery.addQueryString("recursive", recursive);
		sfApiQuery.setBody(accessControl);
		sfApiQuery.setHttpMethod("PATCH");
		return sfApiQuery;
	}

	/**
	* Update AccessControl
    * {
    * "Principal":{"Email":"user@domain.com"},
    * "CanUpload":true,
    * "CanDownload":true,
    * "CanView":true,
    * "CanDelete":true,
    * "CanManagePermissions":true
    * }
	* Updates an existing Access Controls of a given Item. The Principal element cannot be modified, it is provided
	* in the Body to identity the AccessControl element to be modified. You can provide an ID, Email or URL on the
	* Principal object.
	* @param url 	 	
	* @param accessControl 	 	
	* @return the created or modified AccessControl instance
	*/
	public ISFQuery<SFAccessControl> updateByItem(URI url, SFAccessControl accessControl) throws InvalidOrMissingParameterException 	{
		if (url == null) {
			throw new InvalidOrMissingParameterException("url");
		}
		if (accessControl == null) {
			throw new InvalidOrMissingParameterException("accessControl");
		}

		SFApiQuery<SFAccessControl> sfApiQuery = new SFApiQuery<SFAccessControl>(this.client);
		sfApiQuery.setFrom("Items");
		sfApiQuery.setAction("AccessControls");
		sfApiQuery.addIds(url);
		sfApiQuery.setBody(accessControl);
		sfApiQuery.setHttpMethod("PATCH");
		return sfApiQuery;
	}

	/**
	* Delete AccessControl
	* Deletes an AccessControl entry by itemID and principalID. This method does not return any object, a 204 (No Content)
	* response indicates success.
	*/
	public ISFQuery delete(URI url) throws InvalidOrMissingParameterException 	{
		if (url == null) {
			throw new InvalidOrMissingParameterException("url");
		}

		SFApiQuery sfApiQuery = new SFApiQuery(this.client);
		sfApiQuery.setFrom("AccessControls");
		sfApiQuery.addIds(url);
		sfApiQuery.setHttpMethod("DELETE");
		return sfApiQuery;
	}

	/**
	* Create or Update multiple AccessControls for a given Item
    * {
    * "NotifyUser":true,
    * "NotifyMessage":"msg",
    * 
    * "AccessControlParams":
    * [
    * {
    * "AccessControl":
    * {
    * "Principal" : { "Id":"existing_user_id" },
    * "CanUpload" : true,
    * "CanDownload" : false,
    * "CanView" : true
    * },
    * "NotifyUser":false
    * },
    * {
    * "AccessControl":
    * {
    * "Principal" : { "Id":"group_id" },
    * "CanUpload" : false,
    * "CanDownload" : true,
    * "CanView" : true
    * },
    * "Recursive":true
    * },
    * {
    * "AccessControl":
    * {
    * "Principal" : { "Email":"new_or_existing_user@a.com" },
    * "CanUpload" : false,
    * "CanDownload" : true,
    * "CanView" : true
    * }
    * }
    * ]
    * }
	* All the AccessControls are created or updated for a single Item identified by the Item id in the URI. AccessControl.Item Ids are not allowed.
	* If an AccessControl doesn't specify NotifyUser or NotifyMessage property their values are inherited from the corresponding
	* top-level properties.
	* The Principal can be identified by Id or Email (Users). If a User with the specified email does not exist it will be created.
	* Defaults for NotifyUser and Recursive are false.
	* See AccessControlsBulkParams for other details.
	* @param url 	 	
	* @param bulkParams 	 	
	* @return AccessControlBulkResult
	*/
	public ISFQuery<SFAccessControlBulkResult> bulkSet(URI url, SFAccessControlsBulkParams bulkParams) throws InvalidOrMissingParameterException 	{
		if (url == null) {
			throw new InvalidOrMissingParameterException("url");
		}
		if (bulkParams == null) {
			throw new InvalidOrMissingParameterException("bulkParams");
		}

		SFApiQuery<SFAccessControlBulkResult> sfApiQuery = new SFApiQuery<SFAccessControlBulkResult>(this.client);
		sfApiQuery.setFrom("Items");
		sfApiQuery.setAction("AccessControls");
		sfApiQuery.addIds(url);
		sfApiQuery.addSubAction("BulkSet");
		sfApiQuery.setBody(bulkParams);
		sfApiQuery.setHttpMethod("POST");
		return sfApiQuery;
	}

	/**
	* Update multiple access controls for a single principal
    * {
    * "NotifyUser":true,
    * "NotifyMessage":"msg",
    * 
    * "AccessControlParams":
    * [
    * {
    * "AccessControl":
    * {
    * "Item": { "Id": "item-id-1" },
    * "Principal" : { "Id":"existing_user_id" },
    * "CanUpload" : true,
    * "CanDownload" : false,
    * "CanView" : true
    * },
    * "NotifyUser":false
    * },
    * {
    * "AccessControl":
    * {
    * "Item": { "Id": "item-id-3" },
    * "Principal" : { "Id":"group_id" },
    * "CanUpload" : false,
    * "CanDownload" : true,
    * "CanView" : true
    * },
    * "Recursive":true
    * },
    * {
    * "AccessControl":
    * {
    * "Item": { "Id": "item-id-2" },
    * "Principal" : { "Email":"new_or_existing_user@a.com" },
    * "CanUpload" : false,
    * "CanDownload" : true,
    * "CanView" : true
    * }
    * }
    * ]
    * }
	* @param principalId 	 	
	* @param bulkParams 	 	
	* @return AccessControlBulkResult
	*/
	public ISFQuery<SFAccessControlBulkResult> bulkSetForPrincipal(SFAccessControlsBulkParams bulkParams, String principalId) throws InvalidOrMissingParameterException 	{
		if (bulkParams == null) {
			throw new InvalidOrMissingParameterException("bulkParams");
		}
		if (principalId == null) {
			throw new InvalidOrMissingParameterException("principalId");
		}

		SFApiQuery<SFAccessControlBulkResult> sfApiQuery = new SFApiQuery<SFAccessControlBulkResult>(this.client);
		sfApiQuery.setFrom("AccessControls");
		sfApiQuery.setAction("BulkSetForPrincipal");
		sfApiQuery.addQueryString("principalId", principalId);
		sfApiQuery.setBody(bulkParams);
		sfApiQuery.setHttpMethod("POST");
		return sfApiQuery;
	}

	/**
	* Recursively clones multiple access controls for multiple principals
    * {
    * "FolderId":"top-level-folder-id",
    * "PrincipalId":"source-user-or-group-id",
    * "ClonePrincipalIds":
    * [
    * "clone-user-or-group-id-1",
    * "clone-user-or-group-id-2",
    * "clone-user-or-group-id-3"
    * ]
    * }
	* @param accessControlsCloneParams 	 	
	* @return Cloned access controls
	*/
	public ISFQuery<SFAccessControlBulkResult> clone(SFAccessControlsCloneParams accessControlsCloneParams) throws InvalidOrMissingParameterException 	{
		if (accessControlsCloneParams == null) {
			throw new InvalidOrMissingParameterException("accessControlsCloneParams");
		}

		SFApiQuery<SFAccessControlBulkResult> sfApiQuery = new SFApiQuery<SFAccessControlBulkResult>(this.client);
		sfApiQuery.setFrom("AccessControls");
		sfApiQuery.setAction("Clone");
		sfApiQuery.setBody(accessControlsCloneParams);
		sfApiQuery.setHttpMethod("POST");
		return sfApiQuery;
	}

	/**
	* Delete multiple access controls
    * ["id1","id2",...]
	* @param folderUrl 	 	
	* @param principalIds 	 	
	*/
	public ISFQuery bulkDelete(URI folderUrl, ArrayList<String> principalIds) throws InvalidOrMissingParameterException 	{
		if (folderUrl == null) {
			throw new InvalidOrMissingParameterException("folderUrl");
		}
		if (principalIds == null) {
			throw new InvalidOrMissingParameterException("principalIds");
		}

		SFApiQuery sfApiQuery = new SFApiQuery(this.client);
		sfApiQuery.setFrom("Items");
		sfApiQuery.setAction("AccessControls");
		sfApiQuery.addIds(folderUrl);
		sfApiQuery.addSubAction("BulkDelete");
		sfApiQuery.setBody(principalIds);
		sfApiQuery.setHttpMethod("POST");
		return sfApiQuery;
	}

	/**
	* Delete multiple access controls for principal
    * ["id1","id2",...]
	* @param principalId 	 	
	* @param folderIds 	 	
	*/
	public ISFQuery bulkDeleteForPrincipal(ArrayList<String> folderIds, String principalId) throws InvalidOrMissingParameterException 	{
		if (folderIds == null) {
			throw new InvalidOrMissingParameterException("folderIds");
		}
		if (principalId == null) {
			throw new InvalidOrMissingParameterException("principalId");
		}

		SFApiQuery sfApiQuery = new SFApiQuery(this.client);
		sfApiQuery.setFrom("AccessControls");
		sfApiQuery.setAction("BulkDeleteForPrincipal");
		sfApiQuery.addQueryString("principalId", principalId);
		sfApiQuery.setBody(folderIds);
		sfApiQuery.setHttpMethod("POST");
		return sfApiQuery;
	}

	/**
	* Notify users that they have access to the parent folder
    * {
    * UserIds: ["id1", "id2"],
    * CustomMessage: "Message content goes here"
    * }
	* All users should have access to the parent folder
	* @param folderUrl 	 	
	* @param notifyUsersParams 	 	
	*/
	public ISFQuery notifyUsers(URI folderUrl, SFNotifyUsersParams notifyUsersParams) throws InvalidOrMissingParameterException 	{
		if (folderUrl == null) {
			throw new InvalidOrMissingParameterException("folderUrl");
		}
		if (notifyUsersParams == null) {
			throw new InvalidOrMissingParameterException("notifyUsersParams");
		}

		SFApiQuery sfApiQuery = new SFApiQuery(this.client);
		sfApiQuery.setFrom("Items");
		sfApiQuery.setAction("AccessControls");
		sfApiQuery.addIds(folderUrl);
		sfApiQuery.addSubAction("NotifyUsers");
		sfApiQuery.setBody(notifyUsersParams);
		sfApiQuery.setHttpMethod("POST");
		return sfApiQuery;
	}

}
