package home.blackharold.trackerlocation.sender;

import android.os.AsyncTask;
import android.util.Log;

import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class EventSender extends Fragment {

    private LatLng latLng;

    public EventSender(LatLng latLng) {
        this.latLng = latLng;
    }

    public void apiCall() {
        new AsyncClass(latLng).execute();
    }
}

class AsyncClass extends AsyncTask {
    private final String CHARSET = "UTF-8";

    private final String TAG = "TrackerTag";
    private float latitude, longitude;

    public AsyncClass(LatLng latLng) {
        latitude = (float) latLng.latitude;
        longitude = (float) latLng.longitude;
    }

    @Override
    protected Object doInBackground(Object... objects) {

        Date date = new Date();

        try {
            TimeUnit.SECONDS.sleep(30);
//            URL sendUrl = new URL("https://dweet.io/dweet/for/tracker-gps-blackharold?latitude=" + latitude + "&longitude=" + longitude);
            URL urlWithGoogleMap = new URL("https://dweet.io/dweet/for/tracker-gps-blackharold?latitude="+latitude+"&longitude="+longitude+"&url=https://www.google.ru/maps/search/"
                    +latitude+",+"+longitude+"/@"+latitude+","+longitude+",15z");
            HttpURLConnection httpURLConnection = (HttpURLConnection) urlWithGoogleMap.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.connect();
            Log.i(TAG, "Sending is O.K., status is " + httpURLConnection.getResponseCode() + ", " + date.toString());
        } catch (IOException | InterruptedException e) {
            Log.e(TAG, "Send failed");
            e.printStackTrace();
        }
        return null;
    }
}