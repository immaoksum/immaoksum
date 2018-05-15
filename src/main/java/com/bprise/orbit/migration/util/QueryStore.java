package com.bprise.orbit.migration.util;

public class QueryStore {

	public static final String SCHEDULE_STATUS_INSERT = "insert into module_schedule_status(id,schedule_name,updated_time,completed_time,schedule_status,module) values(uuid(),'Redis Migration',?,?,?,?)";
	public static final String CHANGE_LOG_QUERY = "select module_id from module_change_logs where module = ? and updated_time >= ? ";
	public static final String GET_ADS = "select * from ad where ad_id in ?";
	public static final String GET_LOCATIONS = "select * from location_mapping where location_group_id in ?";
	public static final String GET_CAMPAIGNS = "select * from campaign where campaign_id in ?";
	public static final String GET_AUDIENCE = "select * from audience where audience_rule_id in ?";
	public static final String CAMPAIGN_AD_MAP_QUERY = "select ad_id,campaign_id,is_deleted from campaign_id_ad_id_mapping  where campaign_id in ? ";
	public static final String CAMPAIGN_AUDIENCE_MAP_QUERY = "select audience_id,campaign_id,is_deleted from campaign_id_audience_id_mapping  where campaign_id in ? ";
	public static final String CAMPAIGN_LOCATION_MAP_QUERY = "select location_group_id ,campaign_id,is_deleted from campaign_id_location_group_id_mapping where campaign_id in ?";
	public static final String LOCATION_GEO_MAP_QUERY = "select location_group_id ,geo_type,is_deleted from location_group_id_geo_type_mapping where location_group_id in ?";
	public static final String GET_BEACONS = "select * from place_beacon_section_migration where  beacon_id in ?";
	public static final String GET_ADSPOT = "select * from ad_spot where id in ?";
	public static final String GET_VENDORS = "select * from vendors where id in ?";
	public static final String GET_PUBLISHER_WEBSITE = "select * from publisher_websites where id = ?";
	public static final String GET_VENDOR_APPLICATION = "select * from vendor_application where id in ?";
	public static final String GET_BANNERS = "select * from banner";
	public static final String GET_PLACE = "select * from place_details where id in ?";
	public static final String GET_MEDIUM = "select * from medium where id in ?";
	public static final String LAST_SYNC_CASSANDRA_QUERY = "select updated_time from cassandra_schedule_status where  module = ? and schedule_status='SUCCESS' limit 1";
	public static final String LAST_SYNC_TIME_QUERY = "select updated_time from module_schedule_status where schedule_name='Redis Migration' and schedule_status='Completed' and module= ? and updated_time <= ? limit 1";
	
	/**************** AUDIENCE CAMPAIGN ID MAPPED QUERY *************************/	
	public static final String GET_DATA_AUDIENCE_CAMPAIGN_ID_MAPPED="select * from audience_id_campaign_id_mapping where audience_id in ?";
	
	public static final String GET_DATA_CAMPAIGN_AUDIENCE_ID_MAPPED="select * from campaign_id_audience_id_mapping where campaign_id in ?";
	




}
