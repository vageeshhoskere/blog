package ramstore;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.matchers.KeyMatcher;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;

public class PrintRamScheduler {

	private Scheduler scheduler;
	public PrintRamScheduler() {
		try {
			//create scheduler factory
			scheduler = new StdSchedulerFactory().getScheduler();
			scheduler.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void schedule() throws SchedulerException {
		JobKey jobKey = JobKey.jobKey("printjob", "printjobgroup");
		//create a job
		JobDetail job = newJob(PrintStatefulJob.class).withIdentity(jobKey).build();
		//put a count variable that we can keep incrementing
		job.getJobDataMap().put("count",0);
		//create a trigger
		Trigger trigger = TriggerBuilder.newTrigger().withIdentity("printTrigger", "printtriggergroup")
				.startNow().withSchedule(simpleSchedule().withIntervalInMilliseconds(100l).repeatForever()).build();
		//schedule the job
		scheduler.scheduleJob(job, trigger);
		//add a listner only for specific job - It is also possible to add a generic listener for all jobs
		scheduler.getListenerManager().addJobListener(new PrintJobListener(), KeyMatcher.keyEquals(jobKey));
	}

	public void stopScheduler() throws SchedulerException {
		//scheduler shutdown
		scheduler.shutdown();
	}

	public static void main(String[] args) {
		PrintRamScheduler printScheduler = new PrintRamScheduler();
		try {
			printScheduler.schedule();
			Thread.sleep(10000l);
			printScheduler.stopScheduler();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
