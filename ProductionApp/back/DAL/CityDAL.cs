using PyxisKapriBack.DAL.Interfaces;
using PyxisKapriBack.Models;

namespace PyxisKapriBack.DAL
{
    public class CityDAL : ICityDAL
    {
        private Database _context; 
        public CityDAL(Database context)
        {
            _context = context;
        }
        public bool AddCity(string cityName, string countryName)
        {
            var country = _context.GetCountry(countryName);

            if (country == null)
                country = _context.InsertCountry(countryName);

            if (country == null)
                return false;

            if (_context.InsertCity(cityName, countryName) == null) 
                return false;
            return true; 
        }

        public bool DeleteCity(string cityName)
        {
            City city = GetCity(cityName);
            if (city == null)
                return false;
            _context.Cities.Remove(city);
            _context.SaveChanges();
            return true;
        }

        public List<City> FilterCities(string filter)
        {
            return _context.Cities.Where(city => city.Name.ToLower().Contains(filter.ToLower())).ToList();
        }

        public City GetCity(string cityName)
        {
            return _context.GetCity(cityName);
        }

        public bool UpdateCity(City city)
        {
            if (city == null)
                return false;
            _context.Cities.Update(city); 
            _context.SaveChanges();
            return true;
        }
    }
}
