package library.utils;

import library.services.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScheduledService {

    @Scheduled(cron = "0 5 * * * *")    //runs every 5 minutes
    private void clearOTPMap(){
        UserServiceImpl.clearOTPMap();
    }

    @Scheduled(cron = "0 0 0 * * *")    //runs at 12 am everyday
    private void clearTokenMap(){
        UserServiceImpl.clearTokenMap();
    }

}
