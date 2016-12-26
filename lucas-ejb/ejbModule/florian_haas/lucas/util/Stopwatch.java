package florian_haas.lucas.util;

public class Stopwatch {

	private long startTime;
	private long tmpStartTime;
	private long duration;
	private long tmpDuration;

	private boolean running;

	public Stopwatch() {
		this.reset();
	}

	public void start() {
		if (!this.running) {
			this.startTime = System.currentTimeMillis();
			this.tmpStartTime = System.currentTimeMillis();
			this.tmpDuration = 0;
			this.running = true;
		}
	}

	public void stop() {
		if (this.running) {
			this.duration += System.currentTimeMillis() - this.startTime;
			this.tmpDuration += System.currentTimeMillis() - this.tmpStartTime;
			this.running = false;
		}
	}

	public void reset() {
		this.startTime = System.currentTimeMillis();
		this.duration = 0;
		this.tmpDuration = 0;
		this.tmpStartTime = System.currentTimeMillis();
	}

	public long getDuration() {
		if (this.running) {
			this.duration += System.currentTimeMillis() - this.startTime;
			this.startTime = System.currentTimeMillis();
		}
		return this.duration;
	}

	public long getTmpDuration() {
		if (this.running) {
			this.tmpDuration += System.currentTimeMillis() - this.tmpStartTime;
			this.tmpStartTime = System.currentTimeMillis();
		}
		return this.tmpDuration;
	}

	public boolean isRunning() {
		return this.running;
	}
}
