package ramstore;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

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
		//create a job
		JobDetail job = newJob(PrintStatefulJob.class).withIdentity("printjob", "printjobgroup").build();
		//put a count variable that we can keep incrementing
		job.getJobDataMap().put("count",0);
		//create a trigger
		Trigger trigger = TriggerBuilder.newTrigger().withIdentity("printTrigger", "printtriggergroup")
				.startNow().withSchedule(simpleSchedule().withIntervalInMilliseconds(100l).repeatForever()).build();
		//schedule the job
		scheduler.scheduleJob(job, trigger);
	}

	public void stopScheduler() throws SchedulerException {
		//scheduler shutdown
		scheduler.shutdown();
	}

	public static void main(String[] args) {
		PrintRamScheduler printScheduler = new PrintRamScheduler();
		try {
			printScheduler.schedule();
			Thread.sleep(60000l);
			printScheduler.stopScheduler();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
