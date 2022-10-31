using PyxisKapriBack.DAL.Interfaces;
using PyxisKapriBack.Models;

namespace PyxisKapriBack.DAL
{
    public class LocationDAL : ILocationDAL
    {
        private Database _context;

        public bool AddLocation(string locationName, string cityName, string countryName = Constants.Constants.UNKNWOWN)
        {
            var country = _context.GetCountry(countryName);
            if (country == null)
                country = _context.InsertCountry(countryName); 
            var city = _context.GetCity(cityName);
            if (city == null)
                city = _context.InsertCity(cityName, countryName); 
            
            if(_context.InsertLocation(locationName, cityName) != null)
                return true;    
            return false;
        }

        public bool DeleteLocation(string locationName)
        {
            Location location = GetLocation(locationName);
            if (location != null)
                if (_context.Locations.Remove(location) != null)
                {
                    _context.SaveChanges();
                    return true;
                }
                throw new Exception(String.Format(Constants.Constants.resDeleteFailed, locationName));
            throw new Exception(Constants.Constants.resNoFoundLocation);
        }

        public List<Location> FilterLocations(string filter)
        {
            return _context.Locations.Where(location => location.Name.ToLower().Contains(filter.ToLower())).ToList(); 
        }

        public List<Location> FilterLocationsByCity(string filter)
        {
            return _context.Locations.Where(location => location.City.Name.ToLower().Contains(filter.ToLower())).ToList();
        }

        public List<Location> FIlterLocationsByCountry(string filter)
        {
            return _context.Locations.Where(location => location.City.Country.Name.ToLower().Contains(filter.ToLower())).ToList();
        }

        public Location GetLocation(string locationName)
        {
            return _context.GetLocation(locationName);
        }

        public bool UpdateLocation(Location location)
        {
            if (_context.Locations.Update(location) != null)
            {
                _context.SaveChanges();
                return true;
            }
            return false;
        }
    }
}
