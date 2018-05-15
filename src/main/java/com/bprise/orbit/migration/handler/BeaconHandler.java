package com.bprise.orbit.migration.handler;

import java.util.List;

import org.springframework.stereotype.Component;

import com.bprise.orbit.migration.util.QueryStore;
import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.UDTValue;

import redis.clients.jedis.Jedis;

@Component
public class BeaconHandler implements Handler {

	@Override
	public void update(List<Long> ids, Session session, Jedis jedis) {
		ids.forEach(p -> {
			jedis.keys("bea_" + p + "*").forEach(t -> {
				jedis.del(t);
			});
			;
		});
		PreparedStatement prepared = session.prepare(QueryStore.GET_BEACONS);
		BoundStatement bound = prepared.bind(ids);
		ResultSet rs = session.execute(bound);
		List<Row> rows = rs.all();
		for (Row row : rows) {
			createBeaconRecord(row, jedis);
		}
	}
	
	/*private void createBeaconRecord(Row row,Jedis jedis){
		List<UDTValue> list = row.getList("section", UDTValue.class);
		for(UDTValue value : list){
		if(row.getString("ap_mac") != null){
//			jedis.set("bea_"+row.getLong("beacon_id")+":"+ row.getString("ap_mac")+":sec_"+value.getLong("section_id"), row.getString("zonalname"));
			jedis.set("bea_"+row.getLong("beacon_id")+":"+ row.getString("ap_mac")+":sec_"+value.getString("section_name").replaceAll(" ", "").toLowerCase(), row.getString("zonalname"));
			jedis.set("bea_"+row.getLong("beacon_id")+":"+ row.getString("ap_mac")+":place_"+row.getLong("place_id"), row.getString("zonalname"));
		}
		if(row.getString("e_uuid") != null){
//			jedis.set("bea_"+row.getLong("beacon_id")+":"+ row.getString("e_uuid")+":sec_"+value.getLong("section_id"), row.getString("zonalname"));
			jedis.set("bea_"+row.getLong("beacon_id")+":"+ row.getString("e_uuid")+":sec_"+value.getString("section_name").replaceAll(" ", "").toLowerCase(), row.getString("zonalname"));
			jedis.set("bea_"+row.getLong("beacon_id")+":"+ row.getString("e_uuid")+":place_"+row.getLong("place_id"), row.getString("zonalname"));
		}
		if(row.getString("i_uuid") != null){
//			jedis.set("bea_"+row.getLong("beacon_id")+":"+ row.getString("i_uuid")+row.getLong("major")+row.getLong("minor")+":sec_"+value.getLong("section_id"), row.getString("zonalname"));
			jedis.set("bea_"+row.getLong("beacon_id")+":"+ row.getString("i_uuid")+row.getLong("major")+row.getLong("minor")+":sec_"+value.getString("section_name").replaceAll(" ", "").toLowerCase(), row.getString("zonalname"));
			jedis.set("bea_"+row.getLong("beacon_id")+":"+ row.getString("i_uuid")+row.getLong("major")+row.getLong("minor")+":place_"+row.getLong("place_id"), row.getString("zonalname"));
//			jedis.set("bea_"+row.getLong("beacon_id")+":"+ row.getString("i_uuid").replaceAll("-", "")+row.getLong("major")+row.getLong("minor")+":sec_"+value.getLong("section_id"), row.getString("zonalname"));
			jedis.set("bea_"+row.getLong("beacon_id")+":"+ row.getString("i_uuid").replaceAll("-", "")+row.getLong("major")+row.getLong("minor")+":sec_"+value.getString("section_name").replaceAll(" ", "").toLowerCase(), row.getString("zonalname"));
			jedis.set("bea_"+row.getLong("beacon_id")+":"+ row.getString("i_uuid").replaceAll("-", "")+row.getLong("major")+row.getLong("minor")+":place_"+row.getLong("place_id"), row.getString("zonalname"));
		}
			jedis.set("section_"+value.getLong("section_id"), value.getString("section_name"));
			jedis.set("section_"+value.getString("section_name").replaceAll(" ", "").toLowerCase(), String.valueOf(value.getLong("section_id")));
		}
//		jedis.set("section_"+row.getLong("section_id"), row.getString("section_name"));
		jedis.set("bea_"+row.getLong("beacon_id"), row.getString("beacon_name"));
		jedis.set("map_beauid_"+row.getString("device_unique_id")+":bea_"+row.getLong("beacon_id"), "");
	}
*/
	
	private void createBeaconRecord(Row row,Jedis jedis){
		List<UDTValue> list = row.getList("section", UDTValue.class);
		if(row.getString("ap_mac") != null){
			jedis.set("bea_"+row.getString("ap_mac"),String.valueOf(row.getLong("beacon_id")));
		}
		if(row.getString("e_uuid") != null){
			jedis.set("bea_"+row.getString("e_uuid"),String.valueOf(row.getLong("beacon_id")));
		}
		if(row.getString("i_uuid") != null){
			jedis.set("bea_"+row.getString("i_uuid")+row.getLong("major")+row.getLong("minor"),String.valueOf(row.getLong("beacon_id")));
		}
		jedis.set("bea_"+row.getLong("beacon_id"), row.getString("beacon_name"));
		jedis.set("bea_"+row.getLong("beacon_id")+":place", "place_"+String.valueOf(row.getLong("place_id")));
		for(UDTValue value : list){
			jedis.sadd("bea_"+row.getLong("beacon_id")+":sec", "sec_"+value.getString("section_name").replaceAll(" ", "").toLowerCase());
		}
	}
}
