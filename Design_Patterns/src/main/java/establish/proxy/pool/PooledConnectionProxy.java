package establish.proxy.pool;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Queue;

public class PooledConnectionProxy extends AbstractConnectionProxy {

	Connection target;
	Queue<PooledConnectionProxy> idleQueue;

	public PooledConnectionProxy(Queue<PooledConnectionProxy> idleQueue, Connection target) {
		this.idleQueue = idleQueue;
		this.target = target;
	}

	@Override
	public void close() throws SQLException {
		System.out.println("Fake close and released to idle queue for future reuse: " + target);
		idleQueue.offer(this);
	}

	@Override
	protected Connection getRealConnection() {
		return target;
	}
}
