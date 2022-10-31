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
            if (country != null)
                if (_context.Countries.Remove(country) != null)
                {
                    _context.SaveChanges(); 
                    return true;
                }
                throw new Exception(String.Format(Constants.Constants.resDeleteFailed, countryName));

            throw new Exception(Constants.Constants.resNoFoundCountry);  
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
           if(_context.Countries.Update(country) != null){
                _context.SaveChanges();
                return true; 
           }
            return false; 
        }
    }
}
