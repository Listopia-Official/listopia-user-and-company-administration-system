package florian_haas.lucas.model;

import static org.junit.Assert.*;

import org.junit.*;

import florian_haas.lucas.util.Utils;

public class StopwatchTest {

	private Stopwatch stopwatch;

	@Before
	public void setUp() {
		this.stopwatch = new Stopwatch();
	}

	@Test
	public void testStopwatchCreation() {
		assertEquals(0, this.stopwatch.getDuration());
		assertEquals(0, this.stopwatch.getTmpDuration());
	}

	@Test
	public void testStopwatch() throws InterruptedException {
		assertFalse(this.stopwatch.isRunning());
		this.stopwatch.start();
		assertTrue(this.stopwatch.isRunning());
		Thread.sleep(1010);
		this.stopwatch.stop();
		assertFalse(this.stopwatch.isRunning());
		assertEquals(true, this.stopwatch.getDuration() >= 1000);
		assertEquals(true, this.stopwatch.getTmpDuration() >= 1000);
		this.stopwatch.start();
		assertTrue(this.stopwatch.isRunning());
		Thread.sleep(1010);
		this.stopwatch.stop();
		assertFalse(this.stopwatch.isRunning());
		assertEquals(true, this.stopwatch.getDuration() >= 2000);
		assertEquals(true, Utils.isInRangeInclusive(1000, 1010, this.stopwatch.getTmpDuration()));
	}

	@Test
	public void testStopwatchReset() throws InterruptedException {
		this.stopwatch.start();
		Thread.sleep(1000);
		this.stopwatch.stop();
		this.stopwatch.reset();
		assertEquals(0, this.stopwatch.getDuration());
		assertEquals(0, this.stopwatch.getTmpDuration());
	}

}
