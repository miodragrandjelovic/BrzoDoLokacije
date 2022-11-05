using PyxisKapriBack.Models;

namespace PyxisKapriBack.DAL.Interfaces
{
    public interface ICountryDAL
    {
        public bool AddCountry(string countryName);
        public bool UpdateCountry(Country country); 
        public bool DeleteCountry(string countryName);
        public Country GetCountry(string countryName);
        public List<Country> FilterCountries(string filter);
    }
}
