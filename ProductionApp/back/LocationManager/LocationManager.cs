using PyxisKapriBack.LocationManager.Interfaces;
using PyxisKapriBack.Models;
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

        public List<Location> GetAllAroundLocations(Location location, List<Location> locations, double distance = Constants.Constants.DISTANCE)
        {
            var closest = new List<Location>();
            double dist; 

            foreach(Location loc in locations)
            {
                dist = GetDistance(location.Latitude, location.Longitude, loc.Latitude, loc.Longitude); 
                if (dist <= distance)
                {
                    loc.Distance = dist; 
                    closest.Add(loc);
                }
            }
            return closest;
        }
    }
}
