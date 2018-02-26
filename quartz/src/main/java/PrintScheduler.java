import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;

public class PrintScheduler {

	private Scheduler scheduler;
	public PrintScheduler() {
		try {
			scheduler = StdSchedulerFactory.getDefaultScheduler();
			scheduler.start();
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
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
		PrintScheduler printScheduler = new PrintScheduler();
		try {
//			printScheduler.schedule();
			Thread.sleep(60000l);
			printScheduler.stopScheduler();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
