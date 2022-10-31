using PyxisKapriBack.DAL.Interfaces;
using PyxisKapriBack.Models;

namespace PyxisKapriBack.DAL
{
    public class LocationDAL : ILocationDAL
    {
        private Database _context;
        private int page = 0; 
        private List<Location> locations = new List<Location>();
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
            locations.Clear();
            locations = _context.Locations.Where(location => location.Name.ToLower().Contains(filter.ToLower())).ToList();
            if(locations.Count > 0)
            {
                if (locations.Count > Constants.Constants.TAKE_ELEMENT)
                    return locations.Take(Constants.Constants.TAKE_ELEMENT).ToList();
                return locations; 
            }
            return null; 
        }

        public List<Location> FilterLocationsByCity(string filter)
        {
            return _context.Locations.Where(location => location.City.Name.ToLower().Contains(filter.ToLower())).ToList();
        }

        public List<Location> FilterLocationsByCountry(string filter)
        {
            return _context.Locations.Where(location => location.City.Country.Name.ToLower().Contains(filter.ToLower())).ToList();
        }

        public Location GetLocation(string locationName)
        {
            return _context.GetLocation(locationName);
        }

        public List<Location> GetNextSetOfLocations(int take = Constants.Constants.TAKE_ELEMENT)
        {
            List<Location> subLocations = new List<Location>();
            page++;
            for (int i = page*take; i < (page+1) * take; i++)
                subLocations.Add(locations[i]);
            return subLocations; 
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
