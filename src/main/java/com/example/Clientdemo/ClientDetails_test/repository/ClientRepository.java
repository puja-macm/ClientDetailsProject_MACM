package com.example.Clientdemo.ClientDetails_test.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import com.example.Clientdemo.ClientDetails_test.AppConstant;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

@Repository
public class ClientRepository {

	final Logger LOGGER = LoggerFactory.getLogger(ClientRepository.class);

	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	@Autowired
	private Environment env;

	public Map<String, String> getDataFromRedis(String id) {
		String redisKey = "macm.user.client." + id;
		HashOperations hashOperations = redisTemplate.opsForHash();
		Map<String, String> value = hashOperations.entries(redisKey);
		Map<String, String> finalValue = new HashMap<>();
		for (Map.Entry<String, String> entry : value.entrySet()) {
			finalValue.put(AppConstant.CL_CODE, value.get("cl_code"));
			finalValue.put(AppConstant.NAME, value.get("name"));
			finalValue.put(AppConstant.EMAIL, value.get("email"));
			finalValue.put(AppConstant.MOBILE_NO, value.get("mobile"));
			finalValue.put(AppConstant.PAN_NO, value.get("pan_gir_no"));
			finalValue.put(AppConstant.IP_PARTNER_CODE, value.get("sub_broker"));
			

		}
		System.out.println("values are:::" + value);
		return finalValue;
	}

	public Map<String, String> getDataFromMySQL(String id) throws SQLException {

		ResultSet rs = null;
		Map<String, String> clientMap = new HashMap<>();
		try (Connection conn = DriverManager.getConnection(env.getProperty("spring.datasource.url"),
				env.getProperty("spring.datasource.username"), env.getProperty("spring.datasource.password"))) {
			if (conn != null) {
				LOGGER.info("Connected to the database!");
				PreparedStatement ps = conn.prepareCall("{call usp_GetClientDetailByClientCodeAPI(?)}");
				ps.setString(1, id);
				rs = ps.executeQuery();
				while (rs.next()) {

					clientMap.put("cl_code", rs.getString("cl_code"));
					clientMap.put("name", rs.getString("long_name"));
					clientMap.put("email", rs.getString("email"));
					clientMap.put("mobile", rs.getString("mobile_pager"));
					clientMap.put("pan_gir_no", rs.getString("pan_gir_no"));
					clientMap.put("sub_broker", rs.getString("sub_broker"));
				

				}

			}

		} catch (SQLException e) {
			System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
		}
		return clientMap;

	}

	public void storeDataInRedis(String id, Map<String, String> data) {

		HashOperations<String, String, String> hashOperations = redisTemplate.opsForHash();
		String redisKey = "macm.user.client." + id;
		hashOperations.putAll(redisKey, data);
		System.out.println("data after storing in redis::::" + hashOperations);
	}

	public Map<String, String> getDataFromPartnerProcedure(String id) {
		ResultSet rs = null;
		Map<String, String> partnerMap = new HashMap<>();
		try (Connection conn = DriverManager.getConnection(env.getProperty("spring.datasource.url"),
				env.getProperty("spring.datasource.username"), env.getProperty("spring.datasource.password"))) {
			if (conn != null) {
				LOGGER.info("Connected to the database!");
				PreparedStatement ps = conn.prepareCall("{call Usp_GetPartnerDetailsfromPartnerCodeAPI(?)}");
				ps.setString(1, id);
				rs = ps.executeQuery();
				while (rs.next()) {

					partnerMap.put(AppConstant.IP_PARTNER_CODE, rs.getString("IP_PARTNER_CODE"));
					partnerMap.put(AppConstant.IP_EMAIL, rs.getString("IP_EMAIL"));
					partnerMap.put(AppConstant.IP_MOBILE_NUMBER, rs.getString("IP_MOBILE_NUMBER"));
					partnerMap.put(AppConstant.IP_NAME, rs.getString("IP_NAME"));

				}

			}
		} catch (SQLException e) {
			System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
		}
		return partnerMap;
	}

	public Map<String, String> getDataFromRedisForPartner(String id) {
		String redisKey = "macm.user.client." + id;
		HashOperations hashOperations = redisTemplate.opsForHash();
		Map<String, String> value = hashOperations.entries(redisKey);
		Map<String, String> finalValue = new HashMap<>();
		for (Map.Entry<String, String> entry : value.entrySet()) {

			finalValue.put(AppConstant.IP_PARTNER_CODE, value.get("partnerCode"));
			finalValue.put(AppConstant.IP_EMAIL, value.get("partnerEmail"));
			finalValue.put(AppConstant.IP_MOBILE_NUMBER, value.get("partnerMobileNumber"));
			finalValue.put(AppConstant.IP_NAME, value.get("partnerName"));

		}
		System.out.println("values are:::" + value);
		return finalValue;
	}

	public void storeDataInRedisForPartner(String id, Map<String, String> partnerData) {
		HashOperations<String, String, String> hashOperations = redisTemplate.opsForHash();
		String redisKey = "macm.user.client." + id;
		hashOperations.putAll(redisKey, partnerData);
		System.out.println("data after storing in redis::::" + hashOperations);

	}
}
