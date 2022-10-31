using PyxisKapriBack.Models;

namespace PyxisKapriBack.DAL.Interfaces
{
    public interface ICountryDAL
    {
        public Boolean AddCountry(string countryName);
        public Boolean UpdateCountry(Country country); 
        public Boolean DeleteCountry(string countryName);
        public Country GetCountry(string countryName);
        public List<Country> FilterCountries(string filter);
    }
}
