
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

package com.sharefile.api.entities;

import java.util.ArrayList;

import com.sharefile.api.SFApiQuery;
import com.sharefile.api.enumerations.SFHttpMethod;
import com.sharefile.api.enumerations.SFTreeMode;
import com.sharefile.api.enumerations.SFUploadMethod;
import com.sharefile.api.enumerations.SFVRootType;
import com.sharefile.api.models.SFDownloadSpecification;
import com.sharefile.api.models.SFFolder;
import com.sharefile.api.models.SFItem;
import com.sharefile.api.models.SFItemInfo;
import com.sharefile.api.models.SFItemProtocolLink;
import com.sharefile.api.models.SFLink;
import com.sharefile.api.models.SFNote;
import com.sharefile.api.models.SFODataFeed;
import com.sharefile.api.models.SFODataObject;
import com.sharefile.api.models.SFSearchResults;
import com.sharefile.api.models.SFSymbolicLink;
import com.sharefile.api.models.SFUploadSpecification;


public class SFItemsEntity extends SFODataEntityBase
{
    /**
	* Get List of Items
	* Retrieve the initial folder and files of the authenticated user.
	* This method will return the user's root directory, using Item.GetRootFromWasabiPath("root").
	* It will force expansion of the Children element, so first level of items under the root is also returned
	* @return a user's root directory and first level of children items
    */
	public SFApiQuery<SFItem> get()
	{
		SFApiQuery<SFItem> query = new SFApiQuery<SFItem>();
		query.setFrom("Items");
		query.setHttpMethod(SFHttpMethod.GET);
		return query;
	}

    /**
	* Get List of Items
	* Retrieve the initial folder and files of the authenticated user.
	* This method will return the user's root directory, using Item.GetRootFromWasabiPath("root").
	* It will force expansion of the Children element, so first level of items under the root is also returned
	* @return a user's root directory and first level of children items
    */
	public SFApiQuery<SFItem> get(String id)
	{
		SFApiQuery<SFItem> query = new SFApiQuery<SFItem>();
		query.setFrom("Items");
		query.setId(id);
		query.setHttpMethod(SFHttpMethod.GET);
		return query;
	}

    /**
	* Get List of Items
	* Retrieve the initial folder and files of the authenticated user.
	* This method will return the user's root directory, using Item.GetRootFromWasabiPath("root").
	* It will force expansion of the Children element, so first level of items under the root is also returned
	* @return a user's root directory and first level of children items
    */
	public SFApiQuery<SFItem> get(String id, SFTreeMode treeMode, String sourceId, String rootId , SFVRootType rootType , Boolean canCreateRootFolder, Boolean fileBox)
	{
		SFApiQuery<SFItem> query = new SFApiQuery<SFItem>();
		query.setFrom("Items");
		query.setId(id);
		query.addQueryString("treeMode", treeMode);
		query.addQueryString("sourceId", sourceId);
		query.addQueryString("rootId", rootId);
		query.addQueryString("rootType", rootType);
		query.addQueryString("canCreateRootFolder", canCreateRootFolder);
		query.addQueryString("fileBox", fileBox);
		query.setHttpMethod(SFHttpMethod.GET);
		return query;
	}

    /**
	* Get Stream
	* Retrieves the versions of a given Stream.
	* An Item represents a single version of a file system object. The stream identifies
	* all versions of the same file system object. For example, when users upload or modify an existing file, a new Item
	* is created with the same StreamID. All default Item enumerations return only the latest version of a given stream.
	* Use this method to retrieve previous versions of a given stream
	* @param id 	
	* @param includeDeleted 	
    */
	public SFApiQuery<SFODataFeed<SFItem>> stream(String id, Boolean includeDeleted)
	{
		SFApiQuery<SFODataFeed<SFItem>> query = new SFApiQuery<SFODataFeed<SFItem>>();
		query.setFrom("Items");
		query.setAction("Stream");
		query.setId(id);
		query.addQueryString("includeDeleted", includeDeleted);
		query.setHttpMethod(SFHttpMethod.GET);
		return query;
	}

    /**
	* Get Item by Path
	* Retrieves an item from its path. The path is of format /foldername/foldername/filename
	* This call may redirect the client to another API provider, if the path
	* contains a symbolic link.
	* @param path 	
	* @return An item identified by a path
    */
	public SFApiQuery<SFItem> byPath(String path)
	{
		SFApiQuery<SFItem> query = new SFApiQuery<SFItem>();
		query.setFrom("Items");
		query.setAction("ByPath");
		query.addQueryString("path", path);
		query.setHttpMethod(SFHttpMethod.GET);
		return query;
	}

    /**
	* Get Item by Path
	* Retrieves an item from its path. The path is of format /foldername/foldername/filename
	* This call may redirect the client to another API provider, if the path
	* contains a symbolic link.
	* @param path 	
	* @return An item identified by a path
    */
	public SFApiQuery<SFItem> byPath( String id , String parentid, String path)
	{
		SFApiQuery<SFItem> query = new SFApiQuery<SFItem>();
		query.setFrom("Items");
		query.setAction("ByPath");
		query.setId(id);
		query.addQueryString("parentid", parentid);
		query.addQueryString("path", path);
		query.setHttpMethod(SFHttpMethod.GET);
		return query;
	}

    /**
	* Get Parent Item
	* Retrieves the Parent navigation property of a single Item.
	* @param id 	
	* @return the Parent Item of the give object ID.
    */
	public SFApiQuery<SFItem> getParent(String id)
	{
		SFApiQuery<SFItem> query = new SFApiQuery<SFItem>();
		query.setFrom("Items");
		query.setAction("Parent");
		query.setId(id);
		query.setHttpMethod(SFHttpMethod.GET);
		return query;
	}

    /**
	* Get Children
	* Handler for the Children navigation property of a given Item.
	* A 302 redirection is returned if the folder is a SymbolicLink. The redirection
	* will enumerate the children of the remote connector
	* @param id 	
	* @return the list of children under the given object ID
    */
	public SFApiQuery<SFItem> getChildren(String id)
	{
		SFApiQuery<SFItem> query = new SFApiQuery<SFItem>();
		query.setFrom("Items");
		query.setAction("Children");
		query.setId(id);
		query.setHttpMethod(SFHttpMethod.GET);
		return query;
	}

    /**
	* Get Folder Access Info
	* Returns the effective Access Controls for the current authenticated user for the
	* selected folder - i.e., the resulting set of Access Controls for the Item/User context.This operation applies to Folders only, will return an error for other Item types.
	* @param id 	
	* @return The Folder Access Control Information
    */
	public SFApiQuery<SFItemInfo> getInfo(String id)
	{
		SFApiQuery<SFItemInfo> query = new SFApiQuery<SFItemInfo>();
		query.setFrom("Items");
		query.setAction("Info");
		query.setId(id);
		query.setHttpMethod(SFHttpMethod.GET);
		return query;
	}

    /**
	* Download Item Content
	* Initiate the download operation for an item. It will return 302 redirection to the
	* actual download link. For Folders, the download link will retrieve a ZIP archive
	* with the contents of the Folder.
	* @param id 	
	* @param redirect 	
	* @return the download link for the provided item content.
    */
	public SFApiQuery<SFDownloadSpecification> download(String id, Boolean redirect )
	{
		SFApiQuery<SFDownloadSpecification> query = new SFApiQuery<SFDownloadSpecification>();
		query.setFrom("Items");
		query.setAction("Download");
		query.setId(id);
		query.addQueryString("redirect", redirect);
		query.setHttpMethod(SFHttpMethod.GET);
		return query;
	}

    /**
	* Create Folder
    * {
    * "Name":"Folder Name",
    * "Description":"Description",
    * "Zone":{ "Id":"z014766e-8e96-4615-86aa-57132a69843c" }
    * }
	* Creates a new Folder.
	* The POST body must contain the serialized object.
	* For top-level folders, use Items/Folder.
	* The Zone object may only be provided for top-level folders. The Zone object must
	* contain a zone ID.
	* @param parentid 	
	* @param folder 	
	* @param overwrite 	
	* @param passthrough 	
	* @return the new Folder
    */
	public SFApiQuery<SFItem> createFolder(String parentid, SFFolder folder, Boolean overwrite , Boolean passthrough )
	{
		SFApiQuery<SFItem> query = new SFApiQuery<SFItem>();
		query.setFrom("Items");
		query.setAction("Folder");
		query.setId(parentid);
		query.addQueryString("overwrite", overwrite);
		query.addQueryString("passthrough", passthrough);
		query.setBody(folder);
		query.setHttpMethod(SFHttpMethod.GET);
		return query;
	}

    /**
	* Create Note
    * {
    * "Name":"Note Name",
    * "Description":"Description"
    * }
	* Creates a new Note.
	* @param parentid 	
	* @param note 	
	* @return the new Note
    */
	public SFApiQuery<SFItem> createNote(String parentid, SFNote note)
	{
		SFApiQuery<SFItem> query = new SFApiQuery<SFItem>();
		query.setFrom("Items");
		query.setAction("Note");
		query.setId(parentid);
		query.setBody(note);
		query.setHttpMethod(SFHttpMethod.GET);
		return query;
	}

    /**
	* Create Link
    * {
    * "Name":"Link Name",
    * "Description":"Description",
    * "Uri":"https://server/path"
    * }
	* Creates a new Link
	* @param parentid 	
	* @param link 	
	* @return the new Link
    */
	public SFApiQuery<SFItem> createLink(String parentid, SFLink link)
	{
		SFApiQuery<SFItem> query = new SFApiQuery<SFItem>();
		query.setFrom("Items");
		query.setAction("Link");
		query.setId(parentid);
		query.setBody(link);
		query.setHttpMethod(SFHttpMethod.GET);
		return query;
	}

    /**
	* Create SymbolicLink
    * {
    * "Name":"Connection Name",
    * "Description":"Description",
    * "Link":"https://server/path"
    * }
    * {
    * "FileName":"RemoteFileName",
    * "Description":"Description",
    * "Zone":{ "Id":"z014766e-8e96-4615-86aa-57132a69843c" }
    * }
	* Creates a Symbolic Link
	* The body must contain either a "Link" parameter with a fully qualified URI; or use
	* FileName + Zone to have sharefile.com attempt to convert the Connector path to an
	* URI using a call to the Zone - using ShareFile Hash authentication mode. For active
	* clients, it's recommended to make the convertion call to the Zone directly, using
	* Items/ByPath=name, retriving the resulting URL, and calling this method with the
	* Link parameter.SymbolicLinks must be created as top-level folders
	* @param parentid 	
	* @param symlink 	
	* @param overwrite 	
	* @return the new SymbolicLink
    */
	public SFApiQuery<SFSymbolicLink> createSymbolicLink(String parentid, SFSymbolicLink symlink, Boolean overwrite)
	{
		SFApiQuery<SFSymbolicLink> query = new SFApiQuery<SFSymbolicLink>();
		query.setFrom("Items");
		query.setAction("SymbolicLink");
		query.setId(parentid);
		query.addQueryString("overwrite", overwrite);
		query.setBody(symlink);
		query.setHttpMethod(SFHttpMethod.GET);
		return query;
	}

    /**
	* Update Item
    * {
    * "Name":"Name",
    * "FileName":"FileName",
    * "Description":"Description",
    * "ExpirationDate": "date",
    * "Parent": { "Id": "parentid" },
    * "Zone": { "Id": "zoneid" }
    * }
	* Updates an Item object
	* @param id 	
	* @param item 	
	* @return A modified Item object. If the item Zone or Parent Zone is modified, then this method will return an Asynchronous operation record instead. Note: the parameters listed in the body of the request are the only parameters that can be updated through this call.
    */
	public SFApiQuery<SFODataObject> update(String id, SFItem item)
	{
		SFApiQuery<SFODataObject> query = new SFApiQuery<SFODataObject>();
		query.setFrom("Items");
		query.setId(id);
		query.setBody(item);
		query.setHttpMethod(SFHttpMethod.PATCH);
		return query;
	}

    /**
	* Update Link
    * {
    * "Name":"Name",
    * "Uri":"https://server/path",
    * "Description":"Description",
    * "Parent": { "Id": "parentid" },
    * }
	* Updates a Link object
	* @param id 	
	* @param link 	
	* @param notify 	
	* @return A modified Link object
    */
	public SFApiQuery<SFItem> updateLink(String id, SFLink link, Boolean notify)
	{
		SFApiQuery<SFItem> query = new SFApiQuery<SFItem>();
		query.setFrom("Items");
		query.setAction("Link");
		query.setId(id);
		query.addQueryString("notify", notify);
		query.setBody(link);
		query.setHttpMethod(SFHttpMethod.PATCH);
		return query;
	}

    /**
	* Update Note
    * {
    * "Name":"Name",
    * "Description":"Description",
    * "Parent": { "Id": "parentid" },
    * }
	* Updates a Note object
	* @param id 	
	* @param note 	
	* @param notify 	
	* @return The modified Note object
    */
	public SFApiQuery<SFItem> updateNote(String id, SFNote note, Boolean notify)
	{
		SFApiQuery<SFItem> query = new SFApiQuery<SFItem>();
		query.setFrom("Items");
		query.setAction("Note");
		query.setId(id);
		query.addQueryString("notify", notify);
		query.setBody(note);
		query.setHttpMethod(SFHttpMethod.PATCH);
		return query;
	}

    /**
	* Update SymbolicLink
    * {
    * "Name":"Name",
    * "Description":"Description",
    * "Link": "https://server/path"
    * }
	* Updates a Symbolic Link object
	* @param id 	
	* @param symlink 	
	* @return The modified SymbolicLink object
    */
	public SFApiQuery<SFSymbolicLink> updateSymbolicLink(String id, SFSymbolicLink symlink)
	{
		SFApiQuery<SFSymbolicLink> query = new SFApiQuery<SFSymbolicLink>();
		query.setFrom("Items");
		query.setAction("SymbolicLink");
		query.setId(id);
		query.setBody(symlink);
		query.setHttpMethod(SFHttpMethod.PATCH);
		return query;
	}

	public SFApiQuery delete(String id, Boolean singleversion, Boolean forceSync)
	{
		SFApiQuery query = new SFApiQuery();
		query.setFrom("Items");
		query.setId(id);
		query.addQueryString("singleversion", singleversion);
		query.addQueryString("forceSync", forceSync);
		query.setHttpMethod(SFHttpMethod.PATCH);
		return query;
	}

    /**
	* Delete Multiple Items
    * ["id1","id2",...]
	* All items in bulk delete must be children of the same parent, identified in the URI
	* @param id 	
	* @param body 	
    */
	public SFApiQuery bulkDelete(String parentid, ArrayList<String> ids, Boolean forceSync)
	{
		SFApiQuery query = new SFApiQuery();
		query.setFrom("Items");
		query.setAction("BulkDelete");
		query.setId(parentid);
		query.addQueryString("ids", ids);
		query.addQueryString("forceSync", forceSync);
		query.setHttpMethod(SFHttpMethod.POST);
		return query;
	}

    /**
	* Get Thumbnail
	* Retrieve a thumbnail link from the specified Item.
	* @param id 	
	* @param size 	
	* @return A 302 redirection to the Thumbnail link
    */
	public SFApiQuery<SFStream> getThumbnail(String id, Integer size)
	{
		SFApiQuery<SFStream> query = new SFApiQuery<SFStream>();
		query.setFrom("Items");
		query.setAction("Thumbnail");
		query.setId(id);
		query.addQueryString("size", size);
		query.setHttpMethod(SFHttpMethod.GET);
		return query;
	}

    /**
	* Get Breadcrumbs
	* Retrieves the path from an item from the root. The return list is a Feed of Items, with the top-level
	* folder at the first position. If this item is in a Connection path, the breadcrumbs may contain URL
	* reference back to the parent account - and the Item in the feed will contain just the URL reference.
	* @param id 	
	* @param path 	
	* @param vroot 	
	* @return A feed containing the path of folders from the specified root to the item, in order
    */
	public SFApiQuery<SFODataFeed<SFItem>> getBreadcrumbs(String id, String path , SFVRootType vroot)
	{
		SFApiQuery<SFODataFeed<SFItem>> query = new SFApiQuery<SFODataFeed<SFItem>>();
		query.setFrom("Items");
		query.setAction("Breadcrumbs");
		query.setId(id);
		query.addQueryString("path", path);
		query.addQueryString("vroot", vroot);
		query.setHttpMethod(SFHttpMethod.GET);
		return query;
	}

    /**
	* Copy Item
	* Copies an item to a new target Folder. If the target folder is in another zone, the operation will
	* return an AsyncOperation record instead. Clients may query the /AsyncOperation Entity to determine
	* operation progress and result.
	* @param id 	
	* @param targetid 	
	* @param overwrite 	
	* @return the modified source object
    */
	public SFApiQuery<SFItem> copy(String id, String targetid, Boolean overwrite )
	{
		SFApiQuery<SFItem> query = new SFApiQuery<SFItem>();
		query.setFrom("Items");
		query.setAction("Copy");
		query.setId(id);
		query.addQueryString("targetid", targetid);
		query.addQueryString("overwrite", overwrite);
		query.setHttpMethod(SFHttpMethod.GET);
		return query;
	}

    /**
	* Upload File
	* Prepares the links for uploading files to the target Folder.
	* This method returns an Upload Specification object. The fields are
	* populated based on the upload method, provider, and resume parameters passed to the
	* upload call.
	* The Method determines how the URLs must be called.
	* 
	* Standard uploads use a single HTTP POST message to the ChunkUri address provided in
	* the response. All other fields will be empty. Standard uploads do not support Resume.
	* 
	* Streamed uploads use multiple HTTP POST calls to the ChunkUri address. The client must
	* append the parameters index, offset and hash to the end of the ChunkUri address. Index
	* is a sequential number representing the data block (zero-based); Offset represents the
	* byte offset for the block; and hash contains the MD5 hash of the block. The last HTTP
	* POST must also contain finish=true parameter.
	* 
	* Threaded uploads use multiple HTTP POST calls to ChunkUri, and can have a number of
	* threads issuing blocks in parallel. Clients must append index, offset and hash to
	* the end of ChunkUri, as explained in Streamed. After all chunks were sent, the client
	* must call the FinishUri provided in this spec.
	* 
	* For all uploaders, the contents of the POST Body can either be "raw", if the "Raw" parameter
	* was provided to the Uploader, or use MIME multi-part form encoding otherwise. Raw uploads
	* simply put the block content in the POST body - Content-Length specifies the size. Multi-part
	* form encoding has to pass the File as a Form parameter named "File1".
	* 
	* For streamed and threaded, if Resume options were provided to the Upload call, then the
	* fields IsResume, ResumeIndex, ResumeOffset and ResumeFileHash MAY be populated. If they are,
	* it indicates that the server has identified a partial upload with that specification, and is
	* ready to resume on the provided parameters. The clients can then verify the ResumeFileHash to
	* ensure the file has not been modified; and continue issuing ChunkUri calls from the ResumeIndex
	* ResumeOffset parameters. If the client decides to restart, it should simply ignore the resume
	* parameters and send the blocks from Index 0.
	* 
	* For all uploaders: the result code for the HTTP POST calls to Chunk and Finish Uri can either
	* be a 401 - indicating authentication is required; 4xx/5xx indicating some kind of error; or
	* 200 with a Content Body of format 'ERROR:message'. Successful calls will return either a 200
	* response with no Body, or with Body of format 'OK'.
	* @param id 	
	* @param method 	
	* @param raw 	
	* @param fileName 	
	* @param fileSize 	
	* @param batchId 	
	* @param batchLast 	
	* @param canResume 	
	* @param startOver 	
	* @param unzip 	
	* @param tool 	
	* @param overwrite 	
	* @param title 	
	* @param details 	
	* @param isSend 	
	* @param sendGuid 	
	* @param opid 	
	* @param threadCount 	
	* @param responseFormat 	
	* @param notify 	
	* @return an Upload Specification element, containing the links for uploading, and the parameters for resume. The caller must know the protocol for sending the prepare, chunk and finish URLs returned in the spec; as well as negotiate the resume upload.
    */
	public SFApiQuery<SFUploadSpecification> upload(String id, SFUploadMethod method , Boolean raw , String fileName , Long fileSize , String batchId , Boolean batchLast , Boolean canResume , Boolean startOver , Boolean unzip , String tool , Boolean overwrite , String title , String details , Boolean isSend , String sendGuid , String opid , Integer threadCount, String responseFormat , Boolean notify , Integer expirationDays )
	{
		SFApiQuery<SFUploadSpecification> query = new SFApiQuery<SFUploadSpecification>();
		query.setFrom("Items");
		query.setAction("Upload");
		query.setId(id);
		query.addQueryString("method", method);
		query.addQueryString("raw", raw);
		query.addQueryString("fileName", fileName);
		query.addQueryString("fileSize", fileSize);
		query.addQueryString("batchId", batchId);
		query.addQueryString("batchLast", batchLast);
		query.addQueryString("canResume", canResume);
		query.addQueryString("startOver", startOver);
		query.addQueryString("unzip", unzip);
		query.addQueryString("tool", tool);
		query.addQueryString("overwrite", overwrite);
		query.addQueryString("title", title);
		query.addQueryString("details", details);
		query.addQueryString("isSend", isSend);
		query.addQueryString("sendGuid", sendGuid);
		query.addQueryString("opid", opid);
		query.addQueryString("threadCount", threadCount);
		query.addQueryString("responseFormat", responseFormat);
		query.addQueryString("notify", notify);
		query.addQueryString("expirationDays", expirationDays);
		query.setHttpMethod(SFHttpMethod.POST);
		return query;
	}

    /**
	* Unlock File
	* Unlock a locked file.
	* This operation is only implemented in Sharepoint providers (/sp)
	* @param id 	
	* @param message 	
    */
	public SFApiQuery checkIn(String id, String message)
	{
		SFApiQuery query = new SFApiQuery();
		query.setFrom("Items");
		query.setAction("CheckIn");
		query.setId(id);
		query.addQueryString("message", message);
		query.setHttpMethod(SFHttpMethod.POST);
		return query;
	}

    /**
	* Lock File
	* Locks a file.
	* This operation is only implemented in Sharepoint providers (/sp)
	* @param id 	
    */
	public SFApiQuery checkOut(String id)
	{
		SFApiQuery query = new SFApiQuery();
		query.setFrom("Items");
		query.setAction("CheckOut");
		query.setId(id);
		query.setHttpMethod(SFHttpMethod.POST);
		return query;
	}

    /**
	* Search
	* Search for Items matching the criteria of the query parameter
	* @param query 	
	* @return SearchResults
    */
	public SFApiQuery<SFSearchResults> search(String querystring)
	{
		SFApiQuery<SFSearchResults> query = new SFApiQuery<SFSearchResults>();
		query.setFrom("Items");
		query.setAction("Search");
		query.addQueryString("query", querystring);
		query.setHttpMethod(SFHttpMethod.GET);
		return query;
	}

    /**
	* Get all Item Protocol Link
	* This method returns all alternate protocol links supported by ShareFile (such
	* as WOPI, FTP, WebDAV).
	* @param id 	
	* @return A Feed containing all protocols links supported by the given item
    */
	public SFApiQuery<SFODataFeed<SFItemProtocolLink>> getProtocolLinks(String parentid)
	{
		SFApiQuery<SFODataFeed<SFItemProtocolLink>> query = new SFApiQuery<SFODataFeed<SFItemProtocolLink>>();
		query.setFrom("Items");
		query.setAction("ProtocolLinks");
		query.setId(parentid);
		query.setHttpMethod(SFHttpMethod.GET);
		return query;
	}

    /**
	* Get all Item Protocol Link
	* This method returns all alternate protocol links supported by ShareFile (such
	* as WOPI, FTP, WebDAV).
	* @param id 	
	* @return A Feed containing all protocols links supported by the given item
    */
	public SFApiQuery<SFItemProtocolLink> getProtocolLinks(String parentid, String id)
	{
		SFApiQuery<SFItemProtocolLink> query = new SFApiQuery<SFItemProtocolLink>();
		query.setFrom("Items");
		query.setAction("ProtocolLinks");
		query.setId(parentid);
		query.addActionIds(id);
		query.setHttpMethod(SFHttpMethod.GET);
		return query;
	}

}

