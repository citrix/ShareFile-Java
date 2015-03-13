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

public interface ISFEntities {
    public static abstract class Implementation extends SFEntitiesBase implements ISFEntities {
        protected Implementation() {
    
        }

        @Override
        public SFConnectorGroupsEntity connectorGroups() {
            return getEntity(SFConnectorGroupsEntity.class);
        }
        @Override
        public SFFolderTemplatesEntity folderTemplates() {
            return getEntity(SFFolderTemplatesEntity.class);
        }
        @Override
        public SFAccessControlsEntity accessControls() {
            return getEntity(SFAccessControlsEntity.class);
        }
        @Override
        public SFAccountsEntity accounts() {
            return getEntity(SFAccountsEntity.class);
        }
        @Override
        public SFAsyncOperationsEntity asyncOperations() {
            return getEntity(SFAsyncOperationsEntity.class);
        }
        @Override
        public SFCapabilitiesEntity capabilities() {
            return getEntity(SFCapabilitiesEntity.class);
        }
        @Override
        public SFFavoriteFoldersEntity favoriteFolders() {
            return getEntity(SFFavoriteFoldersEntity.class);
        }
        @Override
        public SFGroupsEntity groups() {
            return getEntity(SFGroupsEntity.class);
        }
        @Override
        public SFItemsEntity items() {
            return getEntity(SFItemsEntity.class);
        }
        @Override
        public SFMetadataEntity metadata() {
            return getEntity(SFMetadataEntity.class);
        }
        @Override
        public SFSessionsEntity sessions() {
            return getEntity(SFSessionsEntity.class);
        }
        @Override
        public SFSharesEntity shares() {
            return getEntity(SFSharesEntity.class);
        }
        @Override
        public SFStorageCentersEntity storageCenters() {
            return getEntity(SFStorageCentersEntity.class);
        }
        @Override
        public SFUsersEntity users() {
            return getEntity(SFUsersEntity.class);
        }
        @Override
        public SFZonesEntity zones() {
            return getEntity(SFZonesEntity.class);
        }
    }

    SFConnectorGroupsEntity connectorGroups();
    SFFolderTemplatesEntity folderTemplates();
    SFAccessControlsEntity accessControls();
    SFAccountsEntity accounts();
    SFAsyncOperationsEntity asyncOperations();
    SFCapabilitiesEntity capabilities();
    SFFavoriteFoldersEntity favoriteFolders();
    SFGroupsEntity groups();
    SFItemsEntity items();
    SFMetadataEntity metadata();
    SFSessionsEntity sessions();
    SFSharesEntity shares();
    SFStorageCentersEntity storageCenters();
    SFUsersEntity users();
    SFZonesEntity zones();
}