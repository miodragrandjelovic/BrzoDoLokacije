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

        public List<Location> GetAllAroundLocations(GeoCoordinate coordinate, List<Post> posts, double distance = Constants.Constants.DISTANCE)
        {
            var closest = new List<Location>();
            double dist;
            if (coordinate == null)
                return closest; 

            foreach(var post in posts)
            {
                if ((post.Latitude == null) || (post.Longitude == null))
                    continue; 
                dist = GetDistance(coordinate.Latitude, coordinate.Longitude, Convert.ToDouble(post.Latitude), Convert.ToDouble(post.Longitude)); 
                if (dist <= distance)
                {
                    var location = new Location();
                    location.Name = post.FullLocation; 
                    location.Distance = Math.Round(dist,2); 
                    closest.Add(location);
                }
            }
            return closest;
        }

        public List<Post> GetAllAroundPosts(GeoCoordinate coordinate, List<Post> posts, double distance = Constants.Constants.DISTANCE)
        {
            var closest = new List<Post>();
            double dist;
            if (coordinate == null)
                return closest;

            foreach (var post in posts)
            {
                if ((post.Latitude == null) || (post.Longitude == null))
                    continue;
                
                dist = GetDistance(coordinate.Latitude, coordinate.Longitude, Convert.ToDouble(post.Latitude), Convert.ToDouble(post.Longitude));
                
                if (dist <= distance)
                    closest.Add(post);
            }
            return closest;
        }
    }
}
