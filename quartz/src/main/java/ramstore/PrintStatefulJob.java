package ramstore;

import org.quartz.*;

@PersistJobDataAfterExecution
public class PrintStatefulJob implements Job{

	@Override
	public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
		JobDataMap jobDataMap = jobExecutionContext.getJobDetail().getJobDataMap();
		Integer count = jobDataMap.getInt("count");
		count++;
		System.out.println("Printing count : "+count);
		jobDataMap.put("count",count);
	}

}
