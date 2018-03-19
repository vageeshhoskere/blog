import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.io.*;
import java.util.Properties;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;

public class PrintScheduler {

	private Scheduler scheduler;
	public PrintScheduler(String instanceId) {
		try {
			Properties properties = loadProperties();
			properties.put("org.quartz.scheduler.instanceId",instanceId);
			scheduler = new StdSchedulerFactory(properties).getScheduler();
			scheduler.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private Properties loadProperties() throws IOException {
		Properties properties = new Properties();
		try (InputStream fis = PrintScheduler.class.getResourceAsStream("quartz.properties")) {
			properties.load(fis);
		}
		return properties;
	}

	public void schedule() throws SchedulerException {
		JobDetail job = newJob(PrintJob.class).withIdentity("printjob", "printjobgroup").build();
		Trigger trigger = TriggerBuilder.newTrigger().withIdentity("printTrigger", "printtriggergroup")
				.startNow().withSchedule(simpleSchedule().withIntervalInMilliseconds(100l).repeatForever()).build();
		scheduler.scheduleJob(job, trigger);
	}

	public void stopScheduler() throws SchedulerException {
		scheduler.shutdown();
	}

	public static void main(String[] args) {
		PrintScheduler printScheduler = new PrintScheduler(args[0]);
		try {
//			printScheduler.schedule();
			Thread.sleep(60000l);
			printScheduler.stopScheduler();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
