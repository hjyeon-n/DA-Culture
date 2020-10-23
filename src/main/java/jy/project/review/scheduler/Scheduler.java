package jy.project.review.scheduler;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Scheduler {
//	매일 자정마다 실행되는 스케줄러로, 해당 주의 월요일을 기준으로 지난 주의 베스트 게시물을 선정함
	@Scheduled(cron = "0 0 * * *")
    public String cronScheduler(){
		String date = "";
        try {
           Calendar cal = Calendar.getInstance();
           SimpleDateFormat dateForm = new SimpleDateFormat("yyyy-MM-dd");
//           Calendar.set(설정할 곳, 설정할 값)
           cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
           date = dateForm.format(cal.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }
}
