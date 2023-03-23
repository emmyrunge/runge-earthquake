package runge.earthquakes;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;

public interface EarthquakeService
{

    @GET("/earthquakes/feed/v1.0/summary/significant_month.geojson")
    Observable<FeatureCollection> getLatestEarthquakes();

}
