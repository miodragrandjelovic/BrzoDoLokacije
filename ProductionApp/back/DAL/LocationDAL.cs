using PyxisKapriBack.DAL.Interfaces;
using PyxisKapriBack.LocationManager.Interfaces;
using PyxisKapriBack.Models;

namespace PyxisKapriBack.DAL
{
    public class LocationDAL : ILocationDAL
    {
        private Database _context;
        private ILocationManager locationManager; 
        private int page = 0;
        private List<Location> locations;
        public LocationDAL(Database context, ILocationManager locationManager)
        {
            _context = context;
            this.locationManager = locationManager;
        }

        public bool AddLocation(string locationName, string cityName, string countryName = Constants.Constants.UNKNWOWN)
        {
            var country = _context.GetCountry(countryName);
            if (country == null)
                country = _context.InsertCountry(countryName); 
            if(country == null)
                return false;


            var city = _context.GetCity(cityName);
            if (city == null)
                city = _context.InsertCity(cityName, countryName);
            if (city == null)
                return false;

            if (_context.InsertLocation(locationName, cityName) == null)
                return false;    
            return true;
        }

        public bool DeleteLocation(string locationName)
        {
            Location location = GetLocation(locationName);
            if (location == null)
                return false;

            _context.Locations.Remove(location);
            _context.SaveChanges();
            return true;
        }

        public List<Location> FilterLocationsByName(string filter)
        {
            List<Location> locations;
            if (String.IsNullOrEmpty(filter))
                locations = _context.Locations.Where(location => (!location.Name.Equals(Constants.Constants.UNKNWOWN))).ToList(); 
            else
                locations = _context.Locations.Where(location => (location.Name.ToLower().Contains(filter.ToLower())) &&
                                                             (!location.Name.Equals(Constants.Constants.UNKNWOWN)))
                                              .Include(loc => loc.City)
                                              .Include(city => city.City.Country)
                                              .ToList();
            return locations.Take(Constants.Constants.TAKE_ELEMENT).ToList(); 
        }

        public List<Location> FilterLocationsByCity(string filter)
        {
            List<Location> locations;
            if (String.IsNullOrEmpty(filter))
                locations = _context.Locations.Where(location => (!location.City.Name.Equals(Constants.Constants.UNKNWOWN))).ToList();
            else 
                locations = _context.Locations.Where(location => location.City.Name.ToLower().Contains(filter.ToLower()) &&
                                                             (!location.City.Name.Equals(Constants.Constants.UNKNWOWN)))
                                              .Include(loc => loc.City)
                                              .Include(city => city.City.Country)
                                              .ToList();
            return locations;
        }

        public List<Location> FilterLocationsByCountry(string filter)
        {
            List<Location> locations;
            if (String.IsNullOrEmpty(filter))
                locations = _context.Locations.Where(location => (!location.City.Country.Name.Equals(Constants.Constants.UNKNWOWN))).ToList();
            else 
                locations = _context.Locations.Where(location => location.City.Country.Name.ToLower().Contains(filter.ToLower()) &&
                                                             (!location.Name.Equals(Constants.Constants.UNKNWOWN)))
                                              .Include(loc => loc.City)
                                              .Include(city => city.City.Country)
                                              .ToList();
            return locations; 
        }

        public Location GetLocation(string locationName)
        {
            return _context.GetLocation(locationName);
        }
        public Location GetLocation(int locationID)
        {
            return _context.Locations.Where(location => location.Id == locationID).FirstOrDefault(); 
        }

        public List<Location> GetNextSetOfLocations(int take = Constants.Constants.TAKE_ELEMENT)
        {
            List<Location> subLocations = new List<Location>();
            for (int i = page*take; i < (page+1) * take; i++)
                subLocations.Add(locations[i]);
            page++;
            return subLocations; 
        }

        public bool UpdateLocation(Location location)
        {
            if (location == null)
                return false; 

            _context.Locations.Update(location);
            _context.SaveChanges();
            return true; 
        }

        public List<Location> FilterLocations(string filter)
        {
            var locationsByCountry = FilterLocationsByCountry(filter);
            var locationsByCity = FilterLocationsByCity(filter);
            var locationsByName = FilterLocationsByName(filter);
            
            var allLocations = new List<Location>(); 
            foreach (var item in locationsByCountry)
                allLocations.Add(item);
            foreach (var item in locationsByCity)
                allLocations.Add(item);
            foreach (var item in locationsByName)
                allLocations.Add(item);
            locations = allLocations.Distinct().ToList();
            return locations; 
        }

        public List<Location> GetAllAroundLocations(Location location, double distance = Constants.Constants.DISTANCE)
        {
            List<Location> locations;
            locations = _context.Locations.Include(location => location.Posts).ToList();
            locations = locationManager.GetAllAroundLocations(location, locations, distance); 
            return locations; 
        }

        public bool AddLocation(Location location)
        {
            if (location.City == null)
                throw new Exception(Constants.Constants.resNotFoundCity); 

            _context.Locations.Add(location);
            _context.SaveChanges();

            return true; 
        }
    }
}
