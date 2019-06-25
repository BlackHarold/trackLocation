package home.blackharold.trackerlocation;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;

public class EventSender extends Activity {

    LatLng latLng;
    String email, password;

    public EventSender(LatLng latLng) {
        this.latLng = latLng;

        email = getString(R.string.email);
        password = getString(R.string.password);

    }

    protected void apiCall() {
        new AsyncClass(latLng, email, password).execute();
    }
}

class AsyncClass extends AsyncTask {

    //    private LatLng latLng;
    float latitude, longitude;
    private final String CHARSET = "UTF-8";
    String email, password;

    public AsyncClass(LatLng latLng, String email, String password) {
//        this.latLng = latLng;
        latitude = (float) latLng.latitude;
        longitude = (float) latLng.longitude;
        this.email = email;
        this.password = password;
    }

    @Override
    protected Object doInBackground(Object[] objects) {

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.mail.ru");
        props.put("mail.smtp.port", "465");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.ssl.enable", "true");

        Session session = Session.getDefaultInstance(props);
        MimeMessage message = new MimeMessage(session);
        try {
            message.setFrom(email);
            message.setRecipients(Message.RecipientType.TO, email);
            message.setSubject("Tracker coordinates", CHARSET);
            message.setSentDate(new Date());
            //for sending with Google maps URL
//            https://www.google.ru/maps/search/${latitude},++${longitude}/@${latitude},${longitude},15z
//            --------------------------------------------------------------------------------------------v
//            message.setText("{\n \"latitude\": \"" + latitude + "\",\n \"longitude\": \"" + longitude + "\"\n}" +
//                    "\nhttps://www.google.ru/maps/search/"+latitude+",++"+longitude+"/@"+latitude+","+longitude+",15z", CHARSET);
//            just JSON coodinates
//            --------------------------v
            message.setText("{\n \"latitude\": \"" + latitude + "\",\n \"longitude\": \"" + longitude + "\"\n}", CHARSET);
            Transport.send(message, email, password);
        } catch (MessagingException e) {
            Log.e("error to send", "Error to send");
            e.printStackTrace();
        }
        return null;
    }
}
