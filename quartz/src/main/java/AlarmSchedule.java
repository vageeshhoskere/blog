import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.calendar.WeeklyCalendar;

import static org.quartz.CronScheduleBuilder.dailyAtHourAndMinute;
import static org.quartz.JobBuilder.newJob;

public class AlarmSchedule {

	public AlarmSchedule(){
		try{
			//Create the weekly calendar object - This by default handles disaabling job fire on weekends so no need
			//to explicitly set
			WeeklyCalendar weeklyOff = new WeeklyCalendar();
			//example of adding an excluded day of the week - This excludes fridays from job firing schedule
			//weeklyOff.setDayExcluded(Calendar.FRIDAY, true);
			SchedulerFactory schdFact = new StdSchedulerFactory();
			Scheduler schd = schdFact.getScheduler();
			//add the Calendar object created to the scheduler with a string identifier to it
			schd.addCalendar("weeklyOff", weeklyOff, false, true);
			schd.start();
			JobDetail job = newJob(AlarmJob.class).withIdentity("alarmjob", "alarmjobgroup").build();
			Trigger trigger = TriggerBuilder.newTrigger()
					.withIdentity("alarmtrigger", "alarmtriggergroup")
					.startNow()
					.withSchedule(dailyAtHourAndMinute(6, 30))
					.modifiedByCalendar("weeklyOff")
					.build();
			schd.scheduleJob(job,trigger);
		}
		catch(SchedulerException e){
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new AlarmSchedule();
	}
}