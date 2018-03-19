import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import static org.quartz.SimpleScheduleBuilder.simpleSchedule;

public class PrintRescheduler {

	private Scheduler scheduler;
	public PrintRescheduler() {
		try {
			scheduler = new StdSchedulerFactory().getScheduler();
			scheduler.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void reSchedule() throws SchedulerException {
		String triggerName = "printTrigger";
		String triggerGroup = "printtriggergroup";
		Trigger oldTrigger = scheduler.getTrigger(TriggerKey.triggerKey(triggerName, triggerGroup));
		//use the same trigger builder so that we do not have to worry about change in name/group
		TriggerBuilder triggerBuilder = oldTrigger.getTriggerBuilder();
		Trigger newTrigger = triggerBuilder
				.withSchedule(simpleSchedule()
						.withIntervalInMilliseconds(200l)
						.repeatForever())
				.build();
		scheduler.rescheduleJob(TriggerKey.triggerKey(triggerName, triggerGroup),newTrigger);
	}

	public void stopScheduler() throws SchedulerException {
		scheduler.shutdown();
	}

	public static void main(String[] args) {
		PrintRescheduler printRescheduler = new PrintRescheduler();
		try {
			Thread.sleep(10000l);
			printRescheduler.reSchedule();
			Thread.sleep(10000l);
			printRescheduler.stopScheduler();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
