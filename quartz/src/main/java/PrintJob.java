import org.quartz.*;

@PersistJobDataAfterExecution
public class PrintJob implements Job {

	public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
		try {
			System.out.println("Print : "+System.currentTimeMillis()+" , "+jobExecutionContext.getScheduler().getSchedulerInstanceId());
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}
}
