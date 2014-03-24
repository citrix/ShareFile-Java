package com.sharefile.api.interfaces;

import com.sharefile.api.SFApiClient;
import com.sharefile.api.V3Error;
import com.sharefile.api.models.SFDownloadSpecification;

public interface SFApiDownloadProgressListener 
{
	public void bytesDownloaded(long byteCount,SFDownloadSpecification downloaSpec,SFApiClient client);
	public void downloadSuccess(long byteCount,SFDownloadSpecification downloaSpec,SFApiClient client);
	public void downloadFailure(V3Error v3error,long byteCount,SFDownloadSpecification downloaSpec,SFApiClient client);
}