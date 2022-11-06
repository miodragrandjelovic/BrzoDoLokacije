using PyxisKapriBack.DAL.Interfaces;
using PyxisKapriBack.Models;

namespace PyxisKapriBack.DAL
{
    public class CountryDAL : ICountryDAL
    {
        private Database _context;
        public CountryDAL(Database context)
        {
            _context = context;
        }
        public bool AddCountry(string countryName)
        {
            if(_context.InsertCountry(countryName) != null)
                return true;
            return false;
        }

        public bool DeleteCountry(string countryName)
        {
            Country country = GetCountry(countryName);
            if (country == null)
                return false;
            _context.Countries.Remove(country);
            _context.SaveChanges();
            return true;
        }

        public List<Country> FilterCountries(string filter)
        {
            return _context.Countries.Where(country => country.Name.ToLower().Contains(filter.ToLower())).ToList(); 
        }

        public Country GetCountry(string countryName)
        {
            return _context.GetCountry(countryName);
        }

        public bool UpdateCountry(Country country)
        {
            if (country == null)
                return false;
            _context.Update(country); 
            _context.SaveChanges();
            return true; 
        }
    }
}
