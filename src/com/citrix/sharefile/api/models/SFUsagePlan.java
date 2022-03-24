
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

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

public class SFUsagePlan extends SFODataObject {

	@SerializedName("Name")
	private String Name;
	@SerializedName("PlanTrack")
	private String PlanTrack;
	@SerializedName("Code")
	private String Code;
	@SerializedName("AddonTypeCode")
	private String AddonTypeCode;
	@SerializedName("ProductClass")
	private String ProductClass;
	@SerializedName("CitrixSKU")
	private String CitrixSKU;
	@SerializedName("MonthlyPrice")
	private Double MonthlyPrice;
	@SerializedName("QuarterlyPrice")
	private Double QuarterlyPrice;
	@SerializedName("YearlyPrice")
	private Double YearlyPrice;
	@SerializedName("BasePrice")
	private Double BasePrice;
	@SerializedName("BaseBandwidth")
	private Integer BaseBandwidth;
	@SerializedName("BaseDiskSpace")
	private Integer BaseDiskSpace;
	@SerializedName("BaseUsers")
	private Integer BaseUsers;
	@SerializedName("AdditionalBandwidthPrice")
	private Double AdditionalBandwidthPrice;
	@SerializedName("AdditionalDiskSpacePrice")
	private Double AdditionalDiskSpacePrice;
	@SerializedName("AdditionalUserPrice")
	private Double AdditionalUserPrice;
	@SerializedName("AppliedAddOns")
	private SFAppliedPlanAddOns AppliedAddOns;
	@SerializedName("AvailableAddOns")
	private ArrayList<SFUsagePlanAddOn> AvailableAddOns;
	@SerializedName("UserMax")
	private Integer UserMax;
	@SerializedName("BandwidthMax")
	private Integer BandwidthMax;
	@SerializedName("DiskSpaceMax")
	private Integer DiskSpaceMax;
	@SerializedName("BillingRate")
	private Double BillingRate;
	@SerializedName("BillingCycle")
	private String BillingCycle;
	@SerializedName("TaxQuote")
	private SFSalesTaxQuote TaxQuote;

		/**
		* Name of the plan.
		* This property maps to PlanName field of UsagePlan table
		*/
	public String getName() {
		return this.Name;
	}

		/**
		* Name of the plan.
		* This property maps to PlanName field of UsagePlan table
		*/
	public void setName(String name) {
		this.Name = name;
	}
		/**
		* Plan track of the usage plan.
		* This property maps to PlanTrack field of UsagePlan table
		*/
	public String getPlanTrack() {
		return this.PlanTrack;
	}

		/**
		* Plan track of the usage plan.
		* This property maps to PlanTrack field of UsagePlan table
		*/
	public void setPlanTrack(String plantrack) {
		this.PlanTrack = plantrack;
	}
		/**
		* Usage plan's code.
		* This property maps to Code field of UsagePlan table
		*/
	public String getCode() {
		return this.Code;
	}

		/**
		* Usage plan's code.
		* This property maps to Code field of UsagePlan table
		*/
	public void setCode(String code) {
		this.Code = code;
	}
		/**
		* Addon type code.
		* This property maps to AddonTypeCode field of UsagePlan table
		*/
	public String getAddonTypeCode() {
		return this.AddonTypeCode;
	}

		/**
		* Addon type code.
		* This property maps to AddonTypeCode field of UsagePlan table
		*/
	public void setAddonTypeCode(String addontypecode) {
		this.AddonTypeCode = addontypecode;
	}
		/**
		* The Product Class, for tax purposes
		*/
	public String getProductClass() {
		return this.ProductClass;
	}

		/**
		* The Product Class, for tax purposes
		*/
	public void setProductClass(String productclass) {
		this.ProductClass = productclass;
	}
		/**
		* Stock Keeping Unit for this plan
		*/
	public String getCitrixSKU() {
		return this.CitrixSKU;
	}

		/**
		* Stock Keeping Unit for this plan
		*/
	public void setCitrixSKU(String citrixsku) {
		this.CitrixSKU = citrixsku;
	}
		/**
		* Price of the plan if the billing cycle is Month.
		* This property maps to MonthlyPrice field of UsagePlan table
		*/
	public Double getMonthlyPrice() {
		return this.MonthlyPrice;
	}

		/**
		* Price of the plan if the billing cycle is Month.
		* This property maps to MonthlyPrice field of UsagePlan table
		*/
	public void setMonthlyPrice(Double monthlyprice) {
		this.MonthlyPrice = monthlyprice;
	}
		/**
		* Price of the plan if the billing cycle is Quarter.
		* This property maps to QuarterlyPrice field of UsagePlan table
		*/
	public Double getQuarterlyPrice() {
		return this.QuarterlyPrice;
	}

		/**
		* Price of the plan if the billing cycle is Quarter.
		* This property maps to QuarterlyPrice field of UsagePlan table
		*/
	public void setQuarterlyPrice(Double quarterlyprice) {
		this.QuarterlyPrice = quarterlyprice;
	}
		/**
		* Price of the plan if the billing cycle is Year.
		* This property maps to YearlyPrice field of UsagePlan table
		*/
	public Double getYearlyPrice() {
		return this.YearlyPrice;
	}

		/**
		* Price of the plan if the billing cycle is Year.
		* This property maps to YearlyPrice field of UsagePlan table
		*/
	public void setYearlyPrice(Double yearlyprice) {
		this.YearlyPrice = yearlyprice;
	}
		/**
		* Base price of the plan (not including add ons)
		*/
	public Double getBasePrice() {
		return this.BasePrice;
	}

		/**
		* Base price of the plan (not including add ons)
		*/
	public void setBasePrice(Double baseprice) {
		this.BasePrice = baseprice;
	}
		/**
		* Bandwidth included in the base plan (in megabytes)
		* This property maps to BaseBandwidth field of UsagePlan table
		*/
	public Integer getBaseBandwidth() {
		return this.BaseBandwidth;
	}

		/**
		* Bandwidth included in the base plan (in megabytes)
		* This property maps to BaseBandwidth field of UsagePlan table
		*/
	public void setBaseBandwidth(Integer basebandwidth) {
		this.BaseBandwidth = basebandwidth;
	}
		/**
		* Storage included in the base plan (in megabytes)
		* This property maps to BaseStorage field of UsagePlan table
		*/
	public Integer getBaseDiskSpace() {
		return this.BaseDiskSpace;
	}

		/**
		* Storage included in the base plan (in megabytes)
		* This property maps to BaseStorage field of UsagePlan table
		*/
	public void setBaseDiskSpace(Integer basediskspace) {
		this.BaseDiskSpace = basediskspace;
	}
		/**
		* Users included in plan
		* This property maps to EmployeeUsers field of UsagePlan table
		*/
	public Integer getBaseUsers() {
		return this.BaseUsers;
	}

		/**
		* Users included in plan
		* This property maps to EmployeeUsers field of UsagePlan table
		*/
	public void setBaseUsers(Integer baseusers) {
		this.BaseUsers = baseusers;
	}
		/**
		* Additional charge for bandwidth (price is given per gigabyte/month).
		*/
	public Double getAdditionalBandwidthPrice() {
		return this.AdditionalBandwidthPrice;
	}

		/**
		* Additional charge for bandwidth (price is given per gigabyte/month).
		*/
	public void setAdditionalBandwidthPrice(Double additionalbandwidthprice) {
		this.AdditionalBandwidthPrice = additionalbandwidthprice;
	}
		/**
		* Additional charge for storage (price is given per gigabyte/month)
		*/
	public Double getAdditionalDiskSpacePrice() {
		return this.AdditionalDiskSpacePrice;
	}

		/**
		* Additional charge for storage (price is given per gigabyte/month)
		*/
	public void setAdditionalDiskSpacePrice(Double additionaldiskspaceprice) {
		this.AdditionalDiskSpacePrice = additionaldiskspaceprice;
	}
		/**
		* Additional charge for users
		*/
	public Double getAdditionalUserPrice() {
		return this.AdditionalUserPrice;
	}

		/**
		* Additional charge for users
		*/
	public void setAdditionalUserPrice(Double additionaluserprice) {
		this.AdditionalUserPrice = additionaluserprice;
	}
		/**
		* Any addons applied for the plan (additional users, additional storage, and additional bandwidth)
		*/
	public SFAppliedPlanAddOns getAppliedAddOns() {
		return this.AppliedAddOns;
	}

		/**
		* Any addons applied for the plan (additional users, additional storage, and additional bandwidth)
		*/
	public void setAppliedAddOns(SFAppliedPlanAddOns appliedaddons) {
		this.AppliedAddOns = appliedaddons;
	}
		/**
		* List of available AddOns
		*/
	public ArrayList<SFUsagePlanAddOn> getAvailableAddOns() {
		return this.AvailableAddOns;
	}

		/**
		* List of available AddOns
		*/
	public void setAvailableAddOns(ArrayList<SFUsagePlanAddOn> availableaddons) {
		this.AvailableAddOns = availableaddons;
	}
		/**
		* The total number of users (base users + additional users)
		*/
	public Integer getUserMax() {
		return this.UserMax;
	}

		/**
		* The total number of users (base users + additional users)
		*/
	public void setUserMax(Integer usermax) {
		this.UserMax = usermax;
	}
		/**
		* Total bandwidth (base bandwidth + additional bandwidth)
		*/
	public Integer getBandwidthMax() {
		return this.BandwidthMax;
	}

		/**
		* Total bandwidth (base bandwidth + additional bandwidth)
		*/
	public void setBandwidthMax(Integer bandwidthmax) {
		this.BandwidthMax = bandwidthmax;
	}
		/**
		* Total diskspace (base diskspace + additional diskspace)
		*/
	public Integer getDiskSpaceMax() {
		return this.DiskSpaceMax;
	}

		/**
		* Total diskspace (base diskspace + additional diskspace)
		*/
	public void setDiskSpaceMax(Integer diskspacemax) {
		this.DiskSpaceMax = diskspacemax;
	}
		/**
		* Billing rate of the plan. This is the total of
		* base price + (additional users * additional user price)
		* + (additional bandwidth * additional bandwidth price)
		* + (additional diskspace * additional diskspace price)
		*/
	public Double getBillingRate() {
		return this.BillingRate;
	}

		/**
		* Billing rate of the plan. This is the total of
		* base price + (additional users * additional user price)
		* + (additional bandwidth * additional bandwidth price)
		* + (additional diskspace * additional diskspace price)
		*/
	public void setBillingRate(Double billingrate) {
		this.BillingRate = billingrate;
	}
		/**
		* Plan's billing cycle: Month, Quarter, or Year
		*/
	public String getBillingCycle() {
		return this.BillingCycle;
	}

		/**
		* Plan's billing cycle: Month, Quarter, or Year
		*/
	public void setBillingCycle(String billingcycle) {
		this.BillingCycle = billingcycle;
	}
		/**
		* Sales Tax quote for this plan. This property is only returned by the CalculateUsagePlanValues and GetUsagePlan endpoints
		*/
	public SFSalesTaxQuote getTaxQuote() {
		return this.TaxQuote;
	}

		/**
		* Sales Tax quote for this plan. This property is only returned by the CalculateUsagePlanValues and GetUsagePlan endpoints
		*/
	public void setTaxQuote(SFSalesTaxQuote taxquote) {
		this.TaxQuote = taxquote;
	}

}