package GTD.restapi.util.interceptors;

/**
 * Created by Drugnanov on 11.12.2014.
 */
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import GTD.restapi.util.exceptions.MaintenenceException;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@Component
public class MaintenenceInterceptor extends HandlerInterceptorAdapter {

    private int maintenanceStartTime;
    private int maintenanceEndTime;

    public void setMaintenanceStartTime(int maintenanceStartTime) {
        this.maintenanceStartTime = maintenanceStartTime;
    }

    public void setMaintenanceEndTime(int maintenanceEndTime) {
        this.maintenanceEndTime = maintenanceEndTime;
    }

    //before the actual handler will be executed
    public boolean preHandle(HttpServletRequest request,
            HttpServletResponse response, Object handler)
            throws Exception {

        Calendar cal = Calendar.getInstance();
        int hour = cal.get(cal.HOUR_OF_DAY);

        if (hour >= maintenanceStartTime && hour <= maintenanceEndTime) {
            //maintenance time, send to maintenance page
            throw new MaintenenceException();
        } else {
            return true;
        }

    }
}
