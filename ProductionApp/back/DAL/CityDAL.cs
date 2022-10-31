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
            if (_context.InsertCity(cityName, countryName) != null)
                return true;
            return false;
        }

        public bool DeleteCity(string cityName)
        {
            City city = GetCity(cityName); 
            if (city != null)
                if (_context.Cities.Remove(city) != null)
                {
                    _context.SaveChanges();
                    return true;
                }
                throw new Exception(String.Format(Constants.Constants.resDeleteFailed, cityName));
            throw new Exception(Constants.Constants.resNoFoundCity);
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
            if (_context.Cities.Update(city) != null)
            {
                _context.SaveChanges();
                return true;
            }
            return false;
        }

    }
}
