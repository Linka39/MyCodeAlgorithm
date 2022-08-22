package establish.proxy;

import establish.proxy.pool.LazyDataSource;
import establish.proxy.pool.PooledDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.sql.DataSource;


/**
 * App entry for Maven project.
 *
 * @author liaoxuefeng
 */
public class Main {

	public static void main(String[] args) throws Exception {
		DataSource lazyDataSource = new LazyDataSource(jdbcUrl, jdbcUsername, jdbcPassword);
		System.out.println("get lazy connection...");
		try (Connection conn1 = lazyDataSource.getConnection()) {
			// 并没有实际打开真正的Connection
		}
		System.out.println("get lazy connection...");
		try (Connection conn2 = lazyDataSource.getConnection()) {
			try (PreparedStatement ps = conn2.prepareStatement("SELECT * FROM students")) { // 打开了真正的Connection
				try (ResultSet rs = ps.executeQuery()) {
					while (rs.next()) {
						System.out.println(rs.getString("name"));
					}
				}
			}
		}
		DataSource pooledDataSource = new PooledDataSource(jdbcUrl, jdbcUsername, jdbcPassword);
		try (Connection conn = pooledDataSource.getConnection()) {
		}
		try (Connection conn = pooledDataSource.getConnection()) {
			// 获取到的是同一个Connection
		}
		try (Connection conn = pooledDataSource.getConnection()) {
			// 获取到的是同一个Connection
		}
	}

	static final String jdbcUrl = "jdbc:mysql://localhost/learnjdbc?useSSL=false&characterEncoding=utf8";
	static final String jdbcUsername = "learn";
	static final String jdbcPassword = "learnpassword";
}
