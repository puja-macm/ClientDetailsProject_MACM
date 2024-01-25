package com.example.Clientdemo.ClientDetails_test.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.Clientdemo.ClientDetails_test.service.ClientService;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

@Repository
public class ClientRepository {
	
	final Logger LOGGER=LoggerFactory.getLogger(ClientRepository.class);
	
	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	@Autowired
	private Environment env;
	

	public Map<String, String> getDataFromRedis(String id) {
		String redisKey = "macm.user.client." + id;
		HashOperations hashOperations = redisTemplate.opsForHash();
		Map<String, String> value = hashOperations.entries(redisKey);
		return value;
	}

	public Map<String, String> getDataFromMySQL(String id) throws SQLException {

		ResultSet rs = null;
		Map<String, String> mp = new HashMap<>();
		String url = env.getProperty("spring.datasource.url");
		String username = env.getProperty("spring.datasource.username");
		String password = env.getProperty("spring.datasource.password");
		try (Connection conn = DriverManager.getConnection(url, username, password)) {
			if (conn != null) {
				LOGGER.info("Connected to the database!");
				PreparedStatement ps = conn.prepareStatement("Select * from Client_Details where cl_code=?");
				ps.setString(1, id);
				rs = ps.executeQuery();
				while (rs.next()) {
				
					mp.put("cl_code", rs.getString("cl_code"));
					mp.put("name", rs.getString("long_name"));
					mp.put("email", rs.getString("email"));
					mp.put("mobile", rs.getString("mobile_pager"));

				}

			} else {
				System.out.println("Failed to make connection!");			
			}
		} catch (SQLException e) {
			System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
		}
		return mp;

	}

	public void storeDataInRedis(String id, Map<String, String> data) {

		HashOperations<String, String, String> hashOperations = redisTemplate.opsForHash();
		String redisKey = "macm.user.client." + id;
		hashOperations.putAll(redisKey, data);
	}

}
