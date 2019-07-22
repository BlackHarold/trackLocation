package home.blackharold.trackerlocation.sender;

import android.os.AsyncTask;
import android.util.Log;

import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class EventSender extends Fragment {

    public void apiCall(LatLng latLng) {
        new AsyncClass(latLng).execute();
    }
}

class AsyncClass extends AsyncTask {
    private final String CHARSET = "UTF-8";

    private final String TAG = "TRACKER_TAG";
    private float latitude, longitude;

    public AsyncClass(LatLng latLng) {
        latitude = (float) latLng.latitude;
        longitude = (float) latLng.longitude;
    }

    @Override
    protected Object doInBackground(Object... objects) {
        String address, lat, lot, gmapUrl, date, result;
        StringBuilder builder = new StringBuilder();
        address = "https://dweet.io/dweet/for/tracker-gps-blackharold?";
        lat = "latitude=" + latitude;
        lot = "&longitude=" + longitude;
        gmapUrl = "&url=https://www.google.ru/maps/search/" + latitude + ",+" + longitude + "/@" + latitude + "," + longitude + ",15z";

        result = builder
                .append(address)
                .append(lat)
                .append(lot)
                .append(gmapUrl)
                .toString();
        Log.i(TAG, result);

        try {
            TimeUnit.SECONDS.sleep(10);
            URL urlWithGoogleMap = new URL(result);
            HttpURLConnection httpURLConnection = (HttpURLConnection) urlWithGoogleMap.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.connect();
            Log.i(TAG, "Sending is O.K., status is " + httpURLConnection.getResponseCode());
        } catch (IOException | InterruptedException e) {
            Log.e(TAG, "Send failed");
            e.printStackTrace();
        }
        return null;
    }
}
