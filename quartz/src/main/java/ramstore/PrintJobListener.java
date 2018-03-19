package ramstore;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;

public class PrintJobListener implements JobListener {
	@Override
	public String getName() {
		return "PRINT_JOB_LISTENER";
	}

	@Override
	public void jobToBeExecuted(JobExecutionContext jobExecutionContext) {

	}

	@Override
	public void jobExecutionVetoed(JobExecutionContext jobExecutionContext) {
		//called when a job is vetoed via the TriggerListener#vetoJobExecution
		//details - http://www.quartz-scheduler.org/api/2.2.1/org/quartz/TriggerListener.html#vetoJobExecution(org.quartz.Trigger,%20org.quartz.JobExecutionContext)
	}

	@Override
	public void jobWasExecuted(JobExecutionContext jobExecutionContext, JobExecutionException e) {
		System.out.println("Job was executed for count "
				+String.valueOf(jobExecutionContext.getMergedJobDataMap().get("count")));
	}
}
