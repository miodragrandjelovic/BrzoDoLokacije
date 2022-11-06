using PyxisKapriBack.LocationManager.Interfaces;
using System.Device.Location; 
namespace PyxisKapriBack.LocationManager
{
    public class LocationManager : ILocationManager
    {
        public double GetDistance(double Latitude1, double Longitude1, double Latitude2, double Longitude2)
        {
            var coordinate1 = new GeoCoordinate(Latitude1, Longitude1); 
            var coordinate2 = new GeoCoordinate(Latitude2, Longitude2);
            
            return coordinate1.GetDistanceTo(coordinate2);
        } 
    }
}
